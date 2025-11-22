package br.com.fiap.one_matter.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // Se o header não existir ou não começar com "Bearer ", continua a cadeia (não é erro)
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            username = tokenService.getUsername(token); // Tenta obter o username
        }

        // Se o token for válido E não houver autenticação no contexto, autentica
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = authenticationService.loadUserByUsername(username);

            if (tokenService.isTokenValido(token)) {

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Define o usuário autenticado no contexto do Spring Security
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Continua o fluxo, passando para o próximo filtro (ou para o Controller)
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();

        return path.startsWith("/auth/login") ||
                path.startsWith("/auth/register") ||
                path.startsWith("/auth/refresh");
    }
}
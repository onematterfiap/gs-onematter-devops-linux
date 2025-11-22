package br.com.fiap.one_matter.config;

import br.com.fiap.one_matter.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String gerarAccessToken(Usuario usuario) {
        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + expiration);
        SecretKey key = getSigningKey();

        return Jwts.builder()
                .setIssuer("API Medix")
                .setSubject(usuario.getEmail())
                .setIssuedAt(hoje)
                .setExpiration(dataExpiracao)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String gerarRefreshToken(Usuario usuario) {
        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + refreshExpiration);
        SecretKey key = getSigningKey();

        return Jwts.builder()
                .setIssuer("API Medix")
                .setSubject(usuario.getEmail())
                .setIssuedAt(hoje)
                .setExpiration(dataExpiracao)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        try {
            SecretKey key = getSigningKey();
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Token inválido ou expirado.", e);
        }
    }

    public String getUsername(String token) {
        try {
            return extractAllClaims(token).getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isTokenValido(String token) {
        return getUsername(token) != null;
    }
}
package br.com.fiap.one_matter.service;

import br.com.fiap.one_matter.dto.request.RecrutadorRequestDto;
import br.com.fiap.one_matter.model.Empresa;
import br.com.fiap.one_matter.model.Recrutador;
import br.com.fiap.one_matter.repository.RecrutadorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecrutadorService {

    private final RecrutadorRepository recrutadorRepository;
    private final EmpresaService empresaService;
    private final PasswordEncoder passwordEncoder;

    public Page<Recrutador> listar(Optional<Integer> deletedStatus, Pageable pageable) {
        if (deletedStatus.isPresent() && (deletedStatus.get() == 0 || deletedStatus.get() == 1)) {
            return recrutadorRepository.findByDeleted(deletedStatus.get(), pageable);
        }
        return recrutadorRepository.findAll(pageable);
    }

    public Recrutador buscarPorIdAtivo(Long id) {
        return recrutadorRepository.findByIdAndDeleted(id, 0)
                .orElseThrow(() -> new EntityNotFoundException("Recrutador não encontrado ou inativo."));
    }

    @Transactional
    public Recrutador criar(RecrutadorRequestDto dto) {
        Empresa empresa = empresaService.buscarPorIdAtiva(dto.idEmpresa());

        String senhaHash = passwordEncoder.encode(dto.senha());

        Recrutador recrutador = Recrutador.builder()
                .nome(dto.nome())
                .telefone(dto.telefone())
                .cpf(dto.cpf())
                .email(dto.email())
                .senhaHash(senhaHash)
                .empresa(empresa)
                .deleted(0)
                .build();
        return recrutadorRepository.save(recrutador);
    }

    @Transactional
    public Recrutador atualizar(Long id, RecrutadorRequestDto dto) {
        Recrutador recrutador = buscarPorIdAtivo(id);

        if (dto.idEmpresa() != null && !dto.idEmpresa().equals(recrutador.getEmpresa().getId())) {
            Empresa novaEmpresa = empresaService.buscarPorIdAtiva(dto.idEmpresa());
            recrutador.setEmpresa(novaEmpresa);
        }

        recrutador.setNome(dto.nome());
        recrutador.setTelefone(dto.telefone());
        recrutador.setCpf(dto.cpf());
        recrutador.setEmail(dto.email());

        if (dto.senha() != null && !dto.senha().isBlank()) {
            String senhaHash = passwordEncoder.encode(dto.senha());
            recrutador.setSenhaHash(senhaHash);
        }

        return recrutador;
    }

    @Transactional
    public void deletar(Long id) {
        Recrutador recrutador = buscarPorIdAtivo(id);
        recrutador.setDeleted(1);
        recrutadorRepository.save(recrutador);
    }
}
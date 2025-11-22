package br.com.fiap.one_matter.service;

import br.com.fiap.one_matter.dto.request.EmpresaRequestDto;
import br.com.fiap.one_matter.model.Empresa;
import br.com.fiap.one_matter.repository.EmpresaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public Page<Empresa> listar(Optional<Integer> deletedStatus, Pageable pageable) {
        if (deletedStatus.isPresent() && (deletedStatus.get() == 0 || deletedStatus.get() == 1)) {
            return empresaRepository.findByDeleted(deletedStatus.get(), pageable);
        }
        return empresaRepository.findAll(pageable);
    }

    public Empresa buscarPorIdAtiva(Long id) {
        return empresaRepository.findByIdAndDeleted(id, 0)
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada ou inativa."));
    }

    @Transactional
    public Empresa criar(EmpresaRequestDto dto) {
        // (Validação de CNPJ/Email único pode ser adicionada aqui)
        Empresa empresa = Empresa.builder()
                .nome(dto.nome())
                .telefone(dto.telefone())
                .endereco(dto.endereco())
                .cnpj(dto.cnpj())
                .email(dto.email())
                .deleted(0)
                .build();
        return empresaRepository.save(empresa);
    }

    @Transactional
    public Empresa atualizar(Long id, EmpresaRequestDto dto) {
        Empresa empresa = buscarPorIdAtiva(id);
        empresa.setNome(dto.nome());
        empresa.setTelefone(dto.telefone());
        empresa.setEndereco(dto.endereco());
        empresa.setCnpj(dto.cnpj());
        empresa.setEmail(dto.email());
        return empresa;
    }

    @Transactional
    public void deletar(Long id) {
        Empresa empresa = buscarPorIdAtiva(id);
        empresa.setDeleted(1);
        empresaRepository.save(empresa);
    }
}
package br.com.fiap.one_matter.service;

import br.com.fiap.one_matter.dto.request.VagaRequestDto;
import br.com.fiap.one_matter.model.Empresa;
import br.com.fiap.one_matter.model.Recrutador;
import br.com.fiap.one_matter.model.Vaga;
import br.com.fiap.one_matter.repository.EmpresaRepository;
import br.com.fiap.one_matter.repository.RecrutadorRepository;
import br.com.fiap.one_matter.repository.VagaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VagaService {

    private final VagaRepository vagaRepository;
    private final EmpresaRepository empresaRepository;
    private final RecrutadorRepository recrutadorRepository;

    public Page<Vaga> listar(Optional<Integer> deletedStatus, Pageable pageable) {
        if (deletedStatus.isPresent() && (deletedStatus.get() == 0 || deletedStatus.get() == 1)) {
            return vagaRepository.findByDeleted(deletedStatus.get(), pageable);
        }
        return vagaRepository.findAll(pageable);
    }

    public Vaga buscarPorId(Long id) {
        return vagaRepository.findByIdAndDeleted(id, 0)
                .orElseThrow(() -> new EntityNotFoundException("Vaga não encontrada ou inativa."));
    }

    @Transactional
    public Vaga criar(VagaRequestDto dto) {
        Empresa empresa = empresaRepository.findByIdAndDeleted(dto.idEmpresa(), 0)
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada."));

        Recrutador recrutador = recrutadorRepository.findByIdAndDeleted(dto.idRecrutador(), 0)
                .orElseThrow(() -> new EntityNotFoundException("Recrutador não encontrado."));

        Vaga vaga = Vaga.builder()
                .descricao(dto.descricao())
                .empresa(empresa)
                .recrutador(recrutador)
                .deleted(0)
                .build();

        return vagaRepository.save(vaga);
    }

    @Transactional
    public Vaga atualizar(Long id, VagaRequestDto dto) {
        Vaga vaga = buscarPorId(id); // Garante que a vaga exista e esteja ativa

        if (dto.descricao() != null && !dto.descricao().isBlank()) {
            vaga.setDescricao(dto.descricao());
        }

        // (Lógica para atualizar empresa/recrutador se necessário)

        return vaga; // O @Transactional faz o save
    }

    @Transactional
    public void deletar(Long id) {
        Vaga vaga = buscarPorId(id);
        vaga.setDeleted(1);
        vagaRepository.save(vaga);
    }
}
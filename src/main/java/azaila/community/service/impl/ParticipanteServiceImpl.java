package azaila.community.service.impl;

import azaila.community.dto.participante.ParticipanteRequestDTO;
import azaila.community.dto.participante.ParticipanteResponseDTO;
import azaila.community.dto.participante.ParticipanteUpdateDTO;
import azaila.community.exception.ResourceNotFoundException;
import azaila.community.model.Participante;
import azaila.community.repository.ParticipanteRepository;
import azaila.community.service.interfaces.ParticipanteService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ParticipanteServiceImpl implements ParticipanteService {

    private final ParticipanteRepository participanteRepository;

    public ParticipanteServiceImpl(ParticipanteRepository participanteRepository) {
        this.participanteRepository = participanteRepository;
    }

    @Override
    @Transactional
    public ParticipanteResponseDTO createParticipante(ParticipanteRequestDTO participanteDTO) {
        if (participanteDTO.getNombreCompleto() == null || participanteDTO.getNombreCompleto().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo es obligatorio.");
        }
        if (participanteDTO.getEmail() == null && participanteDTO.getTelefono() == null) {
            throw new IllegalArgumentException("Se requiere al menos un email o un teléfono para el registro.");
        }

        Participante participante = new Participante();
        participante.setNombreCompleto(participanteDTO.getNombreCompleto());
        participante.setEmail(participanteDTO.getEmail());
        participante.setPassword(participanteDTO.getPassword());
        participante.setTelefono(participanteDTO.getTelefono());
        participante.setDieta(participanteDTO.getDieta());
        participante.setPrioridadDiscapacidad(participanteDTO.getPrioridadDiscapacidad());

        Participante nuevoParticipante = participanteRepository.save(participante);
        return mapToResponseDTO(nuevoParticipante);
    }

    @Override
    public ParticipanteResponseDTO getParticipanteById(Long id) {
        Participante participante = participanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participante no encontrado con ID: " + id));
        return mapToResponseDTO(participante);
    }

    @Override
    @Transactional
    public ParticipanteResponseDTO updateParticipante(Long id, ParticipanteUpdateDTO participanteDTO) {
        Participante participante = participanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participante no encontrado con ID: " + id));

        if (participanteDTO.getNombreCompleto() != null) {
            participante.setNombreCompleto(participanteDTO.getNombreCompleto());
        }
        if (participanteDTO.getEmail() != null) {
            participante.setEmail(participanteDTO.getEmail());
        }
        if (participanteDTO.getPassword() != null) {
            participante.setPassword(participanteDTO.getPassword());
        }
        if (participanteDTO.getTelefono() != null) {
            participante.setTelefono(participanteDTO.getTelefono());
        }
        if (participanteDTO.getDieta() != null) {
            participante.setDieta(participanteDTO.getDieta());
        }
        if (participanteDTO.getPrioridadDiscapacidad() != null) {
            participante.setPrioridadDiscapacidad(participanteDTO.getPrioridadDiscapacidad());
        }

        Participante updatedParticipante = participanteRepository.save(participante);
        return mapToResponseDTO(updatedParticipante);
    }

    @Override
    @Transactional
    public void deleteParticipante(Long id) {
        if (!participanteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Participante no encontrado con ID: " + id);
        }
        participanteRepository.deleteById(id);
    }

    private ParticipanteResponseDTO mapToResponseDTO(Participante participante) {
        ParticipanteResponseDTO dto = new ParticipanteResponseDTO();
        dto.setId(participante.getId());
        dto.setNombreCompleto(participante.getNombreCompleto());
        dto.setEmail(participante.getEmail());
        dto.setTelefono(participante.getTelefono());
        dto.setDieta(participante.getDieta());
        dto.setPrioridadDiscapacidad(participante.getPrioridadDiscapacidad());
        dto.setFechaCreacion(participante.getFechaCreacion());
        return dto;
    }
}
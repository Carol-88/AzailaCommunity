package azaila.community.service.impl;

import azaila.community.dto.participante.ParticipanteRequestDTO;
import azaila.community.dto.participante.ParticipanteResponseDTO;
import azaila.community.dto.participante.ParticipanteUpdateDTO;
import azaila.community.exception.ResourceNotFoundException;
import azaila.community.model.Participante;
import azaila.community.model.PersonaIdentidad;
import azaila.community.repository.ParticipanteRepository;
import azaila.community.repository.PersonaIdentidadRepository;
import azaila.community.service.interfaces.ParticipanteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParticipanteServiceImpl implements ParticipanteService {

    private final ParticipanteRepository participanteRepository;

    public ParticipanteServiceImpl(ParticipanteRepository participanteRepository) {
        this.participanteRepository = participanteRepository;
    }

    @Autowired
    PersonaIdentidadRepository personaIdentidadRepository;

    private PersonaIdentidad findOrCreateIdentidad(String email, String telefono){
        Optional<PersonaIdentidad> byEmail = (email != null) ? personaIdentidadRepository.findByEmail(email) : Optional.empty();
        if(byEmail.isPresent()) return byEmail.get();
        Optional<PersonaIdentidad> byTelefono = (telefono != null) ? personaIdentidadRepository.findByTelefono(telefono) : Optional.empty();
        if(byTelefono.isPresent()) return byTelefono.get();

        PersonaIdentidad nueva = new PersonaIdentidad();
        nueva.setEmail(email);
        nueva.setTelefono(telefono);
        return personaIdentidadRepository.save(nueva);
    }

    @Override
    @Transactional
    public ParticipanteResponseDTO createParticipante(ParticipanteRequestDTO dto) {
        // Condiciones de seguridad.

        PersonaIdentidad identidad = findOrCreateIdentidad(dto.getEmail(), dto.getTelefono());

        Participante participante = participanteRepository.findById(identidad.getId())
                .orElseGet(()->{
                    Participante p = new Participante();
                    p.setIdentidad(identidad);
                    return p;
                });

        participante.setNombreCompleto(dto.getNombreCompleto());
        participante.setPassword(dto.getPassword());
        participante.setDieta(dto.getDieta());
        participante.setPrioridadDiscapacidad(dto.getPrioridadDiscapacidad());

        Participante savedParticipante = participanteRepository.save(participante);
        return mapToResponseDTO(savedParticipante);
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
        if (participanteDTO.getPassword() != null) {
            participante.setPassword(participanteDTO.getPassword());
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
        dto.setDieta(participante.getDieta());
        dto.setPrioridadDiscapacidad(participante.getPrioridadDiscapacidad());
        return dto;
    }
}
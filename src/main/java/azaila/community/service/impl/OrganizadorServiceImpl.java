package azaila.community.service.impl;

import azaila.community.dto.organizador.OrganizadorRequestDTO;
import azaila.community.dto.organizador.OrganizadorResponseDTO;
import azaila.community.dto.organizador.OrganizadorUpdateDTO;
import azaila.community.exception.ResourceNotFoundException;
import azaila.community.model.Organizador;
import azaila.community.repository.OrganizadorRepository;
import azaila.community.service.interfaces.OrganizadorService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrganizadorServiceImpl implements OrganizadorService {

    private final OrganizadorRepository organizadorRepository;

    public OrganizadorServiceImpl(OrganizadorRepository organizadorRepository) {
        this.organizadorRepository = organizadorRepository;
    }

    @Override
    @Transactional
    public OrganizadorResponseDTO createOrganizador(OrganizadorRequestDTO organizadorDTO) {
        if (organizadorDTO.getNombreCompleto() == null || organizadorDTO.getNombreCompleto().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo es obligatorio.");
        }
        if (organizadorDTO.getEmail() == null && organizadorDTO.getTelefono() == null) {
            throw new IllegalArgumentException("Se requiere al menos un email o un teléfono para el registro.");
        }

        Organizador organizador = new Organizador();
        organizador.setNombreCompleto(organizadorDTO.getNombreCompleto());
        organizador.setEmail(organizadorDTO.getEmail());
        organizador.setPassword(organizadorDTO.getPassword());
        organizador.setTelefono(organizadorDTO.getTelefono());
        organizador.setRanking(organizadorDTO.getRanking());

        Organizador nuevoOrganizador = organizadorRepository.save(organizador);
        return mapToResponseDTO(nuevoOrganizador);
    }

    @Override
    public OrganizadorResponseDTO getOrganizadorById(Long id) {
        Organizador organizador = organizadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organizador no encontrado con ID: " + id));
        return mapToResponseDTO(organizador);
    }

    @Override
    @Transactional
    public OrganizadorResponseDTO updateOrganizador(Long id, OrganizadorUpdateDTO organizadorDTO) {
        Organizador organizador = organizadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organizador no encontrado con ID: " + id));

        if (organizadorDTO.getNombreCompleto() != null) {
            organizador.setNombreCompleto(organizadorDTO.getNombreCompleto());
        }
        if (organizadorDTO.getEmail() != null) {
            organizador.setEmail(organizadorDTO.getEmail());
        }
        if (organizadorDTO.getPassword() != null) {
            organizador.setPassword(organizadorDTO.getPassword());
        }
        if (organizadorDTO.getTelefono() != null) {
            organizador.setTelefono(organizadorDTO.getTelefono());
        }
        if (organizadorDTO.getRanking() != null) {
            organizador.setRanking(organizadorDTO.getRanking());
        }

        Organizador updatedOrganizador = organizadorRepository.save(organizador);
        return mapToResponseDTO(updatedOrganizador);
    }

    @Override
    @Transactional
    public void deleteOrganizador(Long id) {
        if (!organizadorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Organizador no encontrado con ID: " + id);
        }
        organizadorRepository.deleteById(id);
    }

    private OrganizadorResponseDTO mapToResponseDTO(Organizador organizador) {
        OrganizadorResponseDTO dto = new OrganizadorResponseDTO();
        dto.setId(organizador.getId());
        dto.setNombreCompleto(organizador.getNombreCompleto());
        dto.setEmail(organizador.getEmail());
        dto.setTelefono(organizador.getTelefono());
        dto.setRanking(organizador.getRanking());
        dto.setFechaCreacion(organizador.getFechaCreacion());
        return dto;
    }
}
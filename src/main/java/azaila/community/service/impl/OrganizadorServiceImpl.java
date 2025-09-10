package azaila.community.service.impl;

import azaila.community.dto.organizador.OrganizadorRequestDTO;
import azaila.community.dto.organizador.OrganizadorResponseDTO;
import azaila.community.dto.organizador.OrganizadorUpdateDTO;
import azaila.community.exception.ResourceNotFoundException;
import azaila.community.model.Organizador;
import azaila.community.model.Persona;
import azaila.community.model.PersonaIdentidad;
import azaila.community.repository.OrganizadorRepository;
import azaila.community.repository.PersonaIdentidadRepository;
import azaila.community.service.interfaces.OrganizadorService;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrganizadorServiceImpl implements OrganizadorService {

    private final OrganizadorRepository organizadorRepository;

    public OrganizadorServiceImpl(OrganizadorRepository organizadorRepository) {
        this.organizadorRepository = organizadorRepository;
    }

    @Autowired
    PersonaIdentidadRepository personaIdentidadRepository;

    // AUXILIAR
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
    public OrganizadorResponseDTO createOrganizador(OrganizadorRequestDTO dto) {
        if(dto.getNombreCompleto() == null || dto.getNombreCompleto().isBlank()){
            throw new IllegalArgumentException("El nombre completo es obligatorio");
        }
        if(dto.getEmail() == null && dto.getTelefono() == null){
            throw new IllegalArgumentException("Al menos un medio de contacto (email o teléfono) es obligatorio");
        }

        PersonaIdentidad identidad = findOrCreateIdentidad(dto.getEmail(), dto.getTelefono());

        Organizador organizador = organizadorRepository.findById(identidad.getId())
                .orElseGet(()->{
                    Organizador o = new Organizador();
                    o.setIdentidad(identidad);
                    return o;
                });

        organizador.setNombreCompleto(dto.getNombreCompleto());
        if(dto.getPassword() != null && !dto.getPassword().isBlank())
            organizador.setPassword(dto.getPassword());
        if(dto.getRanking() != null)
            organizador.setRanking(dto.getRanking());

        Organizador savedOrganizador = organizadorRepository.save(organizador);
        return mapToResponseDTO(savedOrganizador);
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
        if (organizadorDTO.getPassword() != null) {
            organizador.setPassword(organizadorDTO.getPassword());
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
        dto.setRanking(organizador.getRanking());
        return dto;
    }
}
package azaila.community.service.impl;

import azaila.community.dto.staff.StaffRequestDTO;
import azaila.community.dto.staff.StaffResponseDTO;
import azaila.community.dto.staff.StaffUpdateDTO;
import azaila.community.exception.ResourceNotFoundException;
import azaila.community.model.PersonaIdentidad;
import azaila.community.model.Staff;
import azaila.community.repository.PersonaIdentidadRepository;
import azaila.community.repository.StaffRepository;
import azaila.community.service.interfaces.StaffService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;

    public StaffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
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
    public StaffResponseDTO createStaff(StaffRequestDTO staffDTO) {
        // Validaciones

        PersonaIdentidad identidad = findOrCreateIdentidad(staffDTO.getEmail(), staffDTO.getTelefono());

        Staff staff = staffRepository.findById(identidad.getId())
                .orElseGet(()->{
                    Staff s = new Staff();
                    s.setIdentidad(identidad);
                    return s;
                });
        staff.setNombreCompleto(staffDTO.getNombreCompleto());
        staff.setPassword(staffDTO.getPassword());
        staff.setNumeroStaff(staffDTO.getNumeroStaff());
        Staff savedStaff = staffRepository.save(staff);
        return mapToResponseDTO(savedStaff);
    }

    @Override
    public StaffResponseDTO getStaffById(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff no encontrado con ID: " + id));
        return mapToResponseDTO(staff);
    }

    @Override
    @Transactional
    public StaffResponseDTO updateStaff(Long id, StaffUpdateDTO staffDTO) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff no encontrado con ID: " + id));

        if (staffDTO.getNombreCompleto() != null) {
            staff.setNombreCompleto(staffDTO.getNombreCompleto());
        }
        if (staffDTO.getPassword() != null) {
            staff.setPassword(staffDTO.getPassword());
        }
        if (staffDTO.getNumeroStaff() != null) {
            staff.setNumeroStaff(staffDTO.getNumeroStaff());
        }

        Staff updatedStaff = staffRepository.save(staff);
        return mapToResponseDTO(updatedStaff);
    }

    @Override
    @Transactional
    public void deleteStaff(Long id) {
        if (!staffRepository.existsById(id)) {
            throw new ResourceNotFoundException("Staff no encontrado con ID: " + id);
        }
        staffRepository.deleteById(id);
    }

    private StaffResponseDTO mapToResponseDTO(Staff staff) {
        StaffResponseDTO dto = new StaffResponseDTO();
        dto.setId(staff.getId());
        dto.setNombreCompleto(staff.getNombreCompleto());
        dto.setNumeroStaff(staff.getNumeroStaff());

        return dto;
    }
}
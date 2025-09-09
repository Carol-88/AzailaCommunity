package azaila.community.service.impl;

import azaila.community.dto.staff.StaffRequestDTO;
import azaila.community.dto.staff.StaffResponseDTO;
import azaila.community.dto.staff.StaffUpdateDTO;
import azaila.community.exception.ResourceNotFoundException;
import azaila.community.model.Staff;
import azaila.community.repository.StaffRepository;
import azaila.community.service.interfaces.StaffService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;

    public StaffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    @Transactional
    public StaffResponseDTO createStaff(StaffRequestDTO staffDTO) {
        if (staffDTO.getNombreCompleto() == null || staffDTO.getNombreCompleto().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo es obligatorio.");
        }
        if (staffDTO.getEmail() == null && staffDTO.getTelefono() == null) {
            throw new IllegalArgumentException("Se requiere al menos un email o un teléfono para el registro.");
        }

        Staff staff = new Staff();
        staff.setNombreCompleto(staffDTO.getNombreCompleto());
        staff.setEmail(staffDTO.getEmail());
        staff.setPassword(staffDTO.getPassword());
        staff.setTelefono(staffDTO.getTelefono());
        staff.setNumeroStaff(staffDTO.getNumeroStaff());
        staff.setNumeroEventosColaborados(0); // Valor inicial

        Staff nuevoStaff = staffRepository.save(staff);
        return mapToResponseDTO(nuevoStaff);
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
        if (staffDTO.getEmail() != null) {
            staff.setEmail(staffDTO.getEmail());
        }
        if (staffDTO.getPassword() != null) {
            staff.setPassword(staffDTO.getPassword());
        }
        if (staffDTO.getTelefono() != null) {
            staff.setTelefono(staffDTO.getTelefono());
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
        dto.setEmail(staff.getEmail());
        dto.setTelefono(staff.getTelefono());
        dto.setNumeroStaff(staff.getNumeroStaff());

        // Mapear el número de eventos colaborados
        if (staff.getEventosColaborados() != null) {
            dto.setNumeroEventosColaborados(staff.getEventosColaborados().size());
        } else {
            dto.setNumeroEventosColaborados(0);
        }

        dto.setFechaCreacion(staff.getFechaCreacion());
        return dto;
    }
}
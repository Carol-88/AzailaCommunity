package azaila.community.service.interfaces;

import azaila.community.dto.staff.StaffRequestDTO;
import azaila.community.dto.staff.StaffResponseDTO;
import azaila.community.dto.staff.StaffUpdateDTO;

public interface StaffService {
    StaffResponseDTO createStaff(StaffRequestDTO staffDTO);
    StaffResponseDTO getStaffById(Long id);
    StaffResponseDTO updateStaff(Long id, StaffUpdateDTO staffDTO);
    void deleteStaff(Long id);
}
package azaila.community.controller;

import azaila.community.dto.staff.StaffRequestDTO;
import azaila.community.dto.staff.StaffResponseDTO;
import azaila.community.dto.staff.StaffUpdateDTO;
import azaila.community.service.interfaces.StaffService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/staff")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @PostMapping
    public ResponseEntity<StaffResponseDTO> createStaff(@RequestBody StaffRequestDTO staffRequestDTO) {
        StaffResponseDTO nuevoStaff = staffService.createStaff(staffRequestDTO);
        return new ResponseEntity<>(nuevoStaff, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffResponseDTO> getStaffById(@PathVariable Long id) {
        StaffResponseDTO staff = staffService.getStaffById(id);
        return ResponseEntity.ok(staff);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StaffResponseDTO> updateStaff(@PathVariable Long id, @RequestBody StaffUpdateDTO staffUpdateDTO) {
        StaffResponseDTO staffActualizado = staffService.updateStaff(id, staffUpdateDTO);
        return ResponseEntity.ok(staffActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }
}
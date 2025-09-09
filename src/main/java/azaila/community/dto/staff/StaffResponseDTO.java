package azaila.community.dto.staff;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class StaffResponseDTO {
    private Long id;
    private String nombreCompleto;
    private String email;
    private String telefono;
    private Integer numeroStaff;
    private Integer numeroEventosColaborados;
    private LocalDateTime fechaCreacion;
}
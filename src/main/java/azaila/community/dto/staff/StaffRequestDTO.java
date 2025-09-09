package azaila.community.dto.staff;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StaffRequestDTO {
    private String nombreCompleto;
    private String email;
    private String password;
    private String telefono;
    private Integer numeroStaff;
}
package azaila.community.dto.organizador;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrganizadorRequestDTO {
    private String nombreCompleto;
    private String email;
    private String password;
    private String telefono;
    private Integer ranking;
}
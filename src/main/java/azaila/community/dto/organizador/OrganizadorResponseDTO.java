package azaila.community.dto.organizador;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OrganizadorResponseDTO {
    private Long id;
    private String nombreCompleto;
    private String email;
    private String telefono;
    private Integer ranking;
    private LocalDateTime fechaCreacion;
}
package azaila.community.dto.participante;

import azaila.community.enums.Dieta;
import azaila.community.enums.PrioridadDiscapacidad;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ParticipanteResponseDTO {
    private Long id;
    private String nombreCompleto;
    private String email;
    private String telefono;
    private PrioridadDiscapacidad prioridadDiscapacidad;
    private Dieta dieta;
    private LocalDateTime fechaCreacion;
}
package azaila.community.dto.participante;

import azaila.community.enums.Dieta;
import azaila.community.enums.PrioridadDiscapacidad;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ParticipanteUpdateDTO {
    private String nombreCompleto;
    private String email;
    private String password;
    private String telefono;
    private PrioridadDiscapacidad prioridadDiscapacidad;
    private Dieta dieta;
}
package azaila.community.model;

import azaila.community.enums.Dieta;
import azaila.community.enums.PrioridadDiscapacidad;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Participante extends Persona{
    private PrioridadDiscapacidad prioridadDiscapacidad;
    private Dieta dieta;
}

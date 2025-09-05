package azaila.community.model;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Participante extends Persona{
    private String prioridad;
    private String dieta;

    /*
    ¿Una persona puede ser a la vez organizador, participante y staff? -> Sí
    ¿Una persona puede ser a la vez organizador, participante y staff en el mismo evento? -> No

    LA MISMA PERSONA -> EL MISMO REGISTRO EN LA DB
     */
}

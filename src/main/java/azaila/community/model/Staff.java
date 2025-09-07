package azaila.community.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Staff extends Persona {
    private Integer numeroStaff;
    private Integer numeroEventosColaborados;

    @ManyToMany(mappedBy = "staff")
    private Set<Evento> eventosColaborados = new HashSet<>();
}


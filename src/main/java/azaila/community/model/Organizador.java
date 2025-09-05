package azaila.community.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Organizador extends Persona{
    private String nombreOrganizacion;
    private Integer ranking;

    @OneToMany(mappedBy = "organizador")
    private Set<Evento> eventosCreados = new HashSet<>();

//    public void addEvento(Evento evento) {
//        this.eventosCreados.add(evento);
//        evento.setOrganizador(this);
//    }
}

package azaila.community.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Organizador extends Persona {
    private Integer ranking;

    @OneToMany(mappedBy = "organizador", fetch = FetchType.LAZY)
    private Set<Evento> eventosCreados = new HashSet<>();


    public void addEvento(Evento evento) {
        this.eventosCreados.add(evento);
        evento.setOrganizador(this);
    }
}
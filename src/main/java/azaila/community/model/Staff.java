package azaila.community.model;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Staff extends Persona {
    private Integer numeroStaff;
}


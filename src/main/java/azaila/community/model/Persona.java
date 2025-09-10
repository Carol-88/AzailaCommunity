package azaila.community.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreCompleto;

    @Column(nullable = true)
    private String password;


    @Column(nullable = true)
    private LocalDate fechaNacimiento;

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name = "id")
    private PersonaIdentidad identidad;

}
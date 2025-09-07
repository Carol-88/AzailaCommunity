package azaila.community.dto.evento;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class EventoRequestDTO {
    private String titulo;
    private String descripcion;
    private String ubicacion;
    private LocalDate fecha;
    private LocalTime hora;
    private Integer votaciones;
    private Boolean requiereInscripcion;
    private Integer minimoParticipantes;
    private Integer maximoParticipantes;
    private Long organizadorId; // ID del organizador que crea el evento
}

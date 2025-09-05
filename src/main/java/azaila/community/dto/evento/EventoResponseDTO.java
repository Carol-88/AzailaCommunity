package azaila.community.dto.evento;

import azaila.community.enums.EstadoEvento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class EventoResponseDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String ubicacion;
    private LocalDate fecha;
    private LocalTime hora;
    private Integer minimoParticipantes;
    private Integer maximoParticipantes;
    private EstadoEvento estado;
    private Long organizadorId;
    private int participantesActuales; // Campo para mostrar el conteo de participantes
}

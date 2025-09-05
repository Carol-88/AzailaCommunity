package azaila.community.service.interfaces;

import azaila.community.dto.evento.EventoRequestDTO;
import azaila.community.dto.evento.EventoResponseDTO;
import azaila.community.dto.evento.EventoUpdateDTO;

import java.util.List;

public interface EventoService {
    EventoResponseDTO createEvento(EventoRequestDTO eventoRequestDTO);
    List<EventoResponseDTO> getAllEventos();
    EventoResponseDTO getEventoById(Long id);
    EventoResponseDTO updateEvento(Long id, EventoUpdateDTO eventoUpdateDTO);
    void deleteEvento(Long id);
    void apuntarseAEvento(Long eventoId, Long personaId);
    void desapuntarseDeEvento(Long eventoId, Long personaId);
}

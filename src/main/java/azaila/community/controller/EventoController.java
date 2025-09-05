package azaila.community.controller;

import azaila.community.dto.evento.EventoRequestDTO;
import azaila.community.dto.evento.EventoResponseDTO;
import azaila.community.dto.evento.EventoUpdateDTO;
import azaila.community.service.interfaces.EventoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/eventos")
public class EventoController {

    private final EventoService eventoService;

    // Inyección de dependencias por constructor (mejor práctica)
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping
    public ResponseEntity<EventoResponseDTO> createEvento(@RequestBody EventoRequestDTO eventoRequestDTO) {
        EventoResponseDTO nuevoEvento = eventoService.createEvento(eventoRequestDTO);
        return new ResponseEntity<>(nuevoEvento, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> getAllEventos() {
        List<EventoResponseDTO> eventos = eventoService.getAllEventos();
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> getEventoById(@PathVariable Long id) {
        EventoResponseDTO evento = eventoService.getEventoById(id);
        return ResponseEntity.ok(evento);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> updateEvento(@PathVariable Long id, @RequestBody EventoUpdateDTO eventoUpdateDTO) {
        EventoResponseDTO updatedEvento = eventoService.updateEvento(id, eventoUpdateDTO);
        return ResponseEntity.ok(updatedEvento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id) {
        eventoService.deleteEvento(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{eventoId}/apuntarse/{personaId}")
    public ResponseEntity<Void> apuntarseAEvento(@PathVariable Long eventoId, @PathVariable Long personaId) {
        eventoService.apuntarseAEvento(eventoId, personaId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{eventoId}/desapuntarse/{personaId}")
    public ResponseEntity<Void> desapuntarseDeEvento(@PathVariable Long eventoId, @PathVariable Long personaId) {
        eventoService.desapuntarseDeEvento(eventoId, personaId);
        return ResponseEntity.ok().build();
    }
}
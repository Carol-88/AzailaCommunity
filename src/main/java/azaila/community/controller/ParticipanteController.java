package azaila.community.controller;

import azaila.community.dto.participante.ParticipanteRequestDTO;
import azaila.community.dto.participante.ParticipanteResponseDTO;
import azaila.community.dto.participante.ParticipanteUpdateDTO;
import azaila.community.service.interfaces.ParticipanteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/participantes")
public class ParticipanteController {

    private final ParticipanteService participanteService;

    public ParticipanteController(ParticipanteService participanteService) {
        this.participanteService = participanteService;
    }

    @PostMapping
    public ResponseEntity<ParticipanteResponseDTO> createParticipante(@RequestBody ParticipanteRequestDTO participanteRequestDTO) {
        ParticipanteResponseDTO nuevoParticipante = participanteService.createParticipante(participanteRequestDTO);
        return new ResponseEntity<>(nuevoParticipante, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteResponseDTO> getParticipanteById(@PathVariable Long id) {
        ParticipanteResponseDTO participante = participanteService.getParticipanteById(id);
        return ResponseEntity.ok(participante);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ParticipanteResponseDTO> updateParticipante(@PathVariable Long id, @RequestBody ParticipanteUpdateDTO participanteUpdateDTO) {
        ParticipanteResponseDTO participanteActualizado = participanteService.updateParticipante(id, participanteUpdateDTO);
        return ResponseEntity.ok(participanteActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipante(@PathVariable Long id) {
        participanteService.deleteParticipante(id);
        return ResponseEntity.noContent().build();
    }
}
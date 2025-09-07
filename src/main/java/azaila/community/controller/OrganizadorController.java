package azaila.community.controller;

import azaila.community.dto.organizador.OrganizadorRequestDTO;
import azaila.community.dto.organizador.OrganizadorResponseDTO;
import azaila.community.dto.organizador.OrganizadorUpdateDTO;
import azaila.community.service.interfaces.OrganizadorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/organizadores")
public class OrganizadorController {

    private final OrganizadorService organizadorService;

    public OrganizadorController(OrganizadorService organizadorService) {
        this.organizadorService = organizadorService;
    }

    @PostMapping
    public ResponseEntity<OrganizadorResponseDTO> createOrganizador(@RequestBody OrganizadorRequestDTO organizadorRequestDTO) {
        OrganizadorResponseDTO nuevoOrganizador = organizadorService.createOrganizador(organizadorRequestDTO);
        return new ResponseEntity<>(nuevoOrganizador, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizadorResponseDTO> getOrganizadorById(@PathVariable Long id) {
        OrganizadorResponseDTO organizador = organizadorService.getOrganizadorById(id);
        return ResponseEntity.ok(organizador);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrganizadorResponseDTO> updateOrganizador(@PathVariable Long id, @RequestBody OrganizadorUpdateDTO organizadorUpdateDTO) {
        OrganizadorResponseDTO organizadorActualizado = organizadorService.updateOrganizador(id, organizadorUpdateDTO);
        return ResponseEntity.ok(organizadorActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizador(@PathVariable Long id) {
        organizadorService.deleteOrganizador(id);
        return ResponseEntity.noContent().build();
    }
}
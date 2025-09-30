package azaila.community.service.impl;

import azaila.community.dto.evento.EventoRequestDTO;
import azaila.community.dto.evento.EventoResponseDTO;
import azaila.community.dto.evento.EventoUpdateDTO;
import azaila.community.enums.EstadoEvento;
import azaila.community.exception.ResourceNotFoundException;
import azaila.community.model.Evento;
import azaila.community.model.Organizador;
import azaila.community.repository.EventoRepository;
import azaila.community.repository.OrganizadorRepository;
import azaila.community.repository.PersonaIdentidadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventoServiceImplTest {

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private OrganizadorRepository organizadorRepository;

    @Mock
    private PersonaIdentidadRepository personaIdentidadRepository;

    @InjectMocks
    private EventoServiceImpl eventoService;

    private Organizador organizador;
    private Evento evento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        organizador = new Organizador();
        organizador.setId(1L);

        evento = new Evento();
        evento.setId(10L);
        evento.setTitulo("Titulo");
        evento.setDescripcion("Descripcion");
        evento.setUbicacion("Ubicacion");
        evento.setFecha(LocalDate.now());
        evento.setHora(LocalTime.NOON);
        evento.setMinimoParticipantes(1);
        evento.setMaximoParticipantes(10);
        evento.setEstado(EstadoEvento.PENDIENTE);
        evento.setOrganizador(organizador);
        evento.setParticipantes(new HashSet<>());
    }

    // ----------------------------
    // createEvento
    // ----------------------------
    @Test
    void createEvento_ok() {
        EventoRequestDTO request = new EventoRequestDTO();
        request.setOrganizadorId(1L);
        request.setTitulo("Nuevo Evento");

        when(organizadorRepository.findById(1L)).thenReturn(Optional.of(organizador));
        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);

        EventoResponseDTO response = eventoService.createEvento(request);

        assertNotNull(response);
        assertEquals("Titulo", response.getTitulo());
        verify(eventoRepository, times(1)).save(any(Evento.class));
    }

    @Test
    void createEvento_organizadorNoEncontrado() {
        EventoRequestDTO request = new EventoRequestDTO();
        request.setOrganizadorId(99L);

        when(organizadorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> eventoService.createEvento(request));
    }

    // ----------------------------
    // getAllEventos
    // ----------------------------
    @Test
    void getAllEventos_ok() {
        when(eventoRepository.findAll()).thenReturn(Collections.singletonList(evento));

        var result = eventoService.getAllEventos();

        assertEquals(1, result.size());
        assertEquals("Titulo", result.get(0).getTitulo());
    }

    // ----------------------------
    // getEventoById
    // ----------------------------
    @Test
    void getEventoById_ok() {
        when(eventoRepository.findById(10L)).thenReturn(Optional.of(evento));

        EventoResponseDTO response = eventoService.getEventoById(10L);

        assertEquals("Titulo", response.getTitulo());
    }

    @Test
    void getEventoById_noEncontrado() {
        when(eventoRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> eventoService.getEventoById(10L));
    }

    // ----------------------------
    // updateEvento
    // ----------------------------
    @Test
    void updateEvento_ok() {
        EventoUpdateDTO updateDTO = new EventoUpdateDTO();
        updateDTO.setTitulo("Nuevo Titulo");
        updateDTO.setDescripcion("Nueva descripcion");
        updateDTO.setEstado(EstadoEvento.CANCELADO);

        when(eventoRepository.findById(10L)).thenReturn(Optional.of(evento));
        when(eventoRepository.save(any(Evento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        EventoResponseDTO response = eventoService.updateEvento(10L, updateDTO);

        assertEquals("Nuevo Titulo", response.getTitulo()); // ✅ ahora se espera el valor actualizado
        assertEquals("Nueva descripcion", response.getDescripcion());
        assertEquals(EstadoEvento.CANCELADO, response.getEstado());
        verify(eventoRepository, times(1)).save(evento);
    }


    @Test
    void updateEvento_noEncontrado() {
        when(eventoRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> eventoService.updateEvento(10L, new EventoUpdateDTO()));
    }

    // ----------------------------
    // deleteEvento
    // ----------------------------
    @Test
    void deleteEvento_ok() {
        when(eventoRepository.existsById(10L)).thenReturn(true);

        eventoService.deleteEvento(10L);

        verify(eventoRepository, times(1)).deleteById(10L);
    }

    @Test
    void deleteEvento_noEncontrado() {
        when(eventoRepository.existsById(10L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> eventoService.deleteEvento(10L));
    }

    // ----------------------------
    // apuntarseAEvento
    // ----------------------------
    @Test
    void apuntarseAEvento_ok() {
        when(eventoRepository.findById(10L)).thenReturn(Optional.of(evento));

        eventoService.apuntarseAEvento(10L, 20L);

        verify(eventoRepository, times(1)).save(evento);
    }

    @Test
    void apuntarseAEvento_noEncontrado() {
        when(eventoRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> eventoService.apuntarseAEvento(10L, 20L));
    }

    // ----------------------------
    // desapuntarseDeEvento
    // ----------------------------
    @Test
    void desapuntarseDeEvento_ok() {
        when(eventoRepository.findById(10L)).thenReturn(Optional.of(evento));

        eventoService.desapuntarseDeEvento(10L, 20L);

        verify(eventoRepository, times(1)).save(evento);
    }

    @Test
    void desapuntarseDeEvento_noEncontrado() {
        when(eventoRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> eventoService.desapuntarseDeEvento(10L, 20L));
    }
}

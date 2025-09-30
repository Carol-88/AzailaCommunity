package azaila.community.controller;

import azaila.community.dto.evento.EventoRequestDTO;
import azaila.community.dto.evento.EventoResponseDTO;
import azaila.community.dto.evento.EventoUpdateDTO;
import azaila.community.service.interfaces.EventoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EventoControllerTest {

    @Mock
    private EventoService eventoService;

    @InjectMocks
    private EventoController eventoController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventoController).build();
        objectMapper = new ObjectMapper();
    }

    // ----------------------------
    // UNIT TESTS (con Mockito)
    // ----------------------------
    @Nested
    class UnitTests {

        @Test
        void createEvento() {
            EventoRequestDTO request = new EventoRequestDTO();
            EventoResponseDTO response = new EventoResponseDTO();
            when(eventoService.createEvento(ArgumentMatchers.any(EventoRequestDTO.class))).thenReturn(response);

            ResponseEntity<EventoResponseDTO> result = eventoController.createEvento(request);

            assertEquals(HttpStatus.CREATED, result.getStatusCode());
            assertEquals(response, result.getBody());
            verify(eventoService, times(1)).createEvento(request);
        }

        @Test
        void getAllEventos() {
            List<EventoResponseDTO> eventos = Arrays.asList(new EventoResponseDTO(), new EventoResponseDTO());
            when(eventoService.getAllEventos()).thenReturn(eventos);

            ResponseEntity<List<EventoResponseDTO>> result = eventoController.getAllEventos();

            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals(2, result.getBody().size());
            verify(eventoService, times(1)).getAllEventos();
        }

        @Test
        void getEventoById() {
            EventoResponseDTO evento = new EventoResponseDTO();
            when(eventoService.getEventoById(1L)).thenReturn(evento);

            ResponseEntity<EventoResponseDTO> result = eventoController.getEventoById(1L);

            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals(evento, result.getBody());
            verify(eventoService, times(1)).getEventoById(1L);
        }

        @Test
        void updateEvento() {
            EventoUpdateDTO update = new EventoUpdateDTO();
            EventoResponseDTO updated = new EventoResponseDTO();
            when(eventoService.updateEvento(1L, update)).thenReturn(updated);

            ResponseEntity<EventoResponseDTO> result = eventoController.updateEvento(1L, update);

            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals(updated, result.getBody());
            verify(eventoService, times(1)).updateEvento(1L, update);
        }

        @Test
        void deleteEvento() {
            ResponseEntity<Void> result = eventoController.deleteEvento(1L);

            assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
            verify(eventoService, times(1)).deleteEvento(1L);
        }

        @Test
        void apuntarseAEvento() {
            ResponseEntity<Void> result = eventoController.apuntarseAEvento(1L, 2L);

            assertEquals(HttpStatus.OK, result.getStatusCode());
            verify(eventoService, times(1)).apuntarseAEvento(1L, 2L);
        }

        @Test
        void desapuntarseDeEvento() {
            ResponseEntity<Void> result = eventoController.desapuntarseDeEvento(1L, 2L);

            assertEquals(HttpStatus.OK, result.getStatusCode());
            verify(eventoService, times(1)).desapuntarseDeEvento(1L, 2L);
        }
    }

    // ----------------------------
    // INTEGRATION TESTS (con MockMvc)
    // ----------------------------
    @Nested
    class IntegrationTests {

        @Test
        void createEvento() throws Exception {
            EventoRequestDTO request = new EventoRequestDTO();
            EventoResponseDTO response = new EventoResponseDTO();

            when(eventoService.createEvento(any(EventoRequestDTO.class))).thenReturn(response);

            mockMvc.perform(post("/api/v1/eventos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated());
        }

        @Test
        void getAllEventos() throws Exception {
            when(eventoService.getAllEventos()).thenReturn(Arrays.asList(new EventoResponseDTO()));

            mockMvc.perform(get("/api/v1/eventos"))
                    .andExpect(status().isOk());
        }

        @Test
        void getEventoById() throws Exception {
            when(eventoService.getEventoById(1L)).thenReturn(new EventoResponseDTO());

            mockMvc.perform(get("/api/v1/eventos/1"))
                    .andExpect(status().isOk());
        }

        @Test
        void updateEvento() throws Exception {
            EventoUpdateDTO update = new EventoUpdateDTO();
            when(eventoService.updateEvento(eq(1L), any(EventoUpdateDTO.class)))
                    .thenReturn(new EventoResponseDTO());

            mockMvc.perform(patch("/api/v1/eventos/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(update)))
                    .andExpect(status().isOk());
        }

        @Test
        void deleteEvento() throws Exception {
            mockMvc.perform(delete("/api/v1/eventos/1"))
                    .andExpect(status().isNoContent());
        }

        @Test
        void apuntarseAEvento() throws Exception {
            mockMvc.perform(post("/api/v1/eventos/1/apuntarse/2"))
                    .andExpect(status().isOk());
        }

        @Test
        void desapuntarseDeEvento() throws Exception {
            mockMvc.perform(delete("/api/v1/eventos/1/desapuntarse/2"))
                    .andExpect(status().isOk());
        }
    }
}

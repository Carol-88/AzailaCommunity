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
import azaila.community.repository.PersonaRepository;
import azaila.community.service.interfaces.EventoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoServiceImpl implements EventoService {


    private final EventoRepository eventoRepository;

    private final OrganizadorRepository organizadorRepository;

    private final PersonaRepository personaRepository;

    public EventoServiceImpl(EventoRepository eventoRepository, OrganizadorRepository organizadorRepository, PersonaRepository personaRepository) {
        this.eventoRepository = eventoRepository;
        this.organizadorRepository = organizadorRepository;
        this.personaRepository = personaRepository;
    }

    @Override
    @Transactional
    public EventoResponseDTO createEvento(EventoRequestDTO eventoRequestDTO) {
        Organizador organizador = organizadorRepository.findById(eventoRequestDTO.getOrganizadorId())
                .orElseThrow(() -> new ResourceNotFoundException("Organizador no encontrado con ID: " + eventoRequestDTO.getOrganizadorId()));

        Evento evento = new Evento();
        evento.setTitulo(eventoRequestDTO.getTitulo());
        evento.setDescripcion(eventoRequestDTO.getDescripcion());
        evento.setUbicacion(eventoRequestDTO.getUbicacion());
        evento.setFecha(eventoRequestDTO.getFecha());
        evento.setHora(eventoRequestDTO.getHora());
        evento.setMinimoParticipantes(eventoRequestDTO.getMinimoParticipantes());
        evento.setMaximoParticipantes(eventoRequestDTO.getMaximoParticipantes());
        evento.setEstado(EstadoEvento.PENDIENTE); // Estado inicial
        evento.setFechaCreacion(LocalDateTime.now());
        evento.setOrganizador(organizador);

        Evento savedEvento = eventoRepository.save(evento);
        return mapToResponseDTO(savedEvento);
    }

    @Override
    public List<EventoResponseDTO> getAllEventos() {
        return eventoRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EventoResponseDTO getEventoById(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + id));
        return mapToResponseDTO(evento);
    }


    @Override
    public EventoResponseDTO updateEvento(Long id, EventoUpdateDTO eventoUpdateDTO) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + id));

        if (eventoUpdateDTO.getTitulo() != null) {
            evento.setTitulo(eventoUpdateDTO.getTitulo());
        }
        if (eventoUpdateDTO.getDescripcion() != null) {
            evento.setDescripcion(eventoUpdateDTO.getDescripcion());
        }
        if (eventoUpdateDTO.getUbicacion() != null) {
            evento.setUbicacion(eventoUpdateDTO.getUbicacion());
        }
        if (eventoUpdateDTO.getFecha() != null) {
            evento.setFecha(eventoUpdateDTO.getFecha());
        }
        if (eventoUpdateDTO.getHora() != null) {
            evento.setHora(eventoUpdateDTO.getHora());
        }
        if (eventoUpdateDTO.getMinimoParticipantes() != null) {
            evento.setMinimoParticipantes(eventoUpdateDTO.getMinimoParticipantes());
        }
        if (eventoUpdateDTO.getMaximoParticipantes() != null) {
            evento.setMaximoParticipantes(eventoUpdateDTO.getMaximoParticipantes());
        }
        if (eventoUpdateDTO.getEstado() != null) {
            evento.setEstado(eventoUpdateDTO.getEstado());
        }
        Evento updatedEvento = eventoRepository.save(evento);
        return mapToResponseDTO(updatedEvento);
    }

    @Override
    @Transactional
    public void deleteEvento(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Evento no encontrado con ID: " + id);
        }
        eventoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void apuntarseAEvento(Long eventoId, Long personaId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + eventoId));
        eventoRepository.save(evento);
    }

    @Override
    @Transactional
    public void desapuntarseDeEvento(Long eventoId, Long personaId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + eventoId));
        eventoRepository.save(evento);
    }

    private EventoResponseDTO mapToResponseDTO(Evento evento) {
        EventoResponseDTO dto = new EventoResponseDTO();
        dto.setId(evento.getId());
        dto.setTitulo(evento.getTitulo());
        dto.setDescripcion(evento.getDescripcion());
        dto.setUbicacion(evento.getUbicacion());
        dto.setFecha(evento.getFecha());
        dto.setHora(evento.getHora());
        dto.setMinimoParticipantes(evento.getMinimoParticipantes());
        dto.setMaximoParticipantes(evento.getMaximoParticipantes());
        dto.setEstado(evento.getEstado());
        dto.setOrganizadorId(evento.getOrganizador().getId());
        dto.setParticipantesActuales(evento.getParticipantes().size());
        return dto;
    }
}
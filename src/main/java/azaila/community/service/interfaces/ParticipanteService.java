package azaila.community.service.interfaces;

import azaila.community.dto.participante.ParticipanteRequestDTO;
import azaila.community.dto.participante.ParticipanteResponseDTO;
import azaila.community.dto.participante.ParticipanteUpdateDTO;

public interface ParticipanteService {
    ParticipanteResponseDTO createParticipante(ParticipanteRequestDTO participanteDTO);
    ParticipanteResponseDTO getParticipanteById(Long id);
    ParticipanteResponseDTO updateParticipante(Long id, ParticipanteUpdateDTO participanteDTO);
    void deleteParticipante(Long id);
}
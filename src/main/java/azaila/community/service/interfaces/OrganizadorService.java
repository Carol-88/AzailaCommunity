package azaila.community.service.interfaces;

import azaila.community.dto.organizador.OrganizadorRequestDTO;
import azaila.community.dto.organizador.OrganizadorResponseDTO;
import azaila.community.dto.organizador.OrganizadorUpdateDTO;

public interface OrganizadorService {
    OrganizadorResponseDTO createOrganizador(OrganizadorRequestDTO organizadorDTO);
    OrganizadorResponseDTO getOrganizadorById(Long id);
    OrganizadorResponseDTO updateOrganizador(Long id, OrganizadorUpdateDTO organizadorDTO);
    void deleteOrganizador(Long id);
}
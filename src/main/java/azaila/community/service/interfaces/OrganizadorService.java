package azaila.community.service.interfaces;



import azaila.community.dto.organizador.OrganizadorRequestDTO;
import azaila.community.dto.organizador.OrganizadorResponseDTO;
import azaila.community.dto.organizador.OrganizadorUpdateDTO;

import java.util.List;

public interface OrganizadorService {
    OrganizadorResponseDTO createOrganizador(OrganizadorRequestDTO organizadorRequestDTO);
    OrganizadorResponseDTO getOrganizadorById(Long id);
    List<OrganizadorResponseDTO> getAllOrganizadores();
    OrganizadorResponseDTO updateOrganizador(Long id, OrganizadorUpdateDTO organizadorUpdateDTO);
    void deleteOrganizador(Long id);
}

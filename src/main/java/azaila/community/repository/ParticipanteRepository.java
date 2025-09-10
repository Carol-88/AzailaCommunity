package azaila.community.repository;

import azaila.community.model.Participante;
import azaila.community.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
    Optional<Participante> findByIdentidad_Email(String email);
    Optional<Participante> findByIdentidad_Telefono(String telefono);
}
package azaila.community.repository;

import azaila.community.model.Participante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitanteRepository extends JpaRepository<Participante, Long> {
}

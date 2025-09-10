package azaila.community.repository;

import azaila.community.model.PersonaIdentidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonaIdentidadRepository extends JpaRepository<PersonaIdentidad, Long> {
    boolean existsByEmail(String email);
    boolean existsByTelefono(String telefono);
    Optional<PersonaIdentidad> findByEmail(String email);
    Optional<PersonaIdentidad> findByTelefono(String telefono);
}

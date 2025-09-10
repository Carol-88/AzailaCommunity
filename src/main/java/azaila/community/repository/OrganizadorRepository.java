package azaila.community.repository;

import azaila.community.model.Organizador;
import azaila.community.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizadorRepository extends JpaRepository<Organizador, Long> {
    Optional<Organizador> findByIdentidad_Email(String email);
    Optional<Organizador> findByIdentidad_Telefono(String telefono);
}
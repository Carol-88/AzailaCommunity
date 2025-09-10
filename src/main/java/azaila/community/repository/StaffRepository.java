package azaila.community.repository;

import azaila.community.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByIdentidad_Email(String email);
    Optional<Staff> findByIdentidad_Telefono(String telefono);
}
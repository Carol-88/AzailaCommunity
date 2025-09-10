 package azaila.community.repository;

 import azaila.community.model.Persona;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;

 @Repository
 public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Persona findByEmail(String email);
    Persona findByTelefono(String telefono);
 }

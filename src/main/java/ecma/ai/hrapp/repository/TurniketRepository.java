package ecma.ai.hrapp.repository;

import ecma.ai.hrapp.entity.Company;
import ecma.ai.hrapp.entity.Turniket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TurniketRepository extends JpaRepository<Turniket, UUID> {
//    Optional<User> findByEmail(String email);
}

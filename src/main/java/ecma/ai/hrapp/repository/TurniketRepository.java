package ecma.ai.hrapp.repository;

import ecma.ai.hrapp.entity.Company;
import ecma.ai.hrapp.entity.Turniket;
import ecma.ai.hrapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TurniketRepository extends JpaRepository<Turniket, UUID> {
    //    Optional<Turniket> findByEmail(String email);
    Optional<Turniket> findAllByOwner(User owner);

    Optional<Turniket> findByNumber(String number);

}

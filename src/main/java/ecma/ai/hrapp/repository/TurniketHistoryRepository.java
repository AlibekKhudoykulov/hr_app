package ecma.ai.hrapp.repository;

import ecma.ai.hrapp.entity.Turniket;
import ecma.ai.hrapp.entity.TurniketHistory;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TurniketHistoryRepository extends JpaRepository<TurniketHistory, UUID> {
//    Optional<User> findByEmail(String email);
//        List<TurniketHistory> findAllByTurniket(Turniket turniket);
        List<TurniketHistory> findAllByTurniketAndTimeIsBetween(Turniket turniket, Timestamp time, Timestamp time2);
}

package ecma.ai.hrapp.repository;

import ecma.ai.hrapp.entity.Role;
import ecma.ai.hrapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.Email;
import java.util.*;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    Optional<User> findByEmailAndVerifyCode(@Email String email, String verifyCode);

}

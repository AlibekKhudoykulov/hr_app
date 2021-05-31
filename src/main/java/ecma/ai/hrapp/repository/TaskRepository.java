package ecma.ai.hrapp.repository;


import ecma.ai.hrapp.entity.Task;
import ecma.ai.hrapp.entity.User;
import ecma.ai.hrapp.payload.TaskDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
//    Optional<User> findByEmail(String email);

     List<Task> findAllByTaskTaker(User taskTaker);
     List<Task> findAllByTaskGiver(User taskGiver);
//     List<Task> findAllByTaskTakerAndCompletedDateBetween(User taskTaker, Timestamp completedDate, Timestamp completedDate2);
}

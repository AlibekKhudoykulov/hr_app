package ecma.ai.hrapp.service;

import ecma.ai.hrapp.component.Checker;
import ecma.ai.hrapp.component.MailSender;
import ecma.ai.hrapp.entity.Role;
import ecma.ai.hrapp.entity.Task;
import ecma.ai.hrapp.entity.User;
import ecma.ai.hrapp.entity.enums.TaskStatus;
import ecma.ai.hrapp.payload.ApiResponse;
import ecma.ai.hrapp.payload.TaskDto;
import ecma.ai.hrapp.payload.TaskGetDto;
import ecma.ai.hrapp.repository.TaskRepository;
import ecma.ai.hrapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.util.*;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    Checker checker;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MailSender mailSender;

    public ApiResponse add(@RequestBody TaskDto taskDto) {
        User userGiver = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> byEmailUser = userRepository.findByEmail(taskDto.getTaskTakerEmail());
        if (!byEmailUser.isPresent()) return new ApiResponse("User not found", false);
        User user = byEmailUser.get();
        if (user.getEmail().equals(taskDto.getTaskTakerEmail()))
            return new ApiResponse("Taker and giver same person", false);
        //Huquqlarni tekshiramiza
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            boolean check = checker.check(role.getName().name());
            if (!check) return new ApiResponse("You have not such rights", false);
        }
        //Userga bajarilmagan tasklarni borligini tekshirish
        List<Task> allByTaskTaker = taskRepository.findAllByTaskTaker(user);
        if (allByTaskTaker != null) {
            for (Task task : allByTaskTaker) {
                if (!task.getStatus().name().equals(TaskStatus.COMPLETED.name()))
                    return new ApiResponse("User have a uncompleted task", false);
            }
        }
        Task task = new Task();
        task.setName(taskDto.getName());
        task.setTaskGiver(userGiver);
        task.setTaskTaker(user);
        task.setDescription(taskDto.getDescription());
        task.setDeadline(taskDto.getDeadline());
        task.setStatus(taskDto.getStatus());
        Task save = taskRepository.save(task);

        try {
            boolean send = mailSender.mailTextAddTask(save.getTaskTaker().getEmail(), save.getName());
            return new ApiResponse("Task saved successfully and sent email", true);
        } catch (MessagingException e) {
            return new ApiResponse("Task saved and not send email", true);
        }
    }
    //task ni olganlar huquqlar buyicha
    public List<TaskGetDto> getAllTaskTaker() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Task> allByTaskTaker = taskRepository.findAllByTaskTaker(user);
        List<TaskGetDto> taskDtos = new ArrayList<>();
        for (Task task : allByTaskTaker) {
            TaskGetDto taskDto = new TaskGetDto(task.getName(), task.getDescription(),
                    task.getDeadline(), task.getTaskGiver().getUsername(),
                    task.getTaskGiver().getEmail(), task.getTaskTaker().getUsername(),
                    task.getTaskTaker().getEmail(), task.getStatus());
            taskDtos.add(taskDto);
        }

        return taskDtos;
    }
    public ApiResponse getTaskFrom(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> byId = userRepository.findById(user.getId());
        if (!byId.isPresent()) return new ApiResponse("User not found",false);
        User user1 = byId.get();
        List<Task> allByTaskGiver = taskRepository.findAllByTaskGiver(user1);
        List<TaskGetDto> taskGetDtos=new ArrayList<>();
        for (Task task : allByTaskGiver) {
            TaskGetDto taskGetDto=new TaskGetDto(task.getName(), task.getDescription(),
                    task.getDeadline(), task.getTaskGiver().getUsername(),
                    task.getTaskGiver().getEmail(), task.getTaskTaker().getUsername(),
                    task.getTaskTaker().getEmail(), task.getStatus());
            taskGetDtos.add(taskGetDto);
        }
        return new ApiResponse("Task List",true,taskGetDtos);

    }

    public ApiResponse completedTask(UUID id, TaskDto taskDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> byId = userRepository.findById(user.getId());
        if (!byId.isPresent()) return new ApiResponse("User not found", false);
        Optional<Task> task = taskRepository.findById(id);
        if (!task.isPresent()) return new ApiResponse("Task not found", false);
        Task task1 = task.get();
        if (task1.getTaskTaker().getEmail().equals(byId.get().getEmail()))
            return new ApiResponse("The does not belong to you", false);
        task1.setStatus(taskDto.getStatus());
        if (taskDto.getStatus().name().equals(TaskStatus.COMPLETED.name())) {
            task1.setCompletedDate(new Timestamp(System.currentTimeMillis()));
        }
        Task save = taskRepository.save(task1);
        if (save.getStatus().name().equals(TaskStatus.COMPLETED.name())){
            try {
                boolean b = mailSender.taskCompleted(save.getTaskGiver().getEmail(), save.getTaskTaker().getEmail(), save.getName());
                if (b) return new ApiResponse("Task completed and email sent",true);
            } catch (MessagingException e) {
                return new ApiResponse("Task completed but some errors sending mail",true);
            }

        }
        return new ApiResponse("Task edited",true);
    }
}

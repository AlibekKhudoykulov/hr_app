package ecma.ai.hrapp.controller;

import ecma.ai.hrapp.entity.Task;
import ecma.ai.hrapp.payload.ApiResponse;
import ecma.ai.hrapp.payload.TaskDto;
import ecma.ai.hrapp.payload.TaskGetDto;
import ecma.ai.hrapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @PostMapping
    public HttpEntity<?> add(@RequestBody TaskDto taskDto){
        ApiResponse add = taskService.add(taskDto);
        return ResponseEntity.status(add.isSuccess()? HttpStatus.OK:HttpStatus.BAD_REQUEST).body(add);
    }
    @GetMapping("/ge/taskTaker")
    public HttpEntity<?> getTaker(){
        List<TaskGetDto> allTasks = taskService.getAllTaskTaker();
        return ResponseEntity.ok(allTasks);
    }
    @GetMapping("/get/taskGiver")
    public HttpEntity<?> getGiver(){
       ApiResponse allTasks = taskService.getTaskFrom();
        return ResponseEntity.status(allTasks.isSuccess()?200:400).body(allTasks);
    }
    @PostMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id,@RequestBody TaskDto taskDto){
        ApiResponse apiResponse = taskService.completedTask(id, taskDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }

}

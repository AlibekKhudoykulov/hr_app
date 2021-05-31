package ecma.ai.hrapp.controller;

import ecma.ai.hrapp.payload.ApiResponse;
import ecma.ai.hrapp.service.LeadershipService;
import ecma.ai.hrapp.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/leadership")
public class LeadershipController {

    @Autowired
    LeadershipService leadershipService;


    @Autowired
    SalaryService salaryTakenService;

    @GetMapping
    public HttpEntity<?> getHistoryAndTasks(@RequestParam Timestamp startTime, @RequestParam Timestamp endTime, @RequestParam String number){
        ApiResponse apiResponse = leadershipService.getHistoryAndTasks(startTime, endTime, number);
        return ResponseEntity.status(!apiResponse.isSuccess()? HttpStatus.OK:HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @GetMapping("/salaryByUser")
    public HttpEntity<?> getByUser(@RequestParam String email){
        ApiResponse apiResponse = salaryTakenService.getByUser(email);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.OK:HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @GetMapping("/salaryByMonth")
    public HttpEntity<?> getByMonth(@RequestParam String month){
        ApiResponse apiResponse = salaryTakenService.getByMonth(month);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.OK:HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}

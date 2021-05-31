package ecma.ai.hrapp.service;

import ecma.ai.hrapp.entity.Turniket;
import ecma.ai.hrapp.entity.User;
import ecma.ai.hrapp.payload.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class LeadershipService {
    @Autowired
    UserService userService;
    @Autowired
    TurniketService turniketService;
    @Autowired
    TurniketHistoryService turniketHistoryService;
    @Autowired
    TaskService taskService;
    public ApiResponse getHistoryAndTasks(Timestamp startTime, Timestamp endTime, String email){
        ApiResponse apiResponse = userService.getByEmail(email);
        if (!apiResponse.isSuccess())
            return apiResponse;

        User user = (User) apiResponse.getObject();

        ApiResponse responseTurniket = turniketService.getByUser(user);
        if (!responseTurniket.isSuccess())
            return responseTurniket;

        Turniket turniket = (Turniket) responseTurniket.getObject();
        ApiResponse historyList = turniketHistoryService.getAllByDate(turniket.getNumber(), startTime, endTime);

        ApiResponse taskList = taskService.getAllByUserAndDate(startTime, endTime, user);

        List<ApiResponse> responseList = new ArrayList<>();
        responseList.add(historyList);
        responseList.add(taskList);

        return new ApiResponse("So'ralgan narsalar", true, responseList);
    }

}
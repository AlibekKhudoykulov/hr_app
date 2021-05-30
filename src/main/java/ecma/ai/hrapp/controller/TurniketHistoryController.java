package ecma.ai.hrapp.controller;

import ecma.ai.hrapp.payload.ApiResponse;
import ecma.ai.hrapp.service.TurniketHistoryService;
import ecma.ai.hrapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/turniketHistory")
public class TurniketHistoryController {
    @Autowired
    TurniketHistoryService turniketHistoryService;

    @GetMapping
    public HttpEntity<?> get(HttpServletRequest httpServletRequest){
        ApiResponse apiResponse = turniketHistoryService.get(httpServletRequest);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

}

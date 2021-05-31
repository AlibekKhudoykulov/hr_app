package ecma.ai.hrapp.controller;

import ecma.ai.hrapp.payload.ApiResponse;
import ecma.ai.hrapp.service.TurniketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/turniket")
public class TurniketController {

    @Autowired
    TurniketService turniketService;

    @GetMapping("/all")
    public HttpEntity<?> getAll(){
        ApiResponse apiResponse = turniketService.getAll();
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.OK:HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getByNumber(@RequestParam String number){
        ApiResponse apiResponse = turniketService.getByNumber(number);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.OK:HttpStatus.BAD_REQUEST).body(apiResponse);
    }


    @DeleteMapping
    public HttpEntity<?> delete(@RequestParam String number){
        ApiResponse apiResponse = turniketService.delete(number);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.OK:HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}

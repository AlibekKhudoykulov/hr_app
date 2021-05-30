package ecma.ai.hrapp.service;

import ecma.ai.hrapp.payload.ApiResponse;
import ecma.ai.hrapp.repository.TurniketHistoryRepository;
import ecma.ai.hrapp.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class TurniketHistoryService {
    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    TurniketHistoryRepository turniketHistoryRepository;

    public ApiResponse get(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization");

        token = token.substring(7);

        String username = jwtProvider.getUsernameFromToken(token);//username
        if (username.equals("direktor")) {
            return new ApiResponse("Success",true,turniketHistoryRepository.findAll());
        }
        else{
            return new ApiResponse("Dostup Netu",false);
        }

    }
}

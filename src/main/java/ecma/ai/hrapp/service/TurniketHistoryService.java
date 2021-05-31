package ecma.ai.hrapp.service;

import ecma.ai.hrapp.component.Checker;
import ecma.ai.hrapp.entity.Role;
import ecma.ai.hrapp.entity.Turniket;
import ecma.ai.hrapp.entity.TurniketHistory;
import ecma.ai.hrapp.entity.enums.RoleName;
import ecma.ai.hrapp.payload.ApiResponse;
import ecma.ai.hrapp.payload.TurniketHistoryDto;
import ecma.ai.hrapp.repository.TurniketHistoryRepository;
import ecma.ai.hrapp.repository.TurniketRepository;
import ecma.ai.hrapp.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;

@Service
public class TurniketHistoryService {


    @Autowired
    TurniketHistoryRepository turniketHistoryRepository;
    @Autowired
    TurniketRepository turniketRepository;
    @Autowired
    Checker checker;

    public ApiResponse enteringCompanyAdd(TurniketHistoryDto turniketHistoryDto){
        Optional<Turniket> byId = turniketRepository.findById(turniketHistoryDto.getTurniketNumber());
        if (!byId.isPresent()) return new ApiResponse("Turniket not found,no access to enter",false);
        Turniket turniket = byId.get();
        Set<Role> roles = turniket.getOwner().getRoles();
        String role = RoleName.ROLE_STAFF.name();
        for (Role role1 : roles) {
            role = role1.getName().name();
            break;
        }

        boolean check = checker.check(role);
        if (!check) return new ApiResponse("Do not access to enter",false);
        TurniketHistory turniketHistory=new TurniketHistory();
        turniketHistory.setTurniket(turniket);
        turniketHistory.setType(turniketHistoryDto.getType());
        TurniketHistory save = turniketHistoryRepository.save(turniketHistory);
        return new ApiResponse("Welcome",true,save.getTurniket().getOwner());

    }
}

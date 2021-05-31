package ecma.ai.hrapp.service;

import ecma.ai.hrapp.component.Checker;
import ecma.ai.hrapp.entity.Role;
import ecma.ai.hrapp.entity.Turniket;
import ecma.ai.hrapp.entity.User;
import ecma.ai.hrapp.entity.enums.RoleName;
import ecma.ai.hrapp.payload.ApiResponse;
import ecma.ai.hrapp.payload.TurniketDTO;
import ecma.ai.hrapp.repository.CompanyRepository;
import ecma.ai.hrapp.repository.TurniketHistoryRepository;
import ecma.ai.hrapp.repository.TurniketRepository;
import ecma.ai.hrapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@Service
public class TurniketService {
    @Autowired
    TurniketRepository turniketRepository;
    @Autowired
    TurniketHistoryRepository tuniketHistoryRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    Checker checker;
    public void add(Integer companyId, UUID userId){
        Turniket turniket=new Turniket();
        turniket.setCompany(companyRepository.getOne(companyId));
        turniket.setOwner(userRepository.getOne(userId));
        turniketRepository.save(turniket);
    }
    public ApiResponse getByUser(User user){
        Set<Role> roles = user.getRoles();
        String role = RoleName.ROLE_STAFF.name();
        for (Role roleName : roles) {
            role = roleName.getName().name();
            break;
        }
        boolean check = checker.check(role);
        if (!check)
            return new ApiResponse("Sizga mumkin emas!", false);

        Optional<Turniket> optionalTurniket = turniketRepository.findAllByOwner(user);
        return new ApiResponse("TurniketListByUser", true, optionalTurniket);
    }
}

package ecma.ai.hrapp.service;

import ecma.ai.hrapp.entity.Turniket;
import ecma.ai.hrapp.payload.ApiResponse;
import ecma.ai.hrapp.payload.TurniketDTO;
import ecma.ai.hrapp.repository.CompanyRepository;
import ecma.ai.hrapp.repository.TurniketHistoryRepository;
import ecma.ai.hrapp.repository.TurniketRepository;
import ecma.ai.hrapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void add(Integer companyId, UUID userId){
        Turniket turniket=new Turniket();
        turniket.setCompany(companyRepository.getOne(companyId));
        turniket.setOwner(userRepository.getOne(userId));
        turniketRepository.save(turniket);
    }
}

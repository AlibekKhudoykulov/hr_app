//package ecma.ai.hrapp.service;
//
//import ecma.ai.hrapp.component.Checker;
//import ecma.ai.hrapp.entity.*;
//import ecma.ai.hrapp.payload.ApiResponse;
//import ecma.ai.hrapp.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.sql.Timestamp;
//import java.util.*;
//
//@Service
//public class LeadershipService {
////    @Autowired
////    UserRepository userRepository;
////    @Autowired
////    RoleRepository roleRepository;
////    @Autowired
////    Checker checker;
////    @Autowired
////    TurniketHistoryRepository turniketHistoryRepository;
////    @Autowired
////    TurniketService turniketService;
////    @Autowired
////    TurniketRepository turniketRepository;
////    @Autowired
////    TaskRepository taskRepository;
//
//    //Direktor va managerlar uchun xodimlar ruyxati
////    public ApiResponse getStaff(){
////        boolean b = checker.checkForGetStaff();
////        if (!b) return new ApiResponse("You have not access",false);
////        Role byName = roleRepository.findByName(RoleName.ROLE_STAFF);
////        List<User> allByRoles = userRepository.findAllByRoles();
////        return new ApiResponse("Staff list",true,allByRoles);
////    }
////    public ApiResponse getStaffDate(Timestamp startTime, Timestamp endTime, String email) {
////        Optional<User> byEmail = userRepository.findByEmail(email);
////        if (!byEmail.isPresent()) return new ApiResponse("Email not found", false);
////        User user = byEmail.get();
////        Set<Role> roles = user.getRoles();
////        for (Role role : roles) {
////            boolean response = checker.check(role.getName().name());
////            if (!response)
////                return new ApiResponse("You have no such right!", false);
////        }
////        Optional<Turniket> turniket = turniketRepository.findByEmail(user.getEmail());
////        if (!turniket.isPresent()) return new ApiResponse("Turniket not found", false);
////
////        Turniket turniket1 = turniket.get();
////        List<TurniketHistory> allByTurniketAndTimeBetween = turniketHistoryRepository.findAllByTurniketAndTimeBetween(turniket1, startTime, endTime);
////        List<Object> histories = new ArrayList<>();
////
////        List<Task> allByTaskTakerAndCompletedDateBetween = taskRepository.findAllByTaskTakerAndCompletedDateBetween(user, startTime, endTime);
////        histories.add(allByTurniketAndTimeBetween);
////        histories.add(allByTaskTakerAndCompletedDateBetween);
////        return new ApiResponse("Tasks and turniket history",true,histories);
////    }
//}

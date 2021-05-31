package ecma.ai.hrapp.service;

import ecma.ai.hrapp.component.Checker;
import ecma.ai.hrapp.entity.PaidSalary;
import ecma.ai.hrapp.entity.Role;
import ecma.ai.hrapp.entity.User;
import ecma.ai.hrapp.entity.enums.Month;
import ecma.ai.hrapp.entity.enums.RoleName;
import ecma.ai.hrapp.payload.ApiResponse;
import ecma.ai.hrapp.payload.SalaryTakenDto;
import ecma.ai.hrapp.repository.PaidSalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;


@Service
public class SalaryService {
    @Autowired
    UserService userService;
    @Autowired
    Checker checker;
    @Autowired
    PaidSalaryRepository paidSalaryRepository;
    public ApiResponse add(SalaryTakenDto salaryTakenDto){
        ApiResponse response = userService.getByEmail(salaryTakenDto.getEmail());
        if (!response.isSuccess())
            return response;
        User user = (User) response.getObject();

        Set<Role> roles = user.getRoles();
        String role = RoleName.ROLE_STAFF.name();
        for (Role rolex : roles) {
            role = rolex.getName().name();
        }

        boolean check = checker.check(role);
        if(!check)
            return new ApiResponse("Sizda huquq yo'q!", false);

        PaidSalary salaryTaken = new PaidSalary();
        salaryTaken.setAmount(salaryTakenDto.getAmount());
        salaryTaken.setOwner(user);
        salaryTaken.setPeriod(salaryTakenDto.getPeriod());
        PaidSalary save = paidSalaryRepository.save(salaryTaken);
        return new ApiResponse("Xodimga oylik kiritildi!", true);
    }

    public ApiResponse edit(SalaryTakenDto salaryTakenDto){

        ApiResponse response = userService.getByEmail(salaryTakenDto.getEmail());
        if (!response.isSuccess())
            return response;
        User user = (User) response.getObject();

        Set<Role> roles = user.getRoles();
        String role = RoleName.ROLE_STAFF.name();
        for (Role rolex : roles) {
            role = rolex.getName().name();
        }

        boolean check = checker.check(role);
        if(!check)
            return new ApiResponse("Sizda huquq yo'q!", false);

        Optional<PaidSalary> optional = paidSalaryRepository.findByOwnerAndPeriod(user, salaryTakenDto.getPeriod());
        if (!optional.isPresent())
            return new ApiResponse("Oylik mavjud emas!", false);

        if (optional.get().isPaid())
            return new ApiResponse("Bu oylik allaqachon to'langan, uni o'zgartira olmaysiz!", false);


        PaidSalary salaryTaken = optional.get();
        salaryTaken.setAmount(salaryTakenDto.getAmount());
        salaryTaken.setOwner(user);
        salaryTaken.setPeriod(salaryTakenDto.getPeriod());
        PaidSalary save = paidSalaryRepository.save(salaryTaken);
        return new ApiResponse("Xodimning oyligi o'zgartirildi!", true);
    }

    public ApiResponse delete(String email, String month){
        ApiResponse response = userService.getByEmail(email);
        if (!response.isSuccess())
            return response;
        User user = (User) response.getObject();

        Set<Role> roles = user.getRoles();
        String role = RoleName.ROLE_STAFF.name();
        for (Role rolex : roles) {
            role = rolex.getName().name();
        }

        boolean check = checker.check(role);
        if(!check)
            return new ApiResponse("Sizda huquq yo'q!", false);

        Month period = null;

        for (Month value : Month.values()) {
            if (value.name().equals(month)){
                period = value;
                break;
            }
        }
        if (period == null)
            return new ApiResponse("Month xato!", false);

        Optional<PaidSalary> optional = paidSalaryRepository.findByOwnerAndPeriod(user, period);
        if (!optional.isPresent())
            return new ApiResponse("Oylik topilmadi!", false);

        if (optional.get().isPaid())
            return new ApiResponse("Bu oylik allaqachon to'langan, uni o'zgartira olmaysiz!", false);

        paidSalaryRepository.delete(optional.get());
        return new ApiResponse("Oylik o'chirildi!", true);
    }

    //OYLIKNI BERILGAN HOLATGA O'TKAZISH
    public ApiResponse customize(String email, String month, boolean stat){
        ApiResponse response = userService.getByEmail(email);
        if (!response.isSuccess())
            return response;
        User user = (User) response.getObject();

        Set<Role> roles = user.getRoles();
        String role = RoleName.ROLE_STAFF.name();
        for (Role rolex : roles) {
            role = rolex.getName().name();
        }

        boolean check = checker.check(role);
        if(!check)
            return new ApiResponse("Sizda huquq yo'q!", false);

        Month period = null;

        for (Month value : Month.values()) {
            if (value.name().equals(month)){
                period = value;
                break;
            }
        }
        if (period == null)
            return new ApiResponse("Month xato!", false);

        Optional<PaidSalary> optional =paidSalaryRepository.findByOwnerAndPeriod(user, period);
        if (!optional.isPresent())
            return new ApiResponse("Oylik topilmadi!", false);

        PaidSalary salaryTaken = optional.get();
        if (salaryTaken.isPaid())
            return new ApiResponse("Bu oylik allaqachon to'langan, uni o'zgartira olmaysiz!", false);

        salaryTaken.setPaid(stat);
        return new ApiResponse("Oylik to'langanlik holati o'zgartirildi!", true);
    }

    public ApiResponse getByUser(String email){
        ApiResponse response = userService.getByEmail(email);
        if (!response.isSuccess())
            return response;
        User user = (User) response.getObject();

        Set<Role> roles = user.getRoles();
        String role = RoleName.ROLE_STAFF.name();
        for (Role rolex : roles) {
            role = rolex.getName().name();
        }

        boolean check = checker.check(role);
        if(!check)
            return new ApiResponse("Sizda huquq yo'q!", false);

        return new ApiResponse("List by Owner", true, paidSalaryRepository.findAllByOwner(user));
    }

    public ApiResponse getByMonth(String month){
        boolean check = checker.checkForGetStaff();
        if (!check)
            return new ApiResponse("Sizda huquq yo'q", false);

        Month period = null;

        for (Month value : Month.values()) {
            if (value.name().equals(month)){
                period = value;
                break;
            }
        }
        if (period == null)
            return new ApiResponse("Month xato!", false);

        return new ApiResponse("List by period", true, paidSalaryRepository.findAllByPeriod(period));
    }
}

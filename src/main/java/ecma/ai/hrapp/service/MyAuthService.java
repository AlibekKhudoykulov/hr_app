package ecma.ai.hrapp.service;

import ecma.ai.hrapp.entity.User;
import ecma.ai.hrapp.payload.ApiResponse;
import ecma.ai.hrapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MyAuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    TurniketService turniketService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElse(null);
    }

    public ApiResponse verifyEmail(String email, String code){
        Optional<User> optionalUser = userRepository.findByEmailAndVerifyCode(email, code);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            user.setVerifyCode(null);
            userRepository.save(user);
            turniketService.add(1,user.getId());

            return new ApiResponse("Account Verified and Turniket is Given",true);
        }
        return new ApiResponse("Account not found",false);
    }
}

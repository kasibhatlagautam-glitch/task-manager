package com.gautam.taskmanager.service;

import com.gautam.taskmanager.model.User;
import com.gautam.taskmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.gautam.taskmanager.model.Role;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }



    public User register(User user) {

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    public String login(User user) {

        Optional<User> optionalUser =
                userRepository.findByUsername(
                        user.getUsername()
                );

        if (optionalUser.isPresent()) {

            User existingUser = optionalUser.get();

            boolean passwordMatches =
                    passwordEncoder.matches(
                            user.getPassword(),
                            existingUser.getPassword()
                    );

            if (passwordMatches) {

                return jwtService.generateToken(
                        existingUser.getUsername()
                );
            }
        }

        return "Invalid credentials";
    }
}
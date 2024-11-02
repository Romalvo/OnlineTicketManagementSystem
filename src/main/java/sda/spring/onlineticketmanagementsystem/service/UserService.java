package sda.spring.onlineticketmanagementsystem.service;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sda.spring.onlineticketmanagementsystem.dto.common.UserProfileDto;
import sda.spring.onlineticketmanagementsystem.dto.request.RegisterRequestDto;
import sda.spring.onlineticketmanagementsystem.entity.User;
import sda.spring.onlineticketmanagementsystem.mapper.UserMapper;
import sda.spring.onlineticketmanagementsystem.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserProfileDto registerUser(RegisterRequestDto registerUserRequestDto) {
        User user = userMapper.fromRegisterToUserEntity(registerUserRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user = userRepository.save(user);

        return userMapper.toUserProfile(user);
    }

    public UserProfileDto getLoggedInUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username) // Implement retrieval logic
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return userMapper.toUserProfile(user);
        }
        return null;
    }
}

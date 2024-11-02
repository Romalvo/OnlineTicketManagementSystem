package sda.spring.onlineticketmanagementsystem.mapper;

import org.springframework.stereotype.Component;
import sda.spring.onlineticketmanagementsystem.dto.common.UserProfileDto;
import sda.spring.onlineticketmanagementsystem.dto.request.RegisterRequestDto;
import sda.spring.onlineticketmanagementsystem.entity.User;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public UserProfileDto toUserProfile(User user) {
        return new UserProfileDto()
                .setId(user.getId())
                .setFirstname(user.getFirstname())
                .setLastname(user.getLastname())
                .setEmail(user.getEmail());
    }

    public User fromRegisterToUserEntity(RegisterRequestDto registerUserRequestDto) {
        User user = new User();
        user.setEmail(registerUserRequestDto.getEmail());
        user.setFirstname(registerUserRequestDto.getFirstname());
        user.setLastname(registerUserRequestDto.getLastname());
        user.setPassword(registerUserRequestDto.getPassword());
        user.setCreatedAt(LocalDateTime.now());

        return user;
    }

    public UserProfileDto fromEntityToUserProfile(User owner) {
        return new UserProfileDto()
                .setId(owner.getId())
                .setFirstname(owner.getFirstname())
                .setLastname(owner.getLastname())
                .setEmail(owner.getEmail());
    }
}

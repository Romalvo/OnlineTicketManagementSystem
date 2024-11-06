package sda.spring.onlineticketmanagementsystem.mapper;

import org.springframework.stereotype.Component;
import sda.spring.onlineticketmanagementsystem.dto.common.UserProfileDto;
import sda.spring.onlineticketmanagementsystem.dto.request.RegisterCustomerRequestDto;
import sda.spring.onlineticketmanagementsystem.dto.request.RegisterSupportPersonnelDto;
import sda.spring.onlineticketmanagementsystem.entity.Customer;
import sda.spring.onlineticketmanagementsystem.entity.SupportPersonnel;
import sda.spring.onlineticketmanagementsystem.entity.User;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public UserProfileDto toCustomerProfile(User user) {
        return new UserProfileDto()
                .setId(user.getId())
                .setFirstname(user.getFirstname())
                .setLastname(user.getLastname())
                .setEmail(user.getEmail());
    }
    public UserProfileDto toSupportPersonnelProfile(SupportPersonnel user) {
        return new UserProfileDto()
                .setId(user.getId())
                .setFirstname(user.getFirstname())
                .setLastname(user.getLastname())
                .setEmail(user.getEmail());
    }

    public Customer fromRegisterToCustomerEntity(RegisterCustomerRequestDto registerUserRequestDto) {
        Customer customer = new Customer();
        customer.setEmail(registerUserRequestDto.getEmail());
        customer.setFirstname(registerUserRequestDto.getFirstname());
        customer.setLastname(registerUserRequestDto.getLastname());
        customer.setPassword(registerUserRequestDto.getPassword());
        customer.setCreatedAt(LocalDateTime.now());

        return customer;
    }

    public SupportPersonnel fromRegisterToSupportPersonnelEntity(RegisterSupportPersonnelDto registerSupportPersonnelDto) {
        SupportPersonnel supportPersonnel = new SupportPersonnel();
        supportPersonnel.setEmail(registerSupportPersonnelDto.getEmail());
        supportPersonnel.setFirstname(registerSupportPersonnelDto.getFirstname());
        supportPersonnel.setLastname(registerSupportPersonnelDto.getLastname());
        supportPersonnel.setPassword(registerSupportPersonnelDto.getPassword());
        supportPersonnel.setCreatedAt(LocalDateTime.now());

        return supportPersonnel;
    }

    public UserProfileDto fromEntityToUserProfile(User owner) {
        return new UserProfileDto()
                .setId(owner.getId())
                .setFirstname(owner.getFirstname())
                .setLastname(owner.getLastname())
                .setEmail(owner.getEmail());
    }
}

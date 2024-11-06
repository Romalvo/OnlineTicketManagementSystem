package sda.spring.onlineticketmanagementsystem.service;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sda.spring.onlineticketmanagementsystem.dto.common.UserProfileDto;
import sda.spring.onlineticketmanagementsystem.dto.request.RegisterCustomerRequestDto;
import sda.spring.onlineticketmanagementsystem.dto.request.RegisterSupportPersonnelDto;
import sda.spring.onlineticketmanagementsystem.entity.Customer;
import sda.spring.onlineticketmanagementsystem.entity.Department;
import sda.spring.onlineticketmanagementsystem.entity.SupportPersonnel;
import sda.spring.onlineticketmanagementsystem.entity.User;
import sda.spring.onlineticketmanagementsystem.mapper.UserMapper;
import sda.spring.onlineticketmanagementsystem.repository.DepartmentRepository;
import sda.spring.onlineticketmanagementsystem.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepository departmentRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       DepartmentRepository departmentRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.departmentRepository = departmentRepository;
        this.userMapper = userMapper;
    }

    public UserProfileDto registerCustomer(RegisterCustomerRequestDto registerCustomerRequestDto) {
        Customer customer = userMapper.fromRegisterToCustomerEntity(registerCustomerRequestDto);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        customer = userRepository.save(customer);

        return userMapper.toCustomerProfile(customer);
    }

    public UserProfileDto registerSupportPersonnel(RegisterSupportPersonnelDto registerSupportPersonnelDto) {

        SupportPersonnel supportPersonnel = userMapper.fromRegisterToSupportPersonnelEntity(registerSupportPersonnelDto);
        supportPersonnel.setPassword(passwordEncoder.encode(supportPersonnel.getPassword()));
        registerSupportPersonnelDto.getDepartments().forEach(dp -> {
            // Check if the department exists in the database then fetch it and attach it to the support personnel
            // If not throw an exception
            Department department = departmentRepository.findByName(dp)
                    .orElseThrow(()->new RuntimeException("Department not found" + dp));
            supportPersonnel.getDepartments().add(department);
        });

        userRepository.save(supportPersonnel);

        return userMapper.toSupportPersonnelProfile(supportPersonnel);

    }

    public UserProfileDto getLoggedInUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username) // Implement retrieval logic
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return userMapper.toCustomerProfile(user);
        }
        return null;
    }
}

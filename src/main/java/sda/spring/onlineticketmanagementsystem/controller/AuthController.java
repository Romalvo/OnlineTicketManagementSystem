package sda.spring.onlineticketmanagementsystem.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import sda.spring.onlineticketmanagementsystem.dto.common.UserProfileDto;
import sda.spring.onlineticketmanagementsystem.dto.request.LoginRequestDto;
import sda.spring.onlineticketmanagementsystem.dto.request.RegisterCustomerRequestDto;
import sda.spring.onlineticketmanagementsystem.dto.response.TokenResponseDto;
import sda.spring.onlineticketmanagementsystem.service.UserService;
import sda.spring.onlineticketmanagementsystem.util.JwtUtil;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserService userService,
            JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // Register new user
    // todo:separate endpoints for customer and support personnel registration
    @PostMapping("/register-support-personnel")
    public ResponseEntity<UserProfileDto> registerSupportPersonnel(@RequestBody RegisterCustomerRequestDto registerUserRequestDto) {
        UserProfileDto response = userService.registerCustomer(registerUserRequestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register-customer")
    public ResponseEntity<UserProfileDto> registerCustomer(@RequestBody RegisterCustomerRequestDto registerUserRequestDto) {
        UserProfileDto response = userService.registerCustomer(registerUserRequestDto);
        return ResponseEntity.ok(response);
    }


    // Login and return JWT token
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );
        String token = jwtUtil.generateToken(loginRequestDto.getEmail());
        return ResponseEntity.ok(new TokenResponseDto(token));
    }

    // Get user profile (secured endpoint)
    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getProfile() {
        return ResponseEntity.ok(userService.getLoggedInUserProfile());
    }

    // Logout is handled client-side; you can invalidate the token if needed
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logout successful");
    }
}

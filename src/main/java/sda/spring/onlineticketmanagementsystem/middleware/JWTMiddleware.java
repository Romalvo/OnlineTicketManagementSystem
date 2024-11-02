package sda.spring.onlineticketmanagementsystem.middleware;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sda.spring.onlineticketmanagementsystem.service.AuthService;
import sda.spring.onlineticketmanagementsystem.util.JwtUtil;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class JWTMiddleware extends OncePerRequestFilter {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final Logger logger = Logger.getLogger(JWTMiddleware.class.getName());

    public JWTMiddleware(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Check if the token is present in the Authorization header

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Extract the token (Remove "Bearer")
            try {
                username = jwtUtil.extractUsername(jwt); // Extract username from the token
                logger.info("Token extracted, Username: " + username);
            } catch (Exception e) {
                logger.warning("JWT token extraction failed: " + e.getMessage());
            }
        }else{
                logger.warning("JWT token is missing or invalid");
        }

        // Validate the token and set the authentication in the security context
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.authService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, UserDetails.getUsername())) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);
                logger.info("JWT Token validated and authentication set for user: " + username);
            }else {
                logger.warning("JWT token validation failed");
            }
        }
        chain.doFilter(request, response); // Continue the filter chain
    }
}

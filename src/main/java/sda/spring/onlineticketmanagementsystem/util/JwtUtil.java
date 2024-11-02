package sda.spring.onlineticketmanagementsystem.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

@Component
public class JwtUtil {
    private final KeyManager keyManager;

    @Value("${jwt.token.expiration.hours}") // Read expiration time directly from properties
    private long expiration;

    public JwtUtil(KeyManager keyManager) {
        this.keyManager = keyManager;
    }

    public String generateToken(String username) {
        PrivateKey privateKey = keyManager.getPrivateKey();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ expiration)) // Use the expiration from properties
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        PublicKey publicKey = keyManager.getPublicKey();

        // Create a JwtParser to parse the token
        JwtParser parser = Jwts.parser()
                .verifyWith(publicKey) // Set the signing key for validation
                .build();

        // Parse the token and get claims
        return parser.parseSignedClaims(token).getPayload();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}

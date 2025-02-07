package dev.library.backend.config.security;

import dev.library.backend.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;



import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// Note : PLEASE WORK ! I BEG OF YOU :(
// Note : Modified JWT

@Service
public class JwtService {
    public String extractUsername(String token) {
        return this.extractClaims(token, Claims::getSubject);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SecurityConstants.JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public <T> T extractClaims(String token, Function<Claims , T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String generateToken(UserDetails userDetails) {
        return this.generateToken(new HashMap<>() , userDetails);
    }
    public String generateToken(Map<String , Object> extractClaims , UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(SecurityConstants.JWT_ISSUED_AT)
                .setExpiration(SecurityConstants.JWT_EXPIRATION_DATE)
                .signWith(getSignInKey() , SignatureAlgorithm.HS256)
                .compact();
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = this.extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    public boolean isTokenExpired(String token) {
        return this.extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return this.extractClaims(token, Claims::getExpiration);
    }
}

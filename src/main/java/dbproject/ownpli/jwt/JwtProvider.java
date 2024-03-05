package dbproject.ownpli.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbproject.ownpli.controller.dto.token.TokenResponse;
import dbproject.ownpli.controller.dto.user.UserResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${spring.jwt.secret}")
    private String KEY;

    @Value("${spring.jwt.token-validity-in-seconds}")
    private Long atkLive;

    private final ObjectMapper objectMapper;

    @PostConstruct
    protected void init() {
        KEY = Base64.getEncoder().encodeToString(KEY.getBytes());
    }

    public TokenResponse createTokensByLogin(UserResponse userResponse) throws JsonProcessingException {
        Subject atkSubject = Subject.atk(
                userResponse.getUserId(),
                userResponse.getUserName()
        );
        String atk = createToken(atkSubject, atkLive);
        return new TokenResponse(atk, null);
    }

    private String createToken(Subject subject, Long tokenLive) throws JsonProcessingException {
        String subjectStr = objectMapper.writeValueAsString(subject);
        Claims claims = Jwts.claims()
                .setSubject(subjectStr);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenLive))
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();
    }

    public Subject getSubject(String atk) throws JsonProcessingException {
        String subjectStr = Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(atk)
                .getBody()
                .getSubject();
        return objectMapper.readValue(subjectStr, Subject.class);
    }

}

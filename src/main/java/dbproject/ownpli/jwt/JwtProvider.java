package dbproject.ownpli.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbproject.ownpli.controller.dto.token.TokenResponse;
import dbproject.ownpli.controller.dto.user.UserResponse;
import dbproject.ownpli.exception.OwnPliForbiddenException;
import dbproject.ownpli.redis.RedisDao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${spring.jwt.secret}")
    private String KEY;

    @Value("${spring.jwt.token-validity-in-seconds}")
    private Long atkLive;

    @Value("${spring.jwt.token-regenerate-seconds}")
    private Long rtkLive;

    private final ObjectMapper objectMapper;
    private final RedisDao redisDao;

    @PostConstruct
    protected void init() {
        KEY = Base64.getEncoder().encodeToString(KEY.getBytes());
    }

    public TokenResponse createTokensByLogin(UserResponse userResponse) throws JsonProcessingException {
        Subject atkSubject = Subject.atk(userResponse);
        Subject rtkSubject = Subject.rtk(userResponse);

        String atk = createToken(atkSubject, atkLive);
        String rtk = createToken(rtkSubject, rtkLive);

        return new TokenResponse(atk, rtk);
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

    public TokenResponse reissueAtk(UserResponse userResponse) throws JsonProcessingException {
        String rtkInRedis = redisDao.getValues(userResponse.getUserId());
        if (Objects.isNull(rtkInRedis)) throw new OwnPliForbiddenException("인증 정보가 만료되었습니다.");
        Subject atkSubject = Subject.atk(userResponse);
        String atk = createToken(atkSubject, atkLive);
        return new TokenResponse(atk, null);
    }

}

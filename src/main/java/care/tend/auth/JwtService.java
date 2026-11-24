package care.tend.auth;
import care.tend.user.UserAccount;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.*;
@Service
public class JwtService {
  private final javax.crypto.SecretKey key;
  public JwtService(@Value("${tend.jwt-secret}") String secret) { key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); }
  public String create(UserAccount user) { var now=Instant.now(); return Jwts.builder().subject(user.id.toString()).claim("email",user.email).issuedAt(Date.from(now)).expiration(Date.from(now.plus(Duration.ofDays(7)))).signWith(key).compact(); }
  public UUID parseUserId(String token) { return UUID.fromString(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject()); }
}

package care.tend.auth;
import care.tend.user.UserAccount;import org.junit.jupiter.api.Test;import java.util.UUID;import static org.assertj.core.api.Assertions.assertThat;
class JwtServiceTest{@Test void roundTripsUserIdentity(){var service=new JwtService("test-secret-that-is-at-least-thirty-two-characters-long");var user=new UserAccount();user.id=UUID.randomUUID();user.email="person@example.test";assertThat(service.parseUserId(service.create(user))).isEqualTo(user.id);}}

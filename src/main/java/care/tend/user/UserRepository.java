package care.tend.user;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface UserRepository extends JpaRepository<UserAccount, UUID> { Optional<UserAccount> findByEmailIgnoreCase(String email); }

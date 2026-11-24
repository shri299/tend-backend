package care.tend.checkin;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface CheckinRepository extends JpaRepository<WellbeingCheckin,UUID>{List<WellbeingCheckin> findTop30ByUserIdOrderByRecordedAtDesc(UUID userId);Optional<WellbeingCheckin> findFirstByUserIdOrderByRecordedAtDesc(UUID userId);}

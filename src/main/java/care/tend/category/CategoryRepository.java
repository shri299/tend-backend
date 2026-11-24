package care.tend.category;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface CategoryRepository extends JpaRepository<LifeCategory,UUID>{List<LifeCategory> findAllByUserIdAndArchivedFalseOrderByName(UUID userId); Optional<LifeCategory> findByIdAndUserId(UUID id,UUID userId);}

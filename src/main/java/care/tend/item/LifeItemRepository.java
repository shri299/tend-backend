package care.tend.item;
import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.time.LocalDate; import java.util.*;
public interface LifeItemRepository extends JpaRepository<LifeItem,UUID>{
 List<LifeItem> findAllByUserIdAndTypeOrderByCreatedAtDesc(UUID userId,LifeItem.Type type); Optional<LifeItem> findByIdAndUserId(UUID id,UUID userId);
 @Query(value="select * from life_item where user_id=:u and (scheduled_date=:d or (type='HABIT' and status='ACTIVE')) order by sort_order,created_at",nativeQuery=true) List<LifeItem> today(@Param("u")UUID userId,@Param("d")LocalDate date);
 long countByUserIdAndTypeAndStatus(UUID userId,LifeItem.Type type,String status);
}

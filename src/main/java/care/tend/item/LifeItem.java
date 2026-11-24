package care.tend.item;
import jakarta.persistence.*; import java.time.*; import java.util.UUID;
@Entity @Table(name="life_item")
public class LifeItem { public enum Type{BRAIN_DUMP,GOAL,TASK,HABIT}
  @Id @GeneratedValue(strategy=GenerationType.UUID) public UUID id; @Column(name="user_id",nullable=false) public UUID userId; @Column(name="category_id") public UUID categoryId; @Enumerated(EnumType.STRING) @Column(nullable=false) public Type type;
  @Column(nullable=false) public String title; @Column(columnDefinition="text") public String description; @Column(nullable=false) public String status="ACTIVE"; public String priority; public String energy; @Column(name="due_date") public LocalDate dueDate; @Column(name="scheduled_date") public LocalDate scheduledDate; @Column(name="minimum_action") public String minimumAction; public String recurrence; @Column(name="parent_id") public UUID parentId; @Column(name="sort_order") public int sortOrder; public boolean processed; @Column(name="completed_at") public Instant completedAt; @Column(name="created_at",insertable=false,updatable=false) public Instant createdAt; @Column(name="updated_at",insertable=false) public Instant updatedAt;
}

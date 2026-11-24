package care.tend.category;
import jakarta.persistence.*; import java.time.Instant; import java.util.UUID;
@Entity @Table(name="life_category")
public class LifeCategory { @Id @GeneratedValue(strategy=GenerationType.UUID) public UUID id; @Column(name="user_id",nullable=false) public UUID userId; @Column(nullable=false) public String name; @Column(nullable=false) public String color; @Column(nullable=false) public String icon="sparkles"; @Column(nullable=false) public boolean archived=false; @Column(name="created_at",insertable=false,updatable=false) public Instant createdAt; }

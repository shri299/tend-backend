package care.tend.checkin;
import jakarta.persistence.*; import java.time.Instant; import java.util.UUID;
@Entity @Table(name="wellbeing_checkin")
public class WellbeingCheckin {@Id @GeneratedValue(strategy=GenerationType.UUID) public UUID id;@Column(name="user_id",nullable=false) public UUID userId;public Short mood;@Column(nullable=false) public String energy;public Short stress;@Column(columnDefinition="text") public String note;@Column(name="recorded_at",insertable=false,updatable=false) public Instant recordedAt;}

package care.tend.user;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name="app_user")
public class UserAccount {
  @Id @GeneratedValue(strategy=GenerationType.UUID) public UUID id;
  @Column(nullable=false, unique=true) public String email;
  @Column(name="password_hash", nullable=false) public String passwordHash;
  @Column(name="display_name", nullable=false) public String displayName;
  @Column(nullable=false) public String timezone = "UTC";
  @Column(name="created_at", nullable=false, insertable=false, updatable=false) public Instant createdAt;
}

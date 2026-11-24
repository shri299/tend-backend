package care.tend.auth;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@ConditionalOnProperty(name="tend.demo-mode", havingValue="true")
public class DevDemoUser implements CommandLineRunner {
  public static final UUID ID=UUID.fromString("00000000-0000-0000-0000-000000000001");
  private final JdbcTemplate jdbc;
  public DevDemoUser(JdbcTemplate jdbc){this.jdbc=jdbc;}
  @Override public void run(String... args){
    jdbc.update("insert into app_user(id,email,password_hash,display_name,timezone) values (?, 'demo@local.test', 'disabled', 'Local Tester', 'UTC') on conflict (id) do nothing",ID);
    Object[][] defaults={{"Mental health","#E8DDF5","brain"},{"Physical health","#DFECD9","heart-pulse"},{"Personal care","#F5DFE7","sparkles"},{"Career & learning","#DDE8F8","book-open"},{"Finances","#FBE6CF","wallet"},{"Home & lifestyle","#F0EAE2","home"},{"Relationships","#F3E1D4","users"},{"Hobbies & interests","#E1ECF4","palette"}};
    for(var d:defaults)jdbc.update("insert into life_category(user_id,name,color,icon) values (?,?,?,?) on conflict (user_id,name) do nothing",ID,d[0],d[1],d[2]);
  }
}

package care.tend.checkin;
import care.tend.common.CurrentUser; import jakarta.validation.Valid; import jakarta.validation.constraints.*; import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/checkins")
public class CheckinController {private final CheckinRepository repo;public CheckinController(CheckinRepository repo){this.repo=repo;}public record Request(@Min(1)@Max(5)Integer mood,@NotBlank String energy,@Min(1)@Max(5)Integer stress,String note){}
 @GetMapping List<WellbeingCheckin> list(){return repo.findTop30ByUserIdOrderByRecordedAtDesc(CurrentUser.id());}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) WellbeingCheckin create(@Valid@RequestBody Request r){var c=new WellbeingCheckin();c.userId=CurrentUser.id();c.mood=r.mood()==null?null:r.mood().shortValue();c.energy=r.energy();c.stress=r.stress()==null?null:r.stress().shortValue();c.note=r.note();return repo.save(c);}}

package care.tend.item;
import care.tend.common.CurrentUser; import jakarta.validation.Valid; import jakarta.validation.constraints.*; import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*; import java.time.*; import java.util.*;
@RestController @RequestMapping("/api/items")
public class LifeItemController {private final LifeItemRepository repo;public LifeItemController(LifeItemRepository repo){this.repo=repo;}
 public record Request(@NotNull LifeItem.Type type,@NotBlank @Size(max=200) String title,String description,UUID categoryId,String status,String priority,String energy,LocalDate dueDate,LocalDate scheduledDate,String minimumAction,String recurrence,UUID parentId,Boolean processed){}
 @GetMapping List<LifeItem> list(@RequestParam LifeItem.Type type){return repo.findAllByUserIdAndTypeOrderByCreatedAtDesc(CurrentUser.id(),type);}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) LifeItem create(@Valid @RequestBody Request r){return repo.save(apply(new LifeItem(),r));}
 @PutMapping("/{id}") LifeItem update(@PathVariable UUID id,@Valid @RequestBody Request r){return repo.save(apply(repo.findByIdAndUserId(id,CurrentUser.id()).orElseThrow(),r));}
 @PatchMapping("/{id}/complete") LifeItem complete(@PathVariable UUID id){var i=repo.findByIdAndUserId(id,CurrentUser.id()).orElseThrow();i.status="COMPLETED";i.completedAt=Instant.now();return repo.save(i);}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) void delete(@PathVariable UUID id){repo.delete(repo.findByIdAndUserId(id,CurrentUser.id()).orElseThrow());}
 private LifeItem apply(LifeItem i,Request r){i.userId=CurrentUser.id();i.type=r.type();i.title=r.title().trim();i.description=r.description();i.categoryId=r.categoryId();i.status=r.status()==null?"ACTIVE":r.status();i.priority=r.priority();i.energy=r.energy();i.dueDate=r.dueDate();i.scheduledDate=r.scheduledDate();i.minimumAction=r.minimumAction();i.recurrence=r.recurrence();i.parentId=r.parentId();i.processed=Boolean.TRUE.equals(r.processed());return i;}
}

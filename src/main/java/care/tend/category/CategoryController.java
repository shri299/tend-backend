package care.tend.category;
import care.tend.common.CurrentUser; import jakarta.validation.Valid; import jakarta.validation.constraints.*; import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/categories")
public class CategoryController {private final CategoryRepository repo;public CategoryController(CategoryRepository repo){this.repo=repo;} public record Request(@NotBlank @Size(max=80) String name,@Pattern(regexp="^#[0-9A-Fa-f]{6}$") String color,@NotBlank String icon){}
  @GetMapping List<LifeCategory> list(){return repo.findAllByUserIdAndArchivedFalseOrderByName(CurrentUser.id());}
  @PostMapping @ResponseStatus(HttpStatus.CREATED) LifeCategory create(@Valid @RequestBody Request r){var c=new LifeCategory();c.userId=CurrentUser.id();c.name=r.name();c.color=r.color();c.icon=r.icon();return repo.save(c);}
  @PutMapping("/{id}") LifeCategory update(@PathVariable UUID id,@Valid @RequestBody Request r){var c=repo.findByIdAndUserId(id,CurrentUser.id()).orElseThrow();c.name=r.name();c.color=r.color();c.icon=r.icon();return repo.save(c);}
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) void archive(@PathVariable UUID id){var c=repo.findByIdAndUserId(id,CurrentUser.id()).orElseThrow();c.archived=true;repo.save(c);}
}

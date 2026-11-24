package care.tend.category;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.*;
@Service
public class CategoryService { private final CategoryRepository repo; public CategoryService(CategoryRepository repo){this.repo=repo;}
  @Transactional public void createDefaults(UUID userId){List.of(new String[]{"Mental health","#E8DDF5","brain"},new String[]{"Physical health","#DFECD9","heart-pulse"},new String[]{"Personal care","#F5DFE7","sparkles"},new String[]{"Career & learning","#DDE8F8","book-open"},new String[]{"Finances","#FBE6CF","wallet"},new String[]{"Home & lifestyle","#F0EAE2","home"},new String[]{"Relationships","#F3E1D4","users"},new String[]{"Hobbies & interests","#E1ECF4","palette"}).forEach(d->{var c=new LifeCategory();c.userId=userId;c.name=d[0];c.color=d[1];c.icon=d[2];repo.save(c);});}
}

package care.tend.auth;

import care.tend.category.CategoryService;
import care.tend.user.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/auth")
public class AuthController {
  private final UserRepository users; private final PasswordEncoder passwords; private final JwtService jwt; private final CategoryService categories;
  public AuthController(UserRepository users, PasswordEncoder passwords, JwtService jwt, CategoryService categories) { this.users=users; this.passwords=passwords; this.jwt=jwt; this.categories=categories; }

  public record RegisterRequest(@Email @NotBlank String email, @Size(min=10,max=72) String password, @NotBlank @Size(max=80) String displayName) {}
  public record LoginRequest(@Email @NotBlank String email, @NotBlank String password) {}
  public record AuthResponse(String token, String displayName) {}

  @PostMapping("/register") @ResponseStatus(HttpStatus.CREATED)
  public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
    if (users.findByEmailIgnoreCase(request.email()).isPresent()) throw new IllegalArgumentException("An account with this email already exists");
    var user = new UserAccount(); user.email=request.email().trim().toLowerCase(); user.passwordHash=passwords.encode(request.password()); user.displayName=request.displayName().trim();
    user=users.save(user); categories.createDefaults(user.id); return new AuthResponse(jwt.create(user), user.displayName);
  }
  @PostMapping("/login")
  public AuthResponse login(@Valid @RequestBody LoginRequest request) {
    var user=users.findByEmailIgnoreCase(request.email()).orElseThrow(() -> new BadCredentialsException("Invalid email or password"));
    if (!passwords.matches(request.password(), user.passwordHash)) throw new BadCredentialsException("Invalid email or password");
    return new AuthResponse(jwt.create(user), user.displayName);
  }
}

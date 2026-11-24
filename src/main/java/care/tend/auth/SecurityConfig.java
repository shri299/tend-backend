package care.tend.auth;
import jakarta.servlet.*; import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Value; import org.springframework.context.annotation.*; import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy; import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain; import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*; import java.io.IOException; import java.util.*;
@Configuration
public class SecurityConfig {
  @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
  @Bean CorsConfigurationSource cors(@Value("${tend.frontend-origin}") String origin){var c=new CorsConfiguration();c.setAllowedOrigins(List.of(origin));c.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));c.setAllowedHeaders(List.of("*"));var s=new UrlBasedCorsConfigurationSource();s.registerCorsConfiguration("/**",c);return s;}
  @Bean SecurityFilterChain security(HttpSecurity http, JwtService jwt, @Value("${tend.demo-mode:false}") boolean demoMode) throws Exception { return http.csrf(c->c.disable()).cors(c->{}).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(a->a.requestMatchers("/api/auth/**","/v3/api-docs/**","/swagger-ui/**","/actuator/health").permitAll().anyRequest().authenticated()).addFilterBefore(new JwtFilter(jwt,demoMode), UsernamePasswordAuthenticationFilter.class).build(); }
  static class JwtFilter extends org.springframework.web.filter.OncePerRequestFilter { private final JwtService jwt; private final boolean demoMode; JwtFilter(JwtService jwt,boolean demoMode){this.jwt=jwt;this.demoMode=demoMode;} protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException{var h=req.getHeader(HttpHeaders.AUTHORIZATION);if(h!=null&&h.startsWith("Bearer "))try{var id=jwt.parseUserId(h.substring(7));SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(id,null,List.of()));}catch(Exception ignored){}if(demoMode&&SecurityContextHolder.getContext().getAuthentication()==null)SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(DevDemoUser.ID,null,List.of()));chain.doFilter(req,res);} }
}

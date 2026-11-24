package care.tend.common;
import org.springframework.http.*; import org.springframework.security.authentication.BadCredentialsException; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*;
import java.time.Instant; import java.util.*;
@RestControllerAdvice
public class ApiExceptionHandler {
  record ErrorResponse(String message, Instant timestamp, Map<String,String> fields){}
  @ExceptionHandler(MethodArgumentNotValidException.class) @ResponseStatus(HttpStatus.BAD_REQUEST) ErrorResponse invalid(MethodArgumentNotValidException ex){var f=new LinkedHashMap<String,String>();ex.getBindingResult().getFieldErrors().forEach(e->f.put(e.getField(),e.getDefaultMessage()));return new ErrorResponse("Please check the highlighted fields",Instant.now(),f);}
  @ExceptionHandler({IllegalArgumentException.class, BadCredentialsException.class}) @ResponseStatus(HttpStatus.BAD_REQUEST) ErrorResponse bad(RuntimeException ex){return new ErrorResponse(ex.getMessage(),Instant.now(),Map.of());}
  @ExceptionHandler(NoSuchElementException.class) @ResponseStatus(HttpStatus.NOT_FOUND) ErrorResponse missing(NoSuchElementException ex){return new ErrorResponse(ex.getMessage(),Instant.now(),Map.of());}
}

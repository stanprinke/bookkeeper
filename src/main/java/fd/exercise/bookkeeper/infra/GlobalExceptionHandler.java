package fd.exercise.bookkeeper.infra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ErrorResponse handleGenericException(Exception ex) {

    log.error("Unhandled exception during request processing: {}", ex.getMessage());

    return ErrorResponse.builder(
            ex,
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Unexpected error occurred: " + ex.getMessage())
        .build();
  }
}
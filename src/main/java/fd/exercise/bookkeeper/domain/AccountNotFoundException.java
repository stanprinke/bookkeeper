package fd.exercise.bookkeeper.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

public class AccountNotFoundException extends ErrorResponseException {
  public AccountNotFoundException(String message) {
    super(HttpStatus.NOT_FOUND);
    setTitle(message);
  }
}

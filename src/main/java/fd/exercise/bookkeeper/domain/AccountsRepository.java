package fd.exercise.bookkeeper.domain;

import fd.exercise.bookkeeper.domain.entities.Account;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

interface AccountsRepository extends CrudRepository<Account, String> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Override
  public Optional<Account> findById(String id);
}

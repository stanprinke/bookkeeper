package fd.exercise.bookkeeper.domain;

import fd.exercise.bookkeeper.domain.entities.Account;
import org.springframework.data.repository.CrudRepository;

interface AccountsRepository extends CrudRepository<Account, String> {
}

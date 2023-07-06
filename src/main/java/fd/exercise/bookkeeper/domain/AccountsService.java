package fd.exercise.bookkeeper.domain;

import fd.exercise.bookkeeper.api.dtos.AccountDto;
import fd.exercise.bookkeeper.api.dtos.TransferDto;
import fd.exercise.bookkeeper.domain.entities.Account;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class AccountsService {
  private final AccountMapper mapper;
  private final AccountsRepository accountsRepository;

  public List<AccountDto> getAll() {
    return StreamSupport.stream(accountsRepository.findAll().spliterator(), false)
        .map(mapper::convert)
        .toList();
  }

  public void save(AccountDto accountDto) {
    accountsRepository.save(mapper.convert(accountDto));
  }

  @Transactional
  public List<AccountDto> transfer(TransferDto transfer) {
    Optional<Account> maybeSource = accountsRepository.findById(transfer.sourceAccount());
    if (maybeSource.isEmpty()) {
      throw new AccountNotFoundException("source account not found");
    }

    Optional<Account> maybeDestination = accountsRepository.findById(transfer.destinationAccount());
    if (maybeDestination.isEmpty()) {
      throw new AccountNotFoundException("destination account not found");
    }

    Account source = maybeSource.get();
    Account destination = maybeDestination.get();

    source.setBalance(source.getBalance().subtract(transfer.amount()));
    destination.setBalance(destination.getBalance().add(transfer.amount()));

    return List.of(mapper.convert(source), mapper.convert(destination));
  }
}

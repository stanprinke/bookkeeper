package fd.exercise.bookkeeper.domain;

import fd.exercise.bookkeeper.api.dtos.AccountDto;
import fd.exercise.bookkeeper.domain.entities.Account;
import org.mapstruct.Mapper;

@Mapper
interface AccountMapper {
  AccountDto convert(Account source);
  Account convert(AccountDto source);
}

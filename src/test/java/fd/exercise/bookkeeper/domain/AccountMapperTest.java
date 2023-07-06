package fd.exercise.bookkeeper.domain;

import fd.exercise.bookkeeper.api.dtos.AccountDto;
import fd.exercise.bookkeeper.domain.entities.Account;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class AccountMapperTest {
  private final static String SAMPLE_ACCOUNT = "an account";
  private final static BigDecimal SAMPLE_BALANCE = BigDecimal.valueOf(123.456);
   AccountMapper underTest = Mappers.getMapper(AccountMapper.class);

  @Test
  void accountToDto() {
    AccountDto converted = underTest.convert(new Account(SAMPLE_ACCOUNT, SAMPLE_BALANCE));

    Assertions.assertEquals(SAMPLE_ACCOUNT, converted.accountNumber());
    Assertions.assertEquals(SAMPLE_BALANCE, converted.balance());
  }

  @Test
  void dtoToAccount() {
    Account converted = underTest.convert(new AccountDto(SAMPLE_ACCOUNT, SAMPLE_BALANCE));

    Assertions.assertEquals(SAMPLE_ACCOUNT, converted.getAccountNumber());
    Assertions.assertEquals(SAMPLE_BALANCE, converted.getBalance());
  }
}
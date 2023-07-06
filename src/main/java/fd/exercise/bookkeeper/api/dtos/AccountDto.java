package fd.exercise.bookkeeper.api.dtos;

import java.math.BigDecimal;

public record AccountDto(
    String accountNumber,
    BigDecimal balance
) {
}

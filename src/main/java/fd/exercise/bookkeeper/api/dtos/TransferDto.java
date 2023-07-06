package fd.exercise.bookkeeper.api.dtos;

import java.math.BigDecimal;

public record TransferDto(
    String sourceAccount,
    String destinationAccount,
    BigDecimal amount
) {
}

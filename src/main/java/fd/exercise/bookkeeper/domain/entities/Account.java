package fd.exercise.bookkeeper.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {
  @Id
  @Column(name = "account_number", nullable = false, updatable = false)
  private String accountNumber;

  @Column(name = "balance", precision = 16, scale = 4)
  private BigDecimal balance;
}

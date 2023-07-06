package fd.exercise.bookkeeper;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fd.exercise.bookkeeper.api.dtos.AccountDto;
import java.math.BigDecimal;
import java.util.List;
import lombok.SneakyThrows;
import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class TransferScenarioTest {
  private final static String SOURCE = "some test source account";
  private final static String DESTINATION = "some test destination account";
  @Autowired
  private MockMvc mvc;
  @Autowired
  private ObjectMapper mapper;

  @Test
  void successfulTransferScenario() throws Exception {
    // given
    createAccount(SOURCE, "50.1234");
    createAccount(DESTINATION, "0");

    // when
    performTransfer(SOURCE, DESTINATION, "100");

    // then
    assertAccountBalance(SOURCE, -49.8766);
    assertAccountBalance(DESTINATION, 100);
  }

  private void createAccount(String accountNumber, String balance) throws Exception {
    mvc.perform(post("/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                  { "accountNumber": "%s", "balance": "%s" }
                """.formatted(accountNumber, balance)))
        .andExpect(status().isOk());
  }

  private void performTransfer(String sourceAccount, String destinationAccount, String amount) throws Exception {
    mvc.perform(post("/accounts/transfer")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                  { "sourceAccount": "%s", "destinationAccount": "%s", "amount": %s }
                """.formatted(sourceAccount, destinationAccount,amount)))
        .andExpect(status().isOk());
  }

  private void assertAccountBalance(String accountNumber, double balance) throws Exception {
    mvc.perform(get("/accounts"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().string(new AssertionMatcher<String>() {
          @SneakyThrows
          @Override
          public void assertion(String response) throws AssertionError {
            List<AccountDto> accounts = mapper.readValue(response,  new TypeReference<>(){});
            AccountDto account = accounts.stream()
                .filter(a -> a.accountNumber().equals(accountNumber))
                .findFirst()
                .orElseThrow(() -> new AssertionError("account was expected to exist: " + accountNumber));

            Assertions.assertTrue(BigDecimal.valueOf(balance).compareTo(account.balance()) == 0);
          }
        }));
  }
}


package fd.exercise.bookkeeper;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import fd.exercise.bookkeeper.api.AccountsController;
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
class BookkeeperApplicationTests {
  @Autowired
  private MockMvc mvc;
  @Autowired
  private AccountsController controller;

  @Test
  void contextLoads() {
    Assertions.assertNotNull(controller, "Spring context should load, controller should get autowired");
  }

  @Test
  void shouldReturnAccountsAddedByImportDotSqlFile() throws Exception {
    mvc.perform(get("/accounts")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().json("""
            [
              {"accountNumber": "acc 1", "balance": 100 },
              {"accountNumber": "acc 2", "balance": 200 },
              {"accountNumber": "acc 3", "balance": 3000000000.1234 },
              {"accountNumber": "acc 4", "balance": -1000 }
            ]""", true));
  }
}

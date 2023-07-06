package fd.exercise.bookkeeper.api;

import fd.exercise.bookkeeper.api.dtos.AccountDto;
import fd.exercise.bookkeeper.api.dtos.TransferDto;
import fd.exercise.bookkeeper.domain.AccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("accounts")
@AllArgsConstructor
public class AccountsController {
  private final AccountsService accountsService;

  @GetMapping()
  @Operation(summary = "Returns all accounts")
  List<AccountDto> getAll() {
    var result = accountsService.getAll();
    log.info("returning accounts: {}", result);
    return result;
  }

  @PostMapping()
  @Operation(
      summary = "Creates/updates an account",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          content = @Content(
              schema = @Schema(implementation = AccountDto.class),
              examples = {@ExampleObject(value = "{ \"accountNumber\": \"acc 3\", \"balance\": 333.33 }\n")}))
  )
  void save(@RequestBody AccountDto account) {
    accountsService.save(account);
  }

  @PostMapping(value = "transfer")
  @Operation(
      summary = "Transfers specified amount from one account to another",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          content = @Content(
              schema = @Schema(implementation = TransferDto.class),
              examples = {@ExampleObject(value = "{ \"sourceAccount\": \"acc 1\", \"destinationAccount\": \"acc 2\", \"amount\": 10.0 }")}))
  )
  List<AccountDto> transfer(@RequestBody TransferDto transfer) {
    return accountsService.transfer(transfer);
  }
}

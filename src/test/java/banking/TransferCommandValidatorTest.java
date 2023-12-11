package banking;

import org.junit.jupiter.api.BeforeEach;

public class TransferCommandValidatorTest {
	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}
}

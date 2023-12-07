package banking;

import org.junit.jupiter.api.BeforeEach;

public class WithdrawCommandValidatorTest {
	WithdrawCommandValidator withdrawCommandValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		withdrawCommandValidator = new WithdrawCommandValidator(bank);

	}

}

package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawCommandProcessorTest {

	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
		commandProcessor.process("create checking 11111111 1.2");
		commandProcessor.process("create saving 22222222 1.2");
		commandProcessor.process("create CD 33333333 2.2 2000");

	}

	@Test
	void withdrawing_to_a_new_account() {
		commandProcessor.process("deposit 11111111 1000");
		commandProcessor.process("withdraw 11111111 400");

		assertEquals(600, bank.retrieveAccount(11111111).getBalance());
	}

	@Test
	void withdrawing_from_saving_account_twice() {
		commandProcessor.process("deposit 22222222 2000");
		commandProcessor.process("withdraw 22222222 500");
		commandProcessor.process("withdraw 22222222 300");

		assertEquals(1200, bank.retrieveAccount(22222222).getBalance());
	}

	@Test
	void withdrawing_full_amount_from_cd() {
		commandProcessor.process("withdraw 33333333 2000");

		assertEquals(0, bank.retrieveAccount(33333333).getBalance());
	}

	@Test
	void withdrawing_over_the_amount_of_cd_will_be_zero_balance() {
		commandProcessor.process("withdraw 33333333 4000");

		assertEquals(0, bank.retrieveAccount(33333333).getBalance());
	}

	@Test
	void withdrawing_over_the_balance_of_checking_will_be_zero() {
		commandProcessor.process("deposit 11111111 300");
		commandProcessor.process("withdraw 11111111 400");

		assertEquals(0, bank.retrieveAccount(11111111).getBalance());
	}

	@Test
	void withdrawing_over_the_limit_of_saving_will_be_zero() {
		commandProcessor.process("deposit 22222222 600");

		commandProcessor.process("withdraw 22222222 700");

		assertEquals(0, bank.retrieveAccount(22222222).getBalance());
	}

}

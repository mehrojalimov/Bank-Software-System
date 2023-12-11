package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeProcessorTest {
	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
		commandProcessor.process("create checking 11111111 3");
		commandProcessor.process("create saving 22222222 3");
		commandProcessor.process("create CD 33333333 2.1 2000");
		commandProcessor.process("deposit 22222222 1000");
	}

	@Test
	void passing_one_months_to_saving_and_checking_accounts() {
		commandProcessor.process("deposit 11111111 1000");
		commandProcessor.process("pass 1");

		assertEquals(1002.5, bank.retrieveAccount(22222222).getBalance());
		assertEquals(1002.5, bank.retrieveAccount(11111111).getBalance());
	}

	@Test
	void passing_one_month_will_delete_the_accounts_whose_balance_is_zero() {
		commandProcessor.process("pass 1");

		assertFalse(bank.accountExistsByUniqueID(11111111));
	}

	@Test
	void passing_one_month_to_cd_account() {
		commandProcessor.process("pass 1");

		assertEquals(2014.0367928937578, bank.retrieveAccount(33333333).getBalance());
	}

	@Test
	void passing_five_months_to_cd() {
		commandProcessor.process("pass 5");

		assertEquals(2071.1760607677725, bank.retrieveAccount(33333333).getBalance());
	}

	@Test
	void passing_two_months_to_checking() {
		commandProcessor.process("deposit 11111111 600");
		commandProcessor.process("pass 2");

		assertEquals(603.00375, bank.retrieveAccount(11111111).getBalance());
	}

	@Test
	void passing_one_month_to_accounts_whose_balance_is_below_100_will_deduct_25() {
		commandProcessor.process("deposit 11111111 99");
		commandProcessor.process("pass 1");

		assertEquals(74.185, bank.retrieveAccount(11111111).getBalance());

	}
}

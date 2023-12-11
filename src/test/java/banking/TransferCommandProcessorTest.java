package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferCommandProcessorTest {

	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
		commandProcessor.process("create saving 11111111 1.3");
		commandProcessor.process("create saving 22222222 2.3");
		commandProcessor.process("create checking 33333333 3.3");
		commandProcessor.process("create checking 44444444 4.4");
		commandProcessor.process("create cd 55555555 6.6 5000");
		commandProcessor.process("create cd 66666666 3.3 1000");
		commandProcessor.process("deposit 11111111 800");
		commandProcessor.process("deposit 22222222 700");
		commandProcessor.process("deposit 33333333 900");
		commandProcessor.process("deposit 44444444 900");
	}

	@Test
	void transferring_from_saving_account_to_saving() {
		commandProcessor.process("transfer 11111111 22222222 300");

		double actual = bank.retrieveAccount(11111111).getBalance();
		double actual2 = bank.retrieveAccount(22222222).getBalance();
		assertEquals(500, actual);
		assertEquals(1000, actual2);
	}

	@Test
	void transferring_from_saving_account_to_checking() {
		commandProcessor.process("transfer 11111111 33333333 400");

		double actual = bank.retrieveAccount(11111111).getBalance();
		double actual2 = bank.retrieveAccount(33333333).getBalance();

		assertEquals(500, actual);
		assertEquals(1000, actual2);
	}

	@Test
	void transferring_from_checking_account_to_checking() {
		commandProcessor.process("transfer 33333333 44444444 250");

		double actual = bank.retrieveAccount(33333333).getBalance();
		double actual2 = bank.retrieveAccount(44444444).getBalance();

		assertEquals(650, actual);
		assertEquals(1150, actual2);
	}

	@Test
	void transferring_from_checking_to_saving() {
		commandProcessor.process("transfer 44444444 22222222 150");

		double actual = bank.retrieveAccount(44444444).getBalance();
		double actual2 = bank.retrieveAccount(22222222).getBalance();

		assertEquals(750, actual);
		assertEquals(850, actual2);
	}

	@Test
	void transferring_from_accounts_twice() {
		commandProcessor.process("transfer 11111111 33333333 400");
		commandProcessor.process("transfer 33333333 11111111 400");

		double actual = bank.retrieveAccount(11111111).getBalance();
		double actual2 = bank.retrieveAccount(33333333).getBalance();

		assertEquals(800, actual);
		assertEquals(900, actual2);
	}

	@Test
	void transferring_more_than_what_si_in_balance_will_transfer_only_existing_money() {
		commandProcessor.process("transfer 11111111 22222222 900");

		double actual = bank.retrieveAccount(11111111).getBalance();
		double actual2 = bank.retrieveAccount(22222222).getBalance();

		assertEquals(0, actual);
		assertEquals(1500, actual2);
	}
}

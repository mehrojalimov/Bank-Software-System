package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {

	MasterControl masterControl;
	List<String> input;

	private void assertSingleCommand(String command, List<String> actual) {
		assertEquals(1, actual.size());
		assertEquals(command, actual.get(0));
	}

	@BeforeEach
	void setUp() {
		input = new ArrayList<>();
		Bank bank = new Bank();
		masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank),
				new CommandStorage(bank));
	}

	@Test
	void typo_in_create_command_is_invalid() {
		input.add("creat checking 12345678 1.0");

		List<String> actual = masterControl.start(input);

		assertSingleCommand("creat checking 12345678 1.0", actual);
	}

	@Test
	void typo_in_deposit_command_is_invalid() {
		input.add("deposit 12345678 100");

		List<String> actual = masterControl.start(input);

		assertSingleCommand("deposit 12345678 100", actual);
	}

	@Test
	void two_typo_commands_both_invalid() {
		input.add("creat checking 12345678 1.0");
		input.add("deposit 12345678 100");

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("creat checking 12345678 1.0", actual.get(0));
		assertEquals("deposit 12345678 100", actual.get(1));
	}

	@Test
	void create_account() {
		input.add("create checking 12345678 1.0");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("Checking 12345678 0.00 1.00", actual);
	}

	@Test
	void invalid_to_create_account_with_the_same_id() {
		input.add("create checking 12345678 1.0");
		input.add("create checking 12345678 1.0");

		List<String> actual = masterControl.start(input);
		assertEquals(actual.size(), 2);
		assertEquals("Checking 12345678 0.00 1.00", actual.get(0));
		assertEquals("create checking 12345678 1.0", actual.get(1));
	}

	@Test
	void sample_test() {
		input.add("Create saving 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 5000");
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");

		List<String> actual = masterControl.start(input);
		assertEquals("Saving 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Deposit 12345678 5000", actual.get(4));
	}

	@Test
	void test_the_accounts_that_was_created_twice() {
		input.add("create Saving 12345678 0.6");
		input.add("CreAte Checking 12345678 0.6");
		input.add("Deposit 12345678 100");
		input.add("deposit 12345678 600");
		input.add("create checking 11111111 1");
		input.add("transfer 12345678 11111111 100");

		List<String> actual = masterControl.start(input);
		assertEquals("Saving 12345678 600.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 100", actual.get(1));
		assertEquals("Deposit 12345678 600", actual.get(2));
		assertEquals("Transfer 12345678 11111111 100", actual.get(3));
		assertEquals("Checking 11111111 100.00 1.00", actual.get(4));
		assertEquals("Transfer 12345678 11111111 100", actual.get(5));
	}

}

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {
	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	void valid_command() {
		boolean actual = commandValidator.validate("create Checking 12345678 1.5");
		assertTrue(actual);
	}

	@Test
	void duplicate_unique_id_is_invalid() {
		bank.addCheckingAccount(12345678, 2.5);
		boolean actual = commandValidator.validate("create Checking 12345678 2.2");
		assertFalse(actual);
	}

	@Test
	void deposit_money_to_an_account_is_valid() {
		bank.addSavingAccount(11111111, 1.1);
		boolean actual = commandValidator.validate("deposit 11111111 1000");
		assertTrue(actual);
	}

	@Test
	void creating_account_without_apr_is_invalid() {
		boolean actual = commandValidator.validate("create Checking 12345677");
		assertFalse(actual);
	}

	@Test
	void deposit_to_nonexistent_account_is_invalid() {
		boolean actual = commandValidator.validate("deposit 99999999 500");
		assertFalse(actual);
	}

	@Test
	void valid_cd_account_creation() {
		boolean actual = commandValidator.validate("create CD 87654321 1.8 5000");
		assertTrue(actual);
	}

	@Test
	void creating_cd_without_specified_amount_is_invalid() {
		boolean actual = commandValidator.validate("create CD 22222222 2.2");
		assertFalse(actual);
	}

	@Test
	void creating_savings_account_is_valid() {
		boolean actual = commandValidator.validate("create Saving 44444444 1.1");
		assertTrue(actual);
	}

	@Test
	void creating_Checking_or_Saving_with_specific_amount_is_invalid() {
		boolean actual = commandValidator.validate("create Checking 55555555 1.1 6000");
		assertFalse(actual);
	}

	@Test
	void creating_account_with_unique_id_of_eight_digit_numbers_is_valid() {
		boolean actual = commandValidator.validate("create Saving 12345678 1.1");
		assertTrue(actual);
	}

	@Test
	void creating_account_with_apr_out_of_range_of_zero_to_ten_is_invalid() {
		boolean actual = commandValidator.validate("create Checking 11111111 10.5");
		assertFalse(actual);
	}

	@Test
	void creating_account_with_negative_unique_id_is_invalid() {
		boolean actual = commandValidator.validate("create CD -55555555 1.5 1000");
		assertFalse(actual);
	}

	@Test
	void creating_account_with_negative_apr_is_invalid() {
		boolean actual = commandValidator.validate("create Saving 12345678 -2.2");
		assertFalse(actual);
	}

	@Test
	void creating_CD_with_over_the_limit_amount_is_invalid() {
		boolean actual = commandValidator.validate("create CD 44444444 1.1 11000");
		assertFalse(actual);
	}

	@Test
	void creating_command_is_test_case_insensitive() {
		boolean actual = commandValidator.validate("CreAte Saving 77777777 1.1");
		assertTrue(actual);
	}

	@Test
	void creating_CD_with_missing_amount_is_invalid() {
		boolean actual = commandValidator.validate("Create CD 88888888 9");
		assertFalse(actual);
	}
}

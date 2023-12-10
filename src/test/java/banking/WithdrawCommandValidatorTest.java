package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawCommandValidatorTest {
	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new WithdrawCommandValidator(bank);
		bank.addSavingAccount(12345678, 1.5);
		bank.addCheckingAccount(77777777, 6.3);
		bank.addCDAccount(88888888, 3.3, 5000);
		bank.deposit(12345678, 1500);
		bank.deposit(77777777, 900);

	}

	@Test
	void withdrawing_300_dollars_from_an_saving_account_is_valid() {
		boolean actual = commandValidator.validate("withdraw 12345678 300");
		assertTrue(actual);
	}

	@Test
	void withdrawing_money_over_the_saving_limit_is_invalid() {
		bank.deposit(12345678, 700);

		boolean actual = commandValidator.validate("withdraw 12345678 1100");
		assertFalse(actual);
	}

	@Test
	void withdrawing_399_dollars_from_checking_limit_is_valid() {
		boolean actual = commandValidator.validate("withdraw 77777777 399");
		assertTrue(actual);
	}

	@Test
	void withdrawing_401_dollars_that_is_over_the_withdraw_limit_is_invalid() {
		boolean actual = commandValidator.validate("Withdraw 77777777 401");
		assertFalse(actual);
	}

	@Test
	void withdrawing_with_mixed_commands_is_invalid() {
		boolean actual = commandValidator.validate("12345678 withdraw 100");
		assertFalse(actual);
	}

	@Test
	void withdrawing_is_case_insensitive() {
		boolean actual = commandValidator.validate("WiThDraW 77777777 200");
		assertTrue(actual);
	}

	@Test
	void withdrawing_money_less_than_full_amount_of_cd_is_invalid() {
		boolean actual = commandValidator.validate("withdraw 88888888 100");
		assertFalse(actual);
	}

	@Test
	void withdrawing_full_amount_from_cd_is_valid() {
		boolean actual = commandValidator.validate("withdraw 88888888 5000");
		assertTrue(actual);
	}

	@Test
	void withdrawing_over_full_amount_of_cd_accounts_is_valid() {
		boolean actual = commandValidator.validate("withdraw 88888888 6000");
		assertTrue(actual);
	}

	@Test
	void withdrawing_from_non_existing_account_is_invalid() {
		boolean actual = commandValidator.validate("withdraw 99999999 500");

		assertFalse(actual);
	}

	@Test
	void withdrawing_from_non_eight_digit_account_is_invalid() {
		boolean actual = commandValidator.validate("withdraw 1234567 100");

		assertFalse(actual);
	}

	@Test
	void type_in_withdrawing_command_is_invalid() {
		boolean actual = commandValidator.validate("withdra 12345678 200");

		assertFalse(actual);
	}

	@Test
	void missing_amount_in_withdrawing_command_is_invalid() {
		boolean actual = commandValidator.validate("withdraw 12345678 ");

		assertFalse(actual);
	}

	@Test
	void withdrawing_negative_amount_is_invalid() {
		boolean actual = commandValidator.validate("withdraw 12345678 -200");

		assertFalse(actual);
	}

	@Test
	void withdrawing_zero_amount_is_valid() {
		boolean actual = commandValidator.validate("withdraw 12345678 0");

		assertTrue(actual);
	}

	@Test
	void withdrawing_from_negative_account_id_is_invalid() {
		boolean actual = commandValidator.validate("withdraw -12345678 230");

		assertFalse(actual);
	}

}

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
		bank.retrieveAccount(88888888).setPassTime(12);
		boolean actual = commandValidator.validate("withdraw 88888888 5000");
		assertTrue(actual);
	}

	@Test
	void withdrawing_over_full_amount_of_cd_accounts_is_valid() {
		bank.retrieveAccount(88888888).setPassTime(13);
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

	@Test
	void withdrawing_to_id_with_letters_is_invalid() {
		boolean actual = commandValidator.validate("withdraw 1234567a 200");

		assertFalse(actual);
	}

	@Test
	void withdrawing_amount_with_letters_into_account_is_invalid() {
		boolean actual = commandValidator.validate("withdraw 12345678 10a");

		assertFalse(actual);
	}

	@Test
	void withdrawing_from_saving_twice_at_the_same_month_is_invalid() {
		bank.withdraw(12345678, 500);
		boolean actual = commandValidator.validate("withdraw 12345678 600");

		assertFalse(actual);
	}

	@Test
	void withdrawing_from_saving_twice_between_one_month_after_is_valid() {
		bank.withdraw(12345678, 500);
		bank.retrieveAccount(12345678).setPassTime(1);

		boolean actual = commandValidator.validate("withdraw 12345678 600");

		assertTrue(actual);
	}

	@Test
	void withdrawing_from_checking_twice_is_valid() {
		bank.withdraw(77777777, 250);

		boolean actual = commandValidator.validate("withdraw 77777777 250");

		assertTrue(actual);
	}

	@Test
	void withdrawing_money_cd_when_12_months_not_passed_is_invalid() {
		bank.retrieveAccount(88888888).setPassTime(9);

		boolean actual = commandValidator.validate("withdraw 88888888 5000");

		assertFalse(actual);
	}

	@Test
	void withdrawing_from_99999999_and_10000000_is_valid() {
		bank.addCheckingAccount(99999999, 2.3);
		bank.addSavingAccount(10000000, 2.5);
		bank.retrieveAccount(99999999).deposit(200);
		bank.retrieveAccount(10000000).deposit(250);

		boolean actual = commandValidator.validate("withdraw 99999999 100");

		boolean actual1 = commandValidator.validate("withdraw 10000000 100");

		assertTrue(actual);
		assertTrue(actual1);
	}

	@Test
	void withdrawing_from_9999999_and_10000001_is_valid() {
		bank.addCheckingAccount(9999999, 2.3);
		bank.addSavingAccount(100000001, 2.5);
		bank.retrieveAccount(9999999).deposit(200);
		bank.retrieveAccount(100000001).deposit(250);

		boolean actual = commandValidator.validate("withdraw 9999999 100");

		boolean actual1 = commandValidator.validate("withdraw 100000001 100");

		assertFalse(actual);
		assertFalse(actual1);
	}

}

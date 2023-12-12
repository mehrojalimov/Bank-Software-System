package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferCommandValidatorTest {
	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
		bank.addCheckingAccount(22222222, 2.5);
		bank.addCheckingAccount(22222220, 6.6);
		bank.deposit(22222222, 800);
		bank.deposit(22222220, 900);
		bank.addSavingAccount(11111111, 1.5);
		bank.addSavingAccount(11111110, 7.5);
		bank.deposit(11111111, 900);
		bank.deposit(11111110, 800);
		bank.addCDAccount(33333333, 3.3, 10000);
		bank.addCDAccount(55555555, 2.2, 5000);
	}

	@Test
	void transfer_form_checking_account_to_saving_account_is_valid() {
		boolean actual = commandValidator.validate("transfer 22222222 11111111 100");

		assertTrue(actual);
	}

	@Test
	void transferring_from_saving_to_checking_account_is_valid() {
		boolean actual = commandValidator.validate("transfer 11111111 22222222 100");

		assertTrue(actual);
	}

	@Test
	void transferring_from_checking_to_checking_is_valid() {
		boolean actual = commandValidator.validate("transfer 22222222 22222220 300");

		assertTrue(actual);
	}

	@Test
	void transferring_from_saving_to_saving_is_valid() {
		boolean actual = commandValidator.validate("transfer 11111111 11111110 500");

		assertTrue(actual);
	}

	@Test
	void transferring_from_cd_to_saving_is_invalid() {
		boolean actual = commandValidator.validate("transfer 33333333 11111111 250");

		assertFalse(actual);
	}

	@Test
	void transferring_from_cd_to_checking_is_invalid() {
		boolean actual = commandValidator.validate("transfer 33333333 22222222 150");

		assertFalse(actual);
	}

	@Test
	void transferring_from_checking_to_checking_is_invalid() {
		boolean actual = commandValidator.validate("transfer 33333333 55555555 350");

		assertFalse(actual);
	}

	@Test
	void transferring_from_saving_to_cd_is_invalid() {
		boolean actual = commandValidator.validate("transfer 11111111 33333333 600");

		assertFalse(actual);
	}

	@Test
	void transferring_from_checking_to_cd_is_invalid() {
		boolean actual = commandValidator.validate("transfer 22222222 33333333 700");

		assertFalse(actual);
	}

	@Test
	void transferring_command_with_missing_one_of_the_accounts_is_invalid() {
		boolean actual = commandValidator.validate("transfer 1111111 700");

		assertFalse(actual);
	}

	@Test
	void transferring_command_with_missing_amount_is_invalid() {
		boolean actual = commandValidator.validate("transfer 11111111 22222222");

		assertFalse(actual);
	}

	@Test
	void transferring_the_negative_value_is_invalid() {
		boolean actual = commandValidator.validate("transfer 22222222 22222220 -100");

		assertFalse(actual);
	}

	@Test
	void transferring_to_the_negative_account_is_invalid() {
		boolean actual = commandValidator.validate("transfer 22222222 -11111111 100");

		assertFalse(actual);
	}

	@Test
	void transferring_from_negative_account_is_invalid() {
		boolean actual = commandValidator.validate("transferring -11111111 22222222 100");

		assertFalse(actual);
	}

	@Test
	void transferring_from_and_to_the_negative_accounts_is_invalid() {
		boolean actual = commandValidator.validate("transfer -11111111 -11111110 330");

		assertFalse(actual);
	}

	@Test
	void transfer_command_being_case_insensitive_is_valid() {
		boolean actual = commandValidator.validate("TraNsFER 11111111 22222220 360");

		assertTrue(actual);
	}

	@Test
	void using_letters_in_account_in_transfer_command_is_invalid() {
		boolean actual = commandValidator.validate("transfer 1111111a 22222222 200");

		assertFalse(actual);
	}

	@Test
	void using_letters_instead_of_amount_is_invalid() {
		boolean actual = commandValidator.validate("transfer 11111110 22222220 20a");

		assertFalse(actual);
	}

	@Test
	void transferring_money_from_the_over_the_limit_of_saving_is_invalid() {
		bank.deposit(11111111, 900);
		boolean actual = commandValidator.validate("transfer 11111111 22222222 1100");

		assertFalse(actual);
	}

	@Test
	void transferring_from_over_limit_of_checking_account_is_invalid() {
		boolean actual = commandValidator.validate("transfer 22222222 11111111 500");

		assertFalse(actual);
	}

	@Test
	void transferring_from_saving_to_the_exact_limit_of_checking_is_invalid() {
		boolean actual = commandValidator.validate("transfer 11111111 22222220 1000");

		assertTrue(actual);
	}

	@Test
	void transferring_money_with_mixed_command_is_invalid() {
		boolean actual = commandValidator.validate("11111111 transfer 22222222 300");

		assertFalse(actual);
	}

	@Test
	void transferring_from_non_existing_account_is_invalid() {
		boolean actual = commandValidator.validate("transfer 12345677 11111111 255");

		assertFalse(actual);
	}

	@Test
	void transferring_to_non_existing_account_is_invalid() {
		boolean actual = commandValidator.validate("transfer 11111111 12345679 155");

		assertFalse(actual);
	}

	@Test
	void transferring_money_with_typo_in_the_name_of_transfer_is_invalid() {
		boolean actual = commandValidator.validate("transfe 11111111 2222222 33");

		assertFalse(actual);
	}

	@Test
	void transferring_zero_amount_is_valid() {
		boolean actual = commandValidator.validate("transfer 11111111 22222222 0");

		assertTrue(actual);
	}

	@Test
	void transferring_to_the_same_account_is_invalid() {
		boolean actual = commandValidator.validate("transfer 11111111 11111111 120");

		assertFalse(actual);
	}

	@Test
	void transferring_from_cd_to_saving__is_invalid() {
		bank.addCDAccount(33333331, 3.3, 100);
		boolean actual = commandValidator.validate("transfer 33333331 11111111 100");

		assertFalse(actual);
	}

	@Test
	void transferring_from_cd_to_saving_one_is_invalid() {
		bank.addCDAccount(33333331, 3.3, 0);
		boolean actual = commandValidator.validate("transfer 33333331 11111111 0");

		assertFalse(actual);
	}

}

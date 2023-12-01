package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountsTest {
	private static final double APR = 5.5;
	private static final int UNIQUE_ID = 12345678;
	Checking checking;
	Saving saving;

	@BeforeEach
	void setUp() {
		checking = new Checking("Checking", UNIQUE_ID, APR);
		saving = new Saving("Saving", UNIQUE_ID, APR);
	}

	@Test
	void checking_and_saving_accounts_when_created_stats_with_0_balance() {
		double actual = checking.getBalance();
		double actual1 = saving.getBalance();

		assertEquals(0, actual);
		assertEquals(0, actual1);
	}

	@Test
	void creating_account_with_APR() {
		double actual = saving.getAPR();

		assertEquals(5.5, actual);
	}

	@Test
	void deposit_50_dollars_to_the_balance() {
		checking.deposit(50);

		double actual = checking.getBalance();
		assertEquals(50, actual);
	}

	@Test
	void withdraw_money_from_balance() {
		saving.deposit(150);
		saving.withdraw(10);

		double actual = saving.getBalance();
		assertEquals(140, actual);

	}

	@Test
	void withdrawing_cannot_go_bellow_zero() {
		checking.withdraw(150);

		double actual = checking.getBalance();
		assertEquals(0, actual);
	}

	@Test
	void deposit_twice_into_account() {
		checking.deposit(50.50);
		checking.deposit(150.50);

		double actual = checking.getBalance();

		assertEquals(201.00, actual);
	}

	@Test
	void withdraw_twice_from_account() {
		saving.deposit(1000);
		saving.withdraw(200);
		saving.withdraw(100);

		double actual = saving.getBalance();

		assertEquals(700, actual);
	}
}
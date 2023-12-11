package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CDTest {

	private static final double APR = 3.2;
	private static final double BALANCE = 1000.00;
	private static final int UNIQUE_ID = 12345688;

	@Test
	void get_the_balance_of_cd() {
		CD cd = new CD("CD", UNIQUE_ID, APR, BALANCE);

		double actual = cd.getBalance();

		assertEquals(1000, actual);

	}
}

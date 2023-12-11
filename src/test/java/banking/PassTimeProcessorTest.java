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
		commandProcessor.process("create checking 11111111 1.2");
		commandProcessor.process("create saving 22222222 3");
		commandProcessor.process("create CD 33333333 2.1 2000");
		commandProcessor.process("deposit 22222222 1000");
	}

	@Test
	void passing_one_months() {
		commandProcessor.process("pass 1");

		assertFalse(bank.accountExistsByUniqueID(11111111));
		assertEquals(1002.5, bank.retrieveAccount(22222222).getBalance());
		assertEquals(2014.0367928937578, bank.retrieveAccount(33333333).getBalance());
	}
}

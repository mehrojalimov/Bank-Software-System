package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	void bank_has_the_correct_checking_with_ID_and_APR_as_in_is_the_command() {
		commandProcessor.process("create checking 11111111 1.2");

		assertNotNull(bank.retrieveAccount(11111111));
		assertEquals(1.2, bank.retrieveAccount(11111111).getAPR());
		assertEquals("checking", bank.retrieveAccount(11111111).getAccountType());
	}

	@Test
	void bank_creates_CD_account_with_right_ID_apr_amount() {
		commandProcessor.process("create CD 12345678 2.2 2000");

		assertNotNull(bank.retrieveAccount(12345678));
		assertEquals(12345678, bank.retrieveAccount(12345678).getUNIQUE_ID());
		assertEquals(2.2, bank.retrieveAccount(12345678).getAPR());
		assertEquals("cd", bank.retrieveAccount(12345678).getAccountType());
		assertEquals(2000, bank.retrieveAccount(12345678).getBalance());
	}

	@Test
	void bank_creates_saving_account_with_correct_id_and_apr() {
		commandProcessor.process("create Saving 99999999 6.6");

		assertNotNull(bank.retrieveAccount(99999999));
		assertEquals(99999999, bank.retrieveAccount(99999999).getUNIQUE_ID());
		assertEquals(6.6, bank.retrieveAccount(99999999).getAPR());
		assertEquals("saving", bank.retrieveAccount(99999999).getAccountType());
	}

	@Test
	void bank_can_store_multiple_accounts() {
		commandProcessor.process("create Saving 88888888 5.5");
		commandProcessor.process("create CD 77777777 3.3 5000");

		assertNotNull(bank.retrieveAccount(88888888));
		assertNotNull(bank.retrieveAccount(77777777));

		assertEquals("saving", bank.retrieveAccount(88888888).getAccountType());
		assertEquals(5.5, bank.retrieveAccount(88888888).getAPR());

		assertEquals("cd", bank.retrieveAccount(77777777).getAccountType());
		assertEquals(3.3, bank.retrieveAccount(77777777).getAPR());
		assertEquals(5000, bank.retrieveAccount(77777777).getBalance());
	}

	@Test
	void depositing_to_a_new_account() {
		commandProcessor.process("create Saving 55555555 1.1");
		commandProcessor.process("deposit 55555555 2000");

		assertEquals(2000, bank.retrieveAccount(55555555).getBalance());
	}

	@Test
	void depositing_to_an_existing_account_two_time() {
		commandProcessor.process("create Checking 22222222 2.3");
		commandProcessor.process("deposit 22222222 1000");
		commandProcessor.process("deposit 22222222 500");

		assertEquals(1500, bank.retrieveAccount(22222222).getBalance());
	}
}

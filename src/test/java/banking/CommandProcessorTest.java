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
        commandProcessor.process("create checking 11111111 1.2");
        commandProcessor.process("create saving 22222222 1.2");
        commandProcessor.process("create CD 33333333 2.2 2000");

    }

    @Test
    void bank_has_the_correct_checking_with_ID_and_APR_as_in_is_the_command() {
        assertNotNull(bank.retrieveAccount(11111111));
        assertEquals(1.2, bank.retrieveAccount(11111111).getAPR());
        assertEquals("checking", bank.retrieveAccount(11111111).getAccountType());
    }

    @Test
    void bank_creates_CD_account_with_right_ID_apr_amount() {
        assertNotNull(bank.retrieveAccount(33333333));
        assertEquals(33333333, bank.retrieveAccount(33333333).getUNIQUE_ID());
        assertEquals(2.2, bank.retrieveAccount(33333333).getAPR());
        assertEquals("cd", bank.retrieveAccount(33333333).getAccountType());
        assertEquals(2000, bank.retrieveAccount(33333333).getBalance());
    }

    @Test
    void bank_creates_saving_account_with_correct_id_and_apr() {
        assertNotNull(bank.retrieveAccount(22222222));
        assertEquals(22222222, bank.retrieveAccount(22222222).getUNIQUE_ID());
        assertEquals(1.2, bank.retrieveAccount(22222222).getAPR());
        assertEquals("saving", bank.retrieveAccount(22222222).getAccountType());
    }

    @Test
    void bank_can_store_multiple_accounts() {
        assertNotNull(bank.retrieveAccount(22222222));
        assertNotNull(bank.retrieveAccount(33333333));

        assertEquals("saving", bank.retrieveAccount(22222222).getAccountType());
        assertEquals(1.2, bank.retrieveAccount(22222222).getAPR());

        assertEquals("cd", bank.retrieveAccount(33333333).getAccountType());
        assertEquals(2.2, bank.retrieveAccount(33333333).getAPR());
        assertEquals(2000, bank.retrieveAccount(33333333).getBalance());
    }

    @Test
    void depositing_to_a_new_account() {
        commandProcessor.process("deposit 22222222 2000");

        assertEquals(2000, bank.retrieveAccount(22222222).getBalance());
    }

    @Test
    void depositing_to_an_existing_account_two_time() {
        commandProcessor.process("deposit 11111111 1000");
        commandProcessor.process("deposit 11111111 500");

        assertEquals(1500, bank.retrieveAccount(11111111).getBalance());
    }

    @Test
    void testing_set_pass_time() {
        commandProcessor.process("create saving 55555555 1.2");
        commandProcessor.process("deposit 55555555 350");
        commandProcessor.process("pass 2");

        int actual = bank.retrieveAccount(55555555).getPassTime();

        assertEquals(2, actual);
    }


}

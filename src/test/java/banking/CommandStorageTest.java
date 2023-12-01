package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStorageTest {

	CommandStorage commandStorage;

	@BeforeEach
	void setUp() {
		commandStorage = new CommandStorage();
	}

	@Test
	void storing_invalid_commands() {
		commandStorage.addInvalidCommand("Create 1111111 11.5");
		assertEquals(1, commandStorage.getInvalidCommands().size());
		assertEquals("Create 1111111 11.5", commandStorage.getInvalidCommands().get(0));
	}

	@Test
	void stores_multiple_invalid_commands() {
		commandStorage.addInvalidCommand("Checking 11111111 3.3");
		commandStorage.addInvalidCommand("Deposit 1111111");
		commandStorage.addInvalidCommand("Create CD 12345678 3.3");

		assertEquals(3, commandStorage.getInvalidCommands().size());
		assertTrue(commandStorage.getInvalidCommands().contains("Checking 11111111 3.3"));
		assertTrue(commandStorage.getInvalidCommands().contains("Deposit 1111111"));
		assertTrue(commandStorage.getInvalidCommands().contains("Create CD 12345678 3.3"));

	}
}

package banking;

import java.util.ArrayList;
import java.util.List;

public class CommandStorage {

	private final List<String> storedCommands;

	public CommandStorage() {
		this.storedCommands = new ArrayList<>();
	}

	public void addInvalidCommand(String command) {
		storedCommands.add(command);
	}

	public List<String> getInvalidCommands() {
		return new ArrayList<>(storedCommands);
	}
}

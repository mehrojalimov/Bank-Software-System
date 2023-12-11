package banking;

import java.util.ArrayList;
import java.util.List;

public class CommandStorage {
	private final List<String> invalidCommands;
	private final ArrayList<String> validCommands;
	private final Bank bank;

	public CommandStorage(Bank bank) {
		this.invalidCommands = new ArrayList<>();
		this.validCommands = new ArrayList<>();
		this.bank = bank;
	}

	public void addInvalidCommand(String command) {
		invalidCommands.add(command);
	}

	public void addValidCommand(String command) {
		validCommands.add(command);
	}

	public ArrayList<String> getValidCommands() {
		return this.validCommands;
	}

	public List<String> getInvalidCommands() {
		return this.invalidCommands;
	}

	public ArrayList<String> getAccountActivities() {
		ArrayList<String> doReturn = new ArrayList<>();
		for (String command : getValidCommands()) {
			String[] arrayCommand = command.split(" ");
			if (arrayCommand[0].equalsIgnoreCase("create")) {
				int uniqueId = Integer.parseInt(arrayCommand[2]);
				if (bank.accountExistsByUniqueID(uniqueId)) {
					Account account = this.bank.retrieveAccount(uniqueId);
					doReturn.addAll(account.getValidCommands());
				}
			}
		}
		return doReturn;
	}

	public List<String> getAllActivities() {
		ArrayList<String> doReturn = new ArrayList<>();
		doReturn.addAll(getAccountActivities());
		doReturn.addAll(getInvalidCommands());
		return doReturn;
	}
}

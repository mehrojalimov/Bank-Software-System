package banking;

import java.util.ArrayList;
import java.util.List;

public class CommandStorage {
	private List<String> invalidCommands;
	private ArrayList<String> validCommands;
	private Bank bank;

	public CommandStorage() {
		this.invalidCommands = new ArrayList<>();
		this.validCommands = new ArrayList<>();
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
				Account account = this.bank.retrieveAccount(uniqueId);
				if (account != null) {
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

package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

	public List<String> getAccountActivities() {
		List<String> activities = new ArrayList<>();

		for (String command : getValidCreateCommands()) {
			int uniqueId = extractUniqueId(command);

			if (bank.accountExistsByUniqueID(uniqueId)) {
				Account account = bank.retrieveAccount(uniqueId);
				activities.addAll(account.getValidCommands());
			}
		}

		return activities;
	}

	private List<String> getValidCreateCommands() {
		return getValidCommands().stream().filter(command -> command.trim().toLowerCase().startsWith("create"))
				.collect(Collectors.toList());
	}

	private int extractUniqueId(String command) {
		String[] arrayCommand = command.split(" ");
		return Integer.parseInt(arrayCommand[2]);
	}

	public List<String> getAllActivities() {
		ArrayList<String> doReturn = new ArrayList<>();
		doReturn.addAll(getAccountActivities());
		doReturn.addAll(getInvalidCommands());
		return doReturn;
	}
}

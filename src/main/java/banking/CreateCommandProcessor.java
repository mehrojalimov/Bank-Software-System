package banking;

public class CreateCommandProcessor {
	private final Bank bank;

	public CreateCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void processCommand(String[] parts) {
		String accountType = parts[1].toLowerCase();
		int uniqueId = Integer.parseInt(parts[2]);
		double apr = Double.parseDouble(parts[3]);

		switch (accountType) {
		case "checking":
			bank.addCheckingAccount(uniqueId, apr);
			break;
		case "cd":
			double amount = Double.parseDouble(parts[4]);
			bank.addCDAccount(uniqueId, apr, amount);
			break;
		case "saving":
			bank.addSavingAccount(uniqueId, apr);
			break;
		}
	}
}

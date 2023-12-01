package banking;

public class DepositCommandProcessor {

	private final Bank bank;

	public DepositCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void processCommand(String[] parts) {
		int uniqueId = Integer.parseInt(parts[1]);
		double amount = Double.parseDouble(parts[2]);

		bank.deposit(uniqueId, amount);
	}
}

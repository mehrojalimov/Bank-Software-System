package banking;

public class WithdrawCommandProcessor {

	private final Bank bank;

	public WithdrawCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void processCommand(String[] parts) {
		int uniqueId = Integer.parseInt(parts[1]);
		double amount = Double.parseDouble(parts[2]);

		bank.withdraw(uniqueId, amount);
	}
}

package banking;

public class TransferCommandProcessor {

	private final Bank bank;

	public TransferCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	// transfer 11111111 22222222 100
	public void processCommand(String[] parts) {
		int accountFrom = Integer.parseInt(parts[1]);
		int accountTo = Integer.parseInt(parts[2]);
		double amount = Double.parseDouble(parts[3]);
		double transferableAmount = bank.retrieveAccount(accountFrom).getTransferAmount(amount);

		bank.withdraw(accountFrom, transferableAmount);
		bank.deposit(accountTo, transferableAmount);
	}

}

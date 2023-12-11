package banking;

public class PassTimeProcessor {

	private final Bank bank;

	public PassTimeProcessor(Bank bank) {
		this.bank = bank;
	}

	public void processCommand(String[] parts) {
		int passMonths = Integer.parseInt(parts[1]);

		for (int i = 0; i < passMonths; i++) {
			bank.passTime(1);
		}
	}
}

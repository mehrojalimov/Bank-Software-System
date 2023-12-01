package banking;

public abstract class CommandValidationProcessor {

	protected final Bank bank;

	public CommandValidationProcessor(Bank bank) {
		this.bank = bank;
	}

	public abstract boolean validateCommand(String[] parts);
}
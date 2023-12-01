package banking;

public class CommandValidator {

	private final Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] parts = command.split(" ");
		String accountCommand = parts[0].toLowerCase();

		CommandValidationProcessor processor = null;

		switch (accountCommand) {
		case "create":
			processor = new CreateCommandValidator(bank);
			break;
		case "deposit":
			processor = new DepositCommandValidator(bank);
			break;
		}

		return processor != null && processor.validateCommand(parts);
	}
}

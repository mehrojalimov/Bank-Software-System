package banking;

public class CommandProcessor {

	private final Bank bank;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String command) {
		String[] parts = command.split(" ");
		String commandType = parts[0].toLowerCase();
		switch (commandType) {
		case "create":
			new CreateCommandProcessor(bank).processCommand(parts);
			break;
		case "deposit":
			new DepositCommandProcessor(bank).processCommand(parts);
			bank.addActivity(parts[1], command);
			break;

		case "withdraw":
			new WithdrawCommandProcessor(bank).processCommand(parts);
			bank.addActivity(parts[1], command);
			break;

		case "transfer":
			new TransferCommandProcessor(bank).processCommand(parts);
			bank.addActivity(parts[1], command);
			bank.addActivity(parts[2], command);
			break;
		case "pass":
			new PassTimeProcessor(bank).processCommand(parts);
			break;
		default:
			break;
		}
	}
}
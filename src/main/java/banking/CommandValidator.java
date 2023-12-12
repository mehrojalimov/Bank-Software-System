package banking;

public class CommandValidator {

	protected Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] parts = command.split(" ");
		String accountCommand = parts[0].toLowerCase();

		CreateCommandValidator create;
		DepositCommandValidator deposit;
		WithdrawCommandValidator withdraw;
		TransferCommandValidator transfer;
		PassTimeValidator passTimeValidator;

		switch (accountCommand) {
		case "create":
			create = new CreateCommandValidator(bank);
			return create.validateCommand(parts);
		case "deposit":
			deposit = new DepositCommandValidator(bank);
			return deposit.validateCommand(parts);
		case "withdraw":
			withdraw = new WithdrawCommandValidator(bank);
			return withdraw.validateCommand(parts);
		case "transfer":
			transfer = new TransferCommandValidator(bank);
			return transfer.validateCommand(parts);
		case "pass":
			passTimeValidator = new PassTimeValidator(bank);
			return passTimeValidator.validateCommand(parts);
		default:
			return false;
		}

	}
}

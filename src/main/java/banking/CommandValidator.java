package banking;

public class CommandValidator {

	protected Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] parts = command.split(" ");

		CreateCommandValidator create;
		DepositCommandValidator deposit;
		WithdrawCommandValidator withdraw;
		TransferCommandValidator transfer;
		PassTimeValidator passTimeValidator;

		switch (parts[0].toLowerCase()) {
		case "create":
			create = new CreateCommandValidator(bank);
			return returnCreate(create, parts);
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

	public boolean returnCreate(CreateCommandValidator create, String[] parts) {
		return create.validateCommand(parts);
	}
}

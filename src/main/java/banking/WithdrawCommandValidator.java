package banking;

public class WithdrawCommandValidator extends CommandValidator {

	public WithdrawCommandValidator(Bank bank) {
		super(bank);
	}

	public boolean validateCommand(String[] parts) {
		return parts.length == 3 && bank.accountExistsByUniqueID(getAccountId(parts[1]))
				&& bank.retrieveAccount(Integer.parseInt(parts[1])).isInValidMaximumRange(Integer.parseInt(parts[2]))

				&& isIncorrectPassTime(parts[1]);
	}

	private boolean isIncorrectPassTime(String uniqueId) {
		return true;
	}

	private int getAccountId(String uniqueId) {
		return Integer.parseInt(uniqueId);
	}
}

package banking;

public class WithdrawCommandValidator extends CommandValidator {

	public WithdrawCommandValidator(Bank bank) {
		super(bank);
	}

	public boolean validateCommand(String[] parts) {
		return parts.length == 3 && bank.accountExistsByUniqueID(getAccountId(parts[1]))
				&& isInValidMaximumRange(parts[1], parts[2])

				&& isIncorrectPassTime(parts[1]);
	}

	private boolean isInValidMaximumRange(String uniqueId, String depositAmountStr) {
		try {
			int accountId = Integer.parseInt(uniqueId);
			Account account = bank.retrieveAccount(accountId);

			if (account != null) {
				double depositAmount = Double.parseDouble(depositAmountStr);
				return account.isInMaxWithdrawLimit(depositAmount);
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return false;
	}

	private boolean isIncorrectPassTime(String uniqueId) {
		return true;
	}

	private int getAccountId(String uniqueId) {
		return Integer.parseInt(uniqueId);
	}
}

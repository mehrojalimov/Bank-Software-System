package banking;

public class DepositCommandValidator extends CommandValidator {

	public DepositCommandValidator(Bank bank) {
		super(bank);
	}

	public boolean validateCommand(String[] parts) {
		return parts.length == 3 && bank.accountExistsByUniqueID(getAccountId(parts[1]))
				&& isInDepositLimit(parts[1], parts[2]);
	}

	private int getAccountId(String uniqueId) {
		return Integer.parseInt(uniqueId);
	}

	private boolean isInDepositLimit(String uniqueId, String depositAmountStr) {
		try {
			int accountId = getAccountId(uniqueId);
			Account account = bank.retrieveAccount(accountId);

			if (account != null) {
				double depositAmount = Double.parseDouble(depositAmountStr);

				return account.isInMaxDepositLimit(depositAmount);
			}
		} catch (NumberFormatException e) {
			return false;
		}

		return false;
	}

}
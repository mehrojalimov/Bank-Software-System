package banking;

public class DepositCommandValidator extends CommandValidationProcessor {

	public DepositCommandValidator(Bank bank) {
		super(bank);
	}

	@Override
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
				String accountType = account.getAccountType().toLowerCase();
				double depositAmount = Double.parseDouble(depositAmountStr);

				double depositLimit = getDepositLimitForAccountType(accountType);

				return depositAmount <= depositLimit && depositAmount >= 0;
			}
		} catch (NumberFormatException e) {
			return false;
		}

		return false;
	}

	private double getDepositLimitForAccountType(String accountType) {
		switch (accountType) {
		case "saving":
			return 2500;
		case "checking":
			return 1000;
		default:
			return -1;
		}
	}
}
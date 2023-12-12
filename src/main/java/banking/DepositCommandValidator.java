package banking;

public class DepositCommandValidator extends CommandValidator {

	public DepositCommandValidator(Bank bank) {
		super(bank);
	}

	public boolean validateCommand(String[] parts) {
		try {
			return parts.length == 3 && bank.accountExistsByUniqueID(Integer.parseInt(parts[1]))
					&& isInDepositLimit(parts[1], parts[2]);
		} catch (Exception e) {
			return false;
		}
	}

	private boolean isInDepositLimit(String uniqueId, String depositAmountStr) {
		try {
			int accountId = Integer.parseInt(uniqueId);
			Account account = bank.retrieveAccount(accountId);

			if (account != null) {
				double depositAmount = Double.parseDouble(depositAmountStr);

				return account.isInMaxDepositLimit(depositAmount);
			}
		} catch (Exception e) {
			return false;
		}

		return false;
	}

}
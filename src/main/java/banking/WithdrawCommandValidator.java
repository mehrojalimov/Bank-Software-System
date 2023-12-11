package banking;

public class WithdrawCommandValidator extends CommandValidator {

	public WithdrawCommandValidator(Bank bank) {
		super(bank);
	}

	// withdraw 11111111 200

	public boolean validateCommand(String[] parts) {
		try {
			if (parts.length != 3) {
				return false;
			}

			int account = Integer.parseInt(parts[1]);
			double amount = Double.parseDouble(parts[2]);

			return isInValidMaximumRange(account, amount) && isTheCorrectAccount(account)
					&& isIncorrectPassTime(account);
		} catch (Exception e) {
			return false;
		}
	}

	private boolean isTheCorrectAccount(int uniqueId) {
		return (uniqueId <= 99999999 && uniqueId >= 10000000) && bank.accountExistsByUniqueID(uniqueId);
	}

	private boolean isInValidMaximumRange(int uniqueId, double amount) {
		return bank.retrieveAccount(uniqueId).isInMaxWithdrawLimit(amount);
	}

	private boolean isIncorrectPassTime(int uniqueId) {
		return true;
	}
}

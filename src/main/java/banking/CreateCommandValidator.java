package banking;

public class CreateCommandValidator extends CommandValidator {

	public CreateCommandValidator(Bank bank) {
		super(bank);
	}

	public boolean validateCommand(String[] parts) {
		return parts.length >= 4 && !doesAccountExist(parts[2]) && hasValidFormat(parts);
	}

	private boolean hasValidFormat(String[] parts) {
		String accountType = parts[1].toLowerCase();

		return hasValidUniquesIdAndApr(parts[2], parts[3])
				&& (("saving".equals(accountType) || "checking".equals(accountType)) && parts.length == 4
						|| ("cd".equals(accountType) && parts.length == 5 && isInAmountRange(parts[4])));
	}

	private boolean hasValidUniquesIdAndApr(String uniqueId, String apr) {
		try {
			int accountId = Integer.parseInt(uniqueId);
			boolean validUniqueId = String.valueOf(accountId).matches("^\\d{8}$");

			double aprValue = Double.parseDouble(apr);
			boolean validApr = aprValue >= 0 && aprValue <= 10;

			return validUniqueId && validApr;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean isInAmountRange(String amount) {
		try {
			double CDAmount = Double.parseDouble(amount);
			return CDAmount >= 1000 && CDAmount <= 10000;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean doesAccountExist(String uniqueId) {
		try {
			int accountId = getAccountId(uniqueId);
			return bank.accountExistsByUniqueID(accountId);
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private int getAccountId(String uniqueId) {
		return Integer.parseInt(uniqueId);
	}
}
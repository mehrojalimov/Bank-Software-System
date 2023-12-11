package banking;

public class TransferCommandValidator extends CommandValidator {
	public TransferCommandValidator(Bank bank) {
		super(bank);
	}
	// transfer 11111111 22222222 1000

	public boolean validateCommand(String[] parts) {
		try {
			if (parts.length != 4) {
				return false;
			}

			int accountOne = Integer.parseInt(parts[1]);
			int accountTwo = Integer.parseInt(parts[2]);
			double amount = Double.parseDouble(parts[3]);

			return usesTransferOperation(accountOne, accountTwo) && areValidAccounts(accountOne, accountTwo)
					&& isInTransferLimit(accountOne, accountTwo, amount);
		} catch (Exception e) {
			return false;
		}
	}

	private boolean usesTransferOperation(int accountOne, int accountTwo) {
		return bank.retrieveAccount(accountOne).acceptsTransfer() && bank.retrieveAccount(accountTwo).acceptsTransfer();
	}

	private boolean areValidAccounts(int account1, int account2) {
		return bank.accountExistsByUniqueID(account1) && bank.accountExistsByUniqueID(account2)
				&& (account1 != account2);
	}

	private boolean isInTransferLimit(int account1, int account2, double amount) {
		return bank.retrieveAccount(account1).isInMaxWithdrawLimit(amount)
				&& bank.retrieveAccount(account2).isInMaxDepositLimit(amount);
	}
}

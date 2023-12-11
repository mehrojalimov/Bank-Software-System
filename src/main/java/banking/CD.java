package banking;

public class CD extends Account {

	public CD(String accountType, int uniqueId, double apr, double balance) {
		super(accountType, uniqueId, apr, balance);
	}

	@Override
	public boolean isInMaxWithdrawLimit(double amount) {
		if (getPassTime() >= 12) {
			return amount >= getBalance();
		} else {
			return false;
		}
	}

	@Override
	public boolean isInMaxDepositLimit(double amount) {
		return false;
	}

	@Override
	public boolean acceptsTransfer() {
		return false;
	}

	@Override
	public double getTransferAmount(double amount) {
		return 0;
	}

	@Override
	public void calculateApr(int months) {
		double decimalAprPerMonth = getAPR() / 100 / 12;
		for (int z = 0; z < months; z++) {
			for (int i = 0; i < 4; i++) {
				double newBalance = (getBalance() * decimalAprPerMonth) + getBalance();
				setBalance(newBalance);
			}
		}
	}
}

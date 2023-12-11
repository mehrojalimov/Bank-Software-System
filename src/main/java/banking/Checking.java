package banking;

public class Checking extends Account {

	public Checking(String accountType, int uniqueId, double apr) {
		super(accountType, uniqueId, apr);
	}

	@Override
	public boolean isInMaxWithdrawLimit(double amount) {
		return amount <= 400 && amount >= 0;
	}

	@Override
	public boolean isInMaxDepositLimit(double amount) {
		return amount <= 1000 && amount >= 0;
	}

	@Override
	public boolean acceptsTransfer() {
		return true;
	}

}

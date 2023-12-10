package banking;

public class Saving extends Account {

	public Saving(String accountType, int uniqueId, double apr) {
		super(accountType, uniqueId, apr);
	}

	@Override
	public boolean isInMaxWithdrawLimit(double amount) {
		return amount <= 400 && amount >= 0;
	}

	@Override
	public boolean isInMaxDepositLimit(double amount) {
		return amount <= 2500 && amount >= 0;
	}
}

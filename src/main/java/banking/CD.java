package banking;

public class CD extends Account {

	public CD(String accountType, int uniqueId, double apr, double balance) {
		super(accountType, uniqueId, apr, balance);
	}

	@Override
	public boolean isInMaxWithdrawLimit(double amount) {
		return amount >= getBalance();
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
}

package banking;

public class Saving extends Account {

	private int LAST_WITHDRAW_MONTH = -1;

	public Saving(String accountType, int uniqueId, double apr) {
		super(accountType, uniqueId, apr);
	}

	int getLastWithdrawMonth() {
		return LAST_WITHDRAW_MONTH;
	}

	void setLastMonthWithdrawMonth(int newMonth) {
		LAST_WITHDRAW_MONTH = newMonth;
	}

	@Override
	public boolean isInMaxWithdrawLimit(double amount) {
		if (getPassTime() > getLastWithdrawMonth()) {
			return amount <= 1000 && amount >= 0;
		} else {
			return false;
		}
	}

	@Override
	public boolean isInMaxDepositLimit(double amount) {
		return amount <= 2500 && amount >= 0;
	}

	@Override
	public boolean acceptsTransfer() {
		return true;
	}

	@Override
	public double getTransferAmount(double amount) {
		return Math.min(amount, getBalance());
	}
}

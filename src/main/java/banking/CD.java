package banking;

public class CD extends Account {

	public CD(String accountType, int uniqueId, double apr, double balance) {
		super(accountType, uniqueId, apr, balance);
	}

	@Override
	public boolean isInValidMaximumRange(int amount) {
		return amount >= getBalance();
	}
}

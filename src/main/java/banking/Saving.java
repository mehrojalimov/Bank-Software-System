package banking;

public class Saving extends Account {

	public Saving(String accountType, int uniqueId, double apr) {
		super(accountType, uniqueId, apr);
	}

	@Override
	public boolean isInValidMaximumRange(int amount) {
		return amount <= 400 && amount >= 0;
	}
}

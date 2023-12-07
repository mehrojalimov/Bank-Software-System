package banking;

public class Checking extends Account {

	public Checking(String accountType, int uniqueId, double apr) {
		super(accountType, uniqueId, apr);
	}

	@Override
	public boolean isInValidMaximumRange(int amount) {
		return amount <= 1000 && amount >= 0;
	}

}

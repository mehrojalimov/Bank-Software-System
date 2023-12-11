package banking;

public class PassTimeValidator extends CommandValidator {

	public PassTimeValidator(Bank bank) {
		super(bank);
	}

	// Pass 1
	public boolean validateCommand(String[] parts) {
		try {
			if (parts.length != 2) {
				return false;
			}
			int months = Integer.parseInt(parts[1]);
			return isInCorrectInteraction(months);
		} catch (Exception e) {
			return false;
		}
	}

	private boolean isInCorrectInteraction(int months) {
		return (months >= 1 && months <= 60);
	}
}

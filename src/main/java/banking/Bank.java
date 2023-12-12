package banking;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Bank {
	private static Map<Integer, Account> accounts;

	Bank() {

		accounts = new HashMap<>();

	}

	public static Map<Integer, Account> getAccounts() {
		return accounts;
	}

	public void addCheckingAccount(int uniqueID, double apr) {
		accounts.put(uniqueID, new Checking("checking", uniqueID, apr));

	}

	public void addSavingAccount(int uniqueID, double apr) {
		accounts.put(uniqueID, new Saving("saving", uniqueID, apr));
	}

	public void addCDAccount(int uniqueID, double apr, double initialBalance) {
		accounts.put(uniqueID, new CD("cd", uniqueID, apr, initialBalance));
	}

	public Account retrieveAccount(int uniqueId) {
		return accounts.get(uniqueId);
	}

	public void deposit(int uniqueId, double money) {
		retrieveAccount(uniqueId).deposit(money);
	}

	public void withdraw(int uniqueId, double money) {
		Account account = retrieveAccount(uniqueId);
		account.withdraw(money);
		account.setLastMonthWithdrawMonth(account.getPassTime());
	}

	public boolean accountExistsByUniqueID(int quickId) {
		return accounts.get(quickId) != null;
	}

	public void passTime(int months) {
		Iterator<Map.Entry<Integer, Account>> iterator = accounts.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Integer, Account> entry = iterator.next();
			Account account = entry.getValue();

			if (account.getBalance() == 0) {
				iterator.remove();
				continue;
			}

			if (account.getBalance() < 100) {
				account.withdraw(25);
			}

			account.calculateApr(months);
			account.setPassTime(months);
		}
	}

	public void addActivity(String id, String command) {
		int uniqueId = Integer.parseInt(id);
		String proper = command.substring(0, 1).toUpperCase() + command.substring(1).toLowerCase();
		retrieveAccount(uniqueId).addValidCommand(proper);
	}
}

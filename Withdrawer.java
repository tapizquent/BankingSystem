import java.util.Random;

public class Withdrawer implements Runnable {
    public Withdrawer(AccountManager accountManager, int threadNumber) {
        this.accountManager = accountManager;
        this.name = "With" + (threadNumber + 1);
    }

    private static Random randomNumberGenerator = new Random();
    private AccountManager accountManager;
    private String name;

    @Override
    public void run() {
        while (true) {
            makeWithdrawal();
        }

    }

    public void makeWithdrawal() {
        int amountToWithdraw = randomNumberGenerator.nextInt(50) + 1;
        int withdrew = accountManager.withdraw(amountToWithdraw);

        if (withdrew > 0) {
            printWithdrawalAndCurrentBalance(amountToWithdraw);
        } else {
            printNotEnoughFunds(amountToWithdraw);
        }

        try {
            Thread.sleep(randomNumberGenerator.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    private void printWithdrawalAndCurrentBalance(int withdrawalAmount) {
        System.out.print("\t\t\t\tThread " + name + " withdrew $" + withdrawalAmount + "\t [-] Current balance: "
                + accountManager.getCurrentBalance() + "\n");
    }

    private void printNotEnoughFunds(int withdrawalAmount) {
        System.out.print("\t\t\t\tThread " + name + " withdrew $" + withdrawalAmount
                + "\t [Δ] WITHDRAWAL BLOCKED (Not enough funds). Current balance: " + accountManager.getCurrentBalance()
                + "\n");
    }
}

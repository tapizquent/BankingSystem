import java.util.Random;

public class Depositor implements Runnable {

    public Depositor(AccountManager accountManager, int threadNumber) {
        this.accountManager = accountManager;
        this.name = "Dep" + (threadNumber + 1);
    }

    private static Random randomNumberGenerator = new Random();
    private AccountManager accountManager;
    private String name;

    @Override
    public void run() {
        while (true) {
            makeDeposit();
        }
    }

    public void makeDeposit() {
        int amountToDeposit = randomNumberGenerator.nextInt(250) + 1;

        int currentBalance = accountManager.deposit(amountToDeposit);

        printDepositAndCurrentBalance(amountToDeposit, currentBalance);

        try {
            Thread.sleep(randomNumberGenerator.nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    private void printDepositAndCurrentBalance(int depositAmount, int currentBalance) {
        System.out.print("Thread " + name + " deposited $" + depositAmount + "\t\t\t\t\t [+] Current balance: "
                + currentBalance + "\n");
    }
}

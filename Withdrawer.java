
/** 
 * Name: Jose Tapizquent
 * Course: CNT 4714 Spring 2021
 * Assignment Title: Project 2 - Synchronized, Cooperating Threads Under Locking
 * Due Date: February 14, 2021
 * 
 * Withdrawer.java
 */

import java.util.Random;

public class Withdrawer implements Runnable {

    /**
     * An actor that performs withdrawals with the help of the [accountManager]
     * 
     * @param accountManager The account manager that allows access to the account
     * @param threadNumber   The number of the current thread. Only for displaying
     *                       and identification purposes
     */
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

    /**
     * Performs a withdrawal of a random amount between $1-50 and prints the
     * transaction along with the account's current balance
     */
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

    /**
     * Prints transaction entry along with account's currentBalance
     * 
     * @param withdrawalAmount
     */
    private void printWithdrawalAndCurrentBalance(int withdrawalAmount) {
        System.out.print("\t\t\t\tThread " + name + " withdrew $" + withdrawalAmount + "\t [-] Current balance: "
                + accountManager.getCurrentBalance() + "\n");
    }

    /**
     * Prints not enough funds entry along with account's currentBalance
     * 
     * @param withdrawalAmount The amount trying to be withdrawn
     */
    private void printNotEnoughFunds(int withdrawalAmount) {
        System.out.print("\t\t\t\tThread " + name + " withdrew $" + withdrawalAmount
                + "\t [Δ] WITHDRAWAL BLOCKED (Not enough funds). Current balance: " + accountManager.getCurrentBalance()
                + "\n");
    }
}

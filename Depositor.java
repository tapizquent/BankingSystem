
/** 
 * Name: Jose Tapizquent
 * Course: CNT 4714 Spring 2021
 * Assignment Title: Project 2 - Synchronized, Cooperating Threads Under Locking
 * Due Date: February 14, 2021
 * 
 * Depositor.java
 */

import java.util.Random;

public class Depositor implements Runnable {

    /**
     * An actor that performs deposits with the help of the [accountManager]
     * 
     * @param accountManager The account manager that allows access to the account
     * @param threadNumber   The number of the current thread. Only for displaying
     *                       and identification purposes
     */
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

    /**
     * Performs a deposit of a random amount between $1-250 and prints the
     * transaction along with the account's current balance
     */
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

    /**
     * Prints transaction entry along with account's currentBalance
     * 
     * @param depositAmount  The amount deposited
     * @param currentBalance The currentBalance of the account returned by deposit
     *                       function
     */
    private void printDepositAndCurrentBalance(int depositAmount, int currentBalance) {
        System.out.print("Thread " + name + " deposited $" + depositAmount + "\t\t\t\t\t [+] Current balance: "
                + currentBalance + "\n");
    }
}

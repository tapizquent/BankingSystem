
/** 
 * Name: Jose Tapizquent
 * Course: CNT 4714 Spring 2021
 * Assignment Title: Project 2 - Synchronized, Cooperating Threads Under Locking
 * Due Date: February 14, 2021
 * 
 * AccountManager.java
 */

import java.util.concurrent.locks.*;

public class AccountManager {
    /**
     * Manages the account by allowing only one thread at a time to access the
     * [currentBalance] in order to keep synchronization among all threads.
     * 
     * Initializes the account with a balance of 0.
     */
    public AccountManager() {
        this(0);
    }

    /**
     * Manages the account by allowing only one thread at a time to access the
     * [currentBalance] in order to keep synchronization among all threads.
     * 
     * @param initialBalance The initial balance on the account
     */
    public AccountManager(int initialBalance) {
        currentBalance = 0;
    }

    private int currentBalance;
    private Lock accessLock = new ReentrantLock();
    private Condition canWithdraw = accessLock.newCondition();
    private Condition canDeposit = accessLock.newCondition();
    private boolean isBusy = false;

    /**
     * Performs deposit if account is not currently busy being accessed by another
     * resource.
     * 
     * Returns the currentBalance to depositor only for displaying purposes.
     *
     * 
     * @param depositAmount The amount to be deposited into the account
     * @return currentBalance of the account after deposit
     */
    public int deposit(int depositAmount) {
        accessLock.lock();

        /**
         * If this thread gets the accessLock, it checks whether the account is
         * currently being accessed by another thread and if it is, waits for a
         * [canDeposit] signal in order to proceed and make a deposit.
         */
        while (isBusy) {
            try {
                System.out.println("Manager occupied. Deposit Waiting...");
                canDeposit.await();
                isBusy = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * Deposit into [currentBalance].
         */
        currentBalance += depositAmount;

        /**
         * Signal other threads that this one is done doing deposits and unlock
         * accessLock.
         */
        canWithdraw.signalAll();
        canDeposit.signalAll();
        accessLock.unlock();

        /**
         * Return currentBalance to depositor for displaying purposes.
         */
        return currentBalance;
    }

    /**
     * Makes a withdrawal is the service is currently not being accessed by another
     * resource.
     *
     * Returns the withdrew amount if there are enough funds in the account. Returns
     * 0 if there are not enough funds to return the withdrawAmount.
     * 
     * @param withdrawAmount The amount to be withdrew from the account balance
     * @return The withdrew amount if there are enough funds or 0 otherwise.
     */
    public int withdraw(int withdrawAmount) {
        accessLock.lock();

        /**
         * If this thread gets the accessLock, it checks whether the account is
         * currently being accessed by another thread and if it is, waits for a
         * [canWithdraw] signal in order to proceed and make a withdrawal.
         */
        while (isBusy) {
            try {
                System.out.println("Manager occupied. Withdrawal Waiting...");
                canWithdraw.await();
                isBusy = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * If the [currentBalance] is less than the [withdrawAmount], it returns 0 and
         * signals other resources that it is done as there are not enough funds to
         * withdraw.
         */
        if (currentBalance - withdrawAmount < 0) {
            canWithdraw.signalAll();
            canDeposit.signalAll();
            accessLock.unlock();
            return 0;
        }

        /**
         * Withdraw from [currentBalance].
         */
        currentBalance -= withdrawAmount;

        /**
         * Signal other threads that this one is done doing deposits and unlock
         * accessLock.
         */
        canWithdraw.signalAll();
        canDeposit.signalAll();
        accessLock.unlock();

        return withdrawAmount;
    }

    public int getCurrentBalance() {
        return currentBalance;
    }
}

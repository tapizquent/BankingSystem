import java.util.concurrent.locks.*;

public class AccountManager {
    public AccountManager() {
        this(0);
    }

    public AccountManager(int initialBalance) {
        currentBalance = 0;
    }

    private int currentBalance;
    private Lock accessLock = new ReentrantLock();
    private Condition canWithdraw = accessLock.newCondition();
    private Condition canDeposit = accessLock.newCondition();
    private boolean isBusy = false;

    // Returns current balance
    public int deposit(int depositAmount) {
        accessLock.lock();

        while (isBusy) {
            try {
                System.out.println("Manager occupied. Deposit Waiting...");
                canDeposit.await();

                isBusy = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        isBusy = true;

        currentBalance += depositAmount;

        isBusy = false;

        canWithdraw.signalAll();
        canDeposit.signalAll();
        accessLock.unlock();

        return currentBalance;
    }

    public int withdraw(int withdrawAmount) {
        accessLock.lock();

        while (isBusy) {
            try {
                System.out.println("Manager occupied. Withdrawal Waiting...");
                canWithdraw.await();

                isBusy = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (currentBalance - withdrawAmount < 0) {
            canWithdraw.signalAll();
            canDeposit.signalAll();
            accessLock.unlock();
            return 0;
        }

        currentBalance -= withdrawAmount;

        canWithdraw.signalAll();
        canDeposit.signalAll();
        accessLock.unlock();

        return withdrawAmount;
    }

    public int getCurrentBalance() {
        return currentBalance;
    }
}

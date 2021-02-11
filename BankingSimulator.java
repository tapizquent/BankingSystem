
/**
 * Must have FIVE depositors and NINE withdrawers at the same time executing.
 * 
 * DEPOSITS: $1-250 whole dollars. Once a depositor has executed, put it to
 * sleep for a few random milliseconds.
 * 
 * WITHDRAWALS: $1-50 whole dollars.
 * 
 * For single core cpus: once a thread has executed, have it yield to another
 * thread. (Since it's voluntarily giving up the processor, it's unlikely it'll
 * run again before another thread has executed.)
 * 
 */

import java.util.concurrent.*;

public class BankingSimulator {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(14);
        AccountManager accountManager = new AccountManager();

        printHeader();

        try {
            for (int i = 0; i < 5; i++) {
                executorService.execute(new Depositor(accountManager, i));
            }

            for (int i = 0; i < 9; i++) {
                executorService.execute(new Withdrawer(accountManager, i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

    static private void printHeader() {
        System.out.println("DEPOSITS\t\t\tWITHDRAWALS\t\t\t\tBALANCE");
        System.out
                .println("-----------------------------\t-----------------------------\t-----------------------------");
    }
}
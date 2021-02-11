
/** 
 * Name: Jose Tapizquent
 * Course: CNT 4714 Spring 2021
 * Assignment Title: Project 2 - Synchronized, Cooperating Threads Under Locking
 * Due Date: February 14, 2021
 * 
 * BankingSimulator.java
 */

import java.util.concurrent.*;

/**
 * Multithreaded banking simulator composed of 5 Depositors and 9 Withdrewers
 * working at the same time.
 * 
 * Each of the actors involved perform in a different thread but are
 * synchronized to work on the same account.
 * 
 */
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
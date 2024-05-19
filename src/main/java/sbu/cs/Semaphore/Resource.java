package sbu.cs.Semaphore;

import java.util.concurrent.Semaphore;
public class Resource {
    private static Semaphore semaphore = new Semaphore(2);
    public static void accessResource() {
        try {
            System.out.println("Requesting in: " + Thread.currentThread().getName());
            semaphore.acquire();
            System.out.println("Running in: " + Thread.currentThread().getName());
            Thread.sleep(500);
        }

        catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Finished: " + Thread.currentThread().getName());

        semaphore.release();
    }
}

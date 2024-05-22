package sbu.cs.Semaphore;
public class Operator extends Thread {
    public Operator(String name) {
        super(name);
    }
    @Override
    public void run() {
        for (int i = 0; i < 10; i++)
        {
            Resource.accessResource();

            System.out.println("Finished " + i + " loop (" + Thread.currentThread().getName() + ")");

            try {
                sleep(500);
            }

            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

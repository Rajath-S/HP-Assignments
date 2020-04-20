public class Ex4Interrupt implements Runnable {
    public void run() {
        try {
            Thread.sleep(Long.MAX_VALUE); // infinite sleep
        } catch (InterruptedException e) {
            System.out.println("[" + Thread.currentThread().getName() + "] Interrupted by exception!");
        }
        while (!Thread.interrupted()) {
            // nothing
        }
        System.out.println("[" + Thread.currentThread().getName() + "] Interrupted for the second time.");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread myThread = new Thread(new Ex4Interrupt(), "myThread");
    myThread.start();
    System.out.println("["+Thread.currentThread().getName()+"] Sleeping in main thread for 5s");
    Thread.sleep(5000);
    System.out.println("["+Thread.currentThread().getName()+"] Interrupting myThread");
    myThread.interrupt();
    System.out.println("["+Thread.currentThread().getName()+"] Sleeping in main thread for 5s");
    Thread.sleep(5000);
    System.out.println("["+Thread.currentThread().getName()+"] Interrupting myThread");
    myThread.interrupt();
    }
}
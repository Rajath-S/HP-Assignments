

    public class Ex3 implements Runnable {
        public void run() {
        System.out.println("Executing thread "+Thread.currentThread().getName());
        }
        public static void main(String[] args) throws InterruptedException {
        Thread myrun = new Thread(new Ex3(), "myrun");
        myrun.start(); //calls run
        }
    }


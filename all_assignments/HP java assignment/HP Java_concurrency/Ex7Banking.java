import java.util.Random; 

public class Ex6_Banking{
    private static int balance = 0;
    public static class Withdrawer implements Runnable {
        public void run() {
            final Random rand = new Random();
            synchronized (Ex6_Banking.class) {
                final int withdrawal_amount = rand.nextInt(100);// 0 to 100
                if (balance - withdrawal_amount < 0) {
                    System.out.println("Cannot withdraw.. insufficient funds");
                } else {
                    balance -= withdrawal_amount;
                    System.out
                            .println("[" + Thread.currentThread().getName() + "] is withdrawing " + withdrawal_amount);
                    try {
                        Thread.sleep(5000);// processing time
                    } catch (final Exception e) {
                        System.out.println(e);
                    }
                    System.out.println("Account balance is: " + balance);
                }
            }

        }
    }

    public static class Depositor implements Runnable {
        Random rand = new Random();

        public void run() {
            synchronized (Ex6_Banking.class) {
                final int deposited = rand.nextInt(100);
                balance += deposited;
                System.out.println("[" + Thread.currentThread().getName() + "] is depositing " + deposited);
                try {
                    Thread.sleep(5000);// processing time
                } catch (final Exception e) {
                    System.out.println("not sleeping");
                } // processing
                System.out.println("Account balance is: " + balance);
            }
        }
    }

    public static void main(final String[] args) throws InterruptedException {

        final Thread[] withdrawers = new Thread[5];
        final Thread[] depositors = new Thread[6];
        for(int i = 0; i < withdrawers.length; i++){
            withdrawers[i] = new Thread(new Withdrawer(), "Withdraw-"+i);
        }
        for(int i = 0; i < depositors.length; i++){
            depositors[i] = new Thread(new Depositor(), "Depositors-"+i);
        }
        for(int i = 0; i < withdrawers.length; i++){
            depositors[i].join();
        }
        for(int i = 0; i < depositors.length; i++){
            depositors[i].join();
        }
        
        depositors[1].start();
        depositors[5].start();
        withdrawers[4].start();
        depositors[0].start();
        withdrawers[2].start();
        depositors[4].start();
        withdrawers[1].start();
        depositors[2].start();
        withdrawers[0].start();
        depositors[3].start();
        withdrawers[3].start();
        withdrawers[4].start();
        
    }
}

    /* Sample Output

    Account balance is: 1000
    Account balance is: 2000
    Account balance is: 1000
    Account balance is: 0
    Account balance is: 1000
    Account balance is: 0
    Account balance is: 1000
    Account balance is: 0
    Account balance is: 1000
    Account balance is: 0
    Account balance is: 1000

    */

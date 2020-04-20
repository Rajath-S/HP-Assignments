//exercise 2
public class Ex2 extends Thread {
    public Ex2(String name) {
    super(name);
    }
    @Override
    public void run() {
    System.out.println("Executing thread "+Thread.currentThread().getName());
    }
    public static void main(String[] args) throws InterruptedException {
        Ex2 my1 = new Ex2("my1");
    my1.start();
    Ex2 my2 = new Ex2("my2");
    my2.start();
    Ex2 my3 = new Ex2("my3");
    my3.start();
    Ex2 my4 = new Ex2("my4");
    my4.start();
    }
}
package wait_and_sleep;

//在 Java 中，线程的 sleep 状态和 wait 状态都是线程的一种状态，它们之间的主要区别在于：
//
//sleep 方法是使当前线程休眠一段时间，不释放锁，到时间后自动唤醒，但不保证具体的休眠时间；
//wait 方法是使当前线程等待，直到其他线程调用该对象的 notify() 或 notifyAll() 方法唤醒当前线程，同时会释放对象的锁，等待其他线程的唤醒。
//在这个例子中，我们使用 synchronized 来同步两个线程对 lock 对象的访问。线程1在获取到锁之后休眠了2秒钟，然后结束休眠；线程2在获取到锁之后调用了 wait() 方法等待，直到线程1结束休眠后，调用 lock.notify() 方法唤醒线程2。
//
//可以看到，线程1休眠期间并没有释放锁，而线程2在调用 wait() 方法后释放了锁。这是两者的主要区别。
public class WaitSleep {
    public static void main(String[] args) {
        final Object lock = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("线程1获取到了锁");
                try {
                    Thread.sleep(2000);
                    System.out.println("线程1结束休眠");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("线程2获取到了锁");
                try {
                    lock.wait();
                    System.out.println("线程2被唤醒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t3 = new Thread(() -> {
            synchronized (lock) {
                lock.notify();
                System.out.println("线程3获取到了锁");
                try {
                    lock.wait();
                    System.out.println("线程3被唤醒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();

    }
}

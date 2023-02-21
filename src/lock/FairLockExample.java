package lock;

import java.util.concurrent.locks.ReentrantLock;

public class FairLockExample {
    private static final int THREAD_COUNT = 5;
    private static ReentrantLock fairLock = new ReentrantLock(true);

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(new Worker()).start();
        }
    }

    static class Worker implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " is trying to acquire lock.");
            fairLock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " has acquired lock.");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                fairLock.unlock();
                System.out.println(Thread.currentThread().getName() + " has released lock.");
            }
        }
    }
}

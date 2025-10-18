package counters;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCounter implements Counter{
    private int count;
    private final ReentrantLock lock = new ReentrantLock();

    public ReentrantLockCounter() {
        this.count = 0;
    }

    public ReentrantLockCounter(int initialValue) {
        this.count = initialValue;
    }

    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void reset() {
        lock.lock();
        try {
            count = 0;
        } finally {
            lock.unlock();
        }
    }
}

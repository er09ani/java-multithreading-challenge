package counters;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockCounter implements Counter {
    private int count;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public ReadWriteLockCounter() {
        this.count = 0;
    }

    public ReadWriteLockCounter(int initialValue) {
        this.count = initialValue;
    }

    public void increment() {
        lock.writeLock().lock();
        try {
            count++;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int getCount() {
        lock.readLock().lock();
        try {
            return count;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void reset() {
        lock.writeLock().lock();
        try {
            count = 0;
        } finally {
            lock.writeLock().unlock();
        }
    }
}

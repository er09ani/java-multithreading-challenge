package counters;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter implements Counter{
    private final AtomicInteger count;

    public AtomicCounter() {
        this.count = new AtomicInteger(0);
    }

    public AtomicCounter(int initialValue) {
        this.count = new AtomicInteger(initialValue);
    }

    public void increment() {
        count.incrementAndGet();
    }

    public int getCount() {
        return count.get();
    }

    @Override
    public void reset() {
        count.set(0);
    }
}

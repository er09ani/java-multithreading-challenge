package counters;

public class SynchronizedCounter implements Counter{
    private int count;

    @Override
    public void increment() {
        synchronized (this) {
            count++;
        }
    }

    @Override
    public int getCount() {
        synchronized (this) {
            return count;
        }
    }

    @Override
    public void reset() {
        synchronized (this) {
            count = 0;
        }
    }
}

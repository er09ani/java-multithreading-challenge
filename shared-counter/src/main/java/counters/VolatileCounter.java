package counters;

public class VolatileCounter implements Counter{
    private volatile int count;

    public VolatileCounter() {
        this.count = 0;
    }

    public VolatileCounter(int initialValue) {
        this.count = initialValue;
    }

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }

    @Override
    public void reset() {
        count = 0;
    }
}

import counters.Counter;

import java.util.Queue;

public class WorkerThread extends Thread {
    private final int workLoad;
    private final Counter counter;
    private final Queue<Double> executionTimes;

    public WorkerThread(Counter counter, Queue<Double> executionTimes, int workLoad) {
        this.counter = counter;
        this.executionTimes = executionTimes;
        this.workLoad = workLoad;
    }

    @Override
    public void run() {
        long start = System.nanoTime();
        for (int i = 0; i < workLoad; i++) {
            counter.increment();
        }
        long end = System.nanoTime();

        double durationMs = (end - start) / 1_000_000.0;
        executionTimes.add(durationMs);
    }
}

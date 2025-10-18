import counters.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PerformanceTest {

    private static final int FIX_THREADS_COUNT = 200;
    private static final int WORKLOAD = 100_000;

    @Test
    public void test_SynchronizedCounter() {
        runBenchmark(new SynchronizedCounter());
    }

    @Test
    public void test_AtomicCounter() {
        runBenchmark(new AtomicCounter());
    }

    @Test
    public void test_ReadWriteLockCounter() {
        runBenchmark(new ReadWriteLockCounter());
    }

    @Test
    public void test_ReentrantLockCounter() {
        runBenchmark(new ReentrantLockCounter());
    }

    private void runBenchmark(Counter counter) {
        warmUp(counter);
        counter.reset();

        Queue<Double> executionTimes = new ConcurrentLinkedQueue<>();
        List<WorkerThread> workers = new ArrayList<>();

        for (int i = 0; i < FIX_THREADS_COUNT; i++) {
            workers.add(new WorkerThread(counter, executionTimes, WORKLOAD));
        }

        long globalStart = System.nanoTime();
        workers.forEach(Thread::start);
        workers.forEach(w -> {
            try { w.join(); } catch (InterruptedException ignored) {}
        });
        long globalEnd = System.nanoTime();

        DoubleSummaryStatistics stats = executionTimes.stream()
                .mapToDouble(Double::doubleValue)
                .summaryStatistics();

        System.out.printf("%-25s | Min: %8.3f ms | Max: %8.3f ms | Avg: %8.3f ms | Total: %8.3f ms%n",
                counter.getClass().getSimpleName(),
                stats.getMin(), stats.getMax(), stats.getAverage(),
                (globalEnd - globalStart) / 1_000_000.0);

        assertThat("Counter value mismatch",
                counter.getCount(), equalTo(FIX_THREADS_COUNT * WORKLOAD));
    }

    private void warmUp(Counter counter) {
        for (int i = 0; i < 10_000; i++) {
            counter.increment();
        }
    }
}

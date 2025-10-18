package improved;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer extends Thread {
    private final BlockingQueue<Integer> queue;
    private final AtomicInteger counter;
    private volatile boolean running = true;

    public Producer(BlockingQueue<Integer> queue, AtomicInteger counter) {
        this.queue = queue;
        this.counter = counter;
    }

    @Override
    public void run() {
        int value = 0;
        try {
            while (running) {
                queue.put(value); // blocks automatically if queue is full
                System.out.println("Producer produced: " + value);
                counter.incrementAndGet();
                value++;
                Thread.sleep(500); // simulate work
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        running = false;
        this.interrupt(); // wake up if blocked on put()
    }
}

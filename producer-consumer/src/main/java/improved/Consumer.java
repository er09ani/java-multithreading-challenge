package improved;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer extends Thread {
    private final BlockingQueue<Integer> queue;
    private final AtomicInteger counter;
    private volatile boolean running = true;

    public Consumer(BlockingQueue<Integer> queue, AtomicInteger counter) {
        this.queue = queue;
        this.counter = counter;
    }

    @Override
    public void run() {
        try {
            while (running || !queue.isEmpty()) {
                Integer value = queue.take(); // blocks automatically if queue is empty
                counter.decrementAndGet();
                System.out.println("Consumer consumed: " + value);
                Thread.sleep(500); // simulate work
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        running = false;
        this.interrupt(); // wake up if blocked on take()
    }
}

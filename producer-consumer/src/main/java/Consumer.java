import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer extends Thread {
   private final Queue<Integer> queue;

   private volatile boolean running;
   private final AtomicInteger counter;

    public Consumer(Queue<Integer> queue, AtomicInteger counter) {
        this.queue = queue;
        this.counter = counter;
        this.running = true;
    }


    @Override
    public void run() {
        while (running || !queue.isEmpty()) {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
                int value = queue.poll();
                counter.decrementAndGet();
                System.out.println("Consumer consumed:" + value);
                queue.notifyAll();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignore) {

            }
        }

    }

    public void shutdown() {
        running = false;
        synchronized(queue) {
            queue.notifyAll();
        }
    }
}

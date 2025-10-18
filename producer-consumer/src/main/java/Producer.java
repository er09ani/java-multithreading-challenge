import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer extends Thread {
    private final Queue<Integer> queue;
    private final int BUFFER_SIZE = 25;
    private volatile boolean running;
    private AtomicInteger counter;

    public Producer(Queue<Integer> queue, AtomicInteger counter) {
        this.queue = queue;
        this.running = true;
        this.counter = counter;
    }

    @Override
    public void run() {
        int value = 0;
        while(running) {
            synchronized(queue) {
                while(queue.size() == BUFFER_SIZE) {
                    try {
                        queue.wait();
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }

                queue.offer(value);
                System.out.println("Producer produced:" + value);
                value++;
                counter.incrementAndGet();
                queue.notifyAll();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignore) {

            }
        }
    }

    public void shutdown()
    {
        running = false;
        synchronized(queue) {
            queue.notifyAll();
        }
    }
}

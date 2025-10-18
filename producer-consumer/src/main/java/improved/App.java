package improved;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class App {

    public static void main(String[] args) throws InterruptedException {
        int BUFFER_SIZE = 10;
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(BUFFER_SIZE);
        AtomicInteger counter = new AtomicInteger(0);

        List<Producer> producers = new ArrayList<>();
        List<Consumer> consumers = new ArrayList<>();

        int numberOfProducers = 10;
        int numberOfConsumers = 4;

        for (int i = 0; i < numberOfProducers; i++) {
            producers.add(new Producer(queue, counter));
        }

        for (int i = 0; i < numberOfConsumers; i++) {
            consumers.add(new Consumer(queue, counter));
        }

        producers.forEach(Thread::start);
        consumers.forEach(Thread::start);

        // Let the producers and consumers run for 5 seconds
        Thread.sleep(5000);

        // Shutdown all threads
        producers.forEach(Producer::shutdown);
        consumers.forEach(Consumer::shutdown);

        // Wait for all threads to finish
        producers.forEach(p -> {
            try {
                p.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        consumers.forEach(c -> {
            try {
                c.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        System.out.println("Final counter: " + counter.get());
    }
}

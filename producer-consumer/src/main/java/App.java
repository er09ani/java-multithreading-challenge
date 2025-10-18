import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class App {

    public static void main(String[] args) throws InterruptedException {
        Queue<Integer> queue = new LinkedList<>();
        List<Producer> producers = new ArrayList<>();
        List<Consumer> consumers = new ArrayList<>();

        AtomicInteger counter = new AtomicInteger(0);

        int numberOfProducers = 10;
        int numberOfConsumers = 5;

        for(int i = 0; i < numberOfProducers; i++) {
            producers.add(new Producer(queue, counter));
        }

        for(int i = 0; i < numberOfConsumers; i++) {
            consumers.add(new Consumer(queue, counter));
        }

        producers.forEach(Producer::start);
        consumers.forEach(Consumer::start);

        Thread.sleep(5000);

        producers.forEach(Producer::shutdown);
        consumers.forEach(Consumer::shutdown);

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

        System.out.println("Counter:" +  counter.get());

    }
}

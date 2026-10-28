import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerDemo {
    static class BoundedBuffer {
        private final Queue<Integer> queue = new LinkedList<>();
        private final int capacity;

        public BoundedBuffer(int capacity) {
            this.capacity = capacity;
        }

        public synchronized void produce(int value) throws InterruptedException {
            while (queue.size() == capacity) {
                wait();
            }
            queue.add(value);
            System.out.println("Produced: " + value + " | Buffer size: " + queue.size());
            notifyAll();
        }

        public synchronized int consume() throws InterruptedException {
            while (queue.isEmpty()) {
                wait();
            }
            int value = queue.remove();
            System.out.println("Consumed: " + value + " | Buffer size: " + queue.size());
            notifyAll();
            return value;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BoundedBuffer sharedBuffer = new BoundedBuffer(3);
        final int totalItems = 8;

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= totalItems; i++) {
                    sharedBuffer.produce(i * 15);
                    Thread.sleep(15);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");

        Thread consumer = new Thread(() -> {
            int count = 0;
            try {
                for (int i = 1; i <= totalItems; i++) {
                    sharedBuffer.consume();
                    count++;
                    Thread.sleep(25);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Consumer finished receiving " + count + "/" + totalItems + " items in order.");
        }, "Consumer");

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
}

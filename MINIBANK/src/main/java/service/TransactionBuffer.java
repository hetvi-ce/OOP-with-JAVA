package service;

/**
 * Shared bounded array buffer coordinating producer and consumer threads using wait() and notifyAll().
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 10
 */
public class TransactionBuffer {

    private final String[] buffer;
    private final int capacity;
    private int count = 0;
    private int in = 0;
    private int out = 0;

    public TransactionBuffer(int capacity) {
        this.capacity = capacity;
        this.buffer = new String[capacity];
    }

    public synchronized void produce(String item) throws InterruptedException {
        while (count == capacity) {
            // Buffer is full: producer waits for consumer to remove an item
            wait();
        }
        buffer[in] = item;
        in = (in + 1) % capacity;
        count++;
        // Notify waiting consumers that data is available
        notifyAll();
    }

    public synchronized String consume() throws InterruptedException {
        while (count == 0) {
            // Buffer is empty: consumer waits for producer to add an item
            wait();
        }
        String item = buffer[out];
        out = (out + 1) % capacity;
        count--;
        // Notify waiting producers that buffer slot is freed
        notifyAll();
        return item;
    }

    public synchronized int getCount() {
        return count;
    }
}

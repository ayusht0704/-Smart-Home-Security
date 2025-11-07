class SharedBuffer {
    private int value;
    private boolean hasValue = false;

    public synchronized void produce(int val) {
        while (hasValue) { 
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        value = val;
        hasValue = true;
        System.out.println("Produced: " + value);
        notify();
    }

    public synchronized void consume() {
        while (!hasValue) { // wait until produced
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        System.out.println("Consumed: " + value);
        hasValue = false;
        notify();
    }
}

class Producer extends Thread {
    private SharedBuffer buffer;

    Producer(SharedBuffer b) {
        this.buffer = b;
    }

    public void run() {
        for (int i = 1; i <= 10; i++) {
            buffer.produce(i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

class Consumer extends Thread {
    private SharedBuffer buffer;

    Consumer(SharedBuffer b) {
        this.buffer = b;
    }

    public void run() {
        for (int i = 1; i <= 10; i++) {
            buffer.consume();
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

public class ProducerConsumer {
    public static void main(String[] args) {
        SharedBuffer buffer = new SharedBuffer();
        new Producer(buffer).start();
        new Consumer(buffer).start();
    }
}

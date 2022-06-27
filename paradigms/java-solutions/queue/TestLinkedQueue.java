package queue;

public class TestLinkedQueue {
    public static void main(String args[]) {
        System.out.println("LinkedList:");
        LinkedQueue queue = new LinkedQueue();
        for (int i = 0; i < 20; i++) {
            queue.enqueue(i);
            if (i % 2 == 0) {
                System.out.println(queue.dequeue() + " " + queue.size());
            }

        }
    }
}

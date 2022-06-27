package queue;

class TestArrayQueue {
    public static void main(String args[]) {
        System.out.println("ArrayQueue:");
        ArrayQueue queue = new ArrayQueue();
        for (int i = 0; i < 20; i++) {
            queue.push(i);
            if (i % 2 == 0) {
                System.out.println(queue.remove() + " " + queue.size());
            }

        }
    }
}
package queue;

public class TestArrayQueueModule {
    public static void main(String[] args) {
        System.out.println("ArrayQueueModule:");
        for (int i = 0; i < 20; i++) {
            ArrayQueueModule.push(i);
            if (i % 2 == 0) {
                System.out.println(ArrayQueueModule.remove() + " " + ArrayQueueModule.size());
            }
        }
    }
}

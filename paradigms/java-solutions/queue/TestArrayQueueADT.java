package queue;

public class TestArrayQueueADT {
    public static void main(String[] args) {
        System.out.println("ArrayQueueADT:");
        ArrayQueueADT queueADT = new ArrayQueueADT();
        for (int i = 0; i < 20; i++) {
            ArrayQueueADT.push(queueADT, i);
            if (i % 2 == 0) {
                System.out.println(ArrayQueueADT.remove(queueADT) + " " + ArrayQueueADT.count(queueADT, 1));
            }
        }
    }
}

package queue;

public class LinkedQueue extends AbstractQueue {
    private Node head;
    private Node tail;

    public LinkedQueue() {
        head = new Node();
        tail = new Node();
        head.next = tail;
    }

    @Override
    protected Object elementImpl() {
        return head.next.value;
    }

    @Override
    protected void enqueueImpl(Object element) {
        tail.value = element;
        tail.next = new Node();
        tail = tail.next;
    }

    @Override
    protected Queue makeQueue() {
        return new LinkedQueue();
    }

    @Override
    protected Object dequeueImpl() {
        Object element = head.next.value;
        head = head.next;
        return element;
    }

    @Override
    public void clearImpl() {
        head.next = tail;
    }

    private static class Node {
        private Node next;
        private Object value;
    }
}

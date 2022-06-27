package queue;

import java.util.Objects;

/*
Model: a[1] ... a[n]
Invariant: for i=1..n: a[i] != null

Let immutable(n): for i=1..n a'[i] == a[i]

    Pred: element != null;
    Post: n' == n + 1 && immutable(n) && a[n'] == element
        enqueue(element)

    Pred: n > 0
    Post: n' == n - 1 && (for i=1..n': a'[i] == a[i + 1]) && R == a[1]
        dequeue()

    Pred: n > 0
    Post: n' == n && immutable(n) && R == a[n]
        element()

    Pred: true
    Post: R == n && n' == n && immutable(n)
        size()

    Pred: true
    Post: R == (n == 0) && n' == n && immutable(n)
        isEmpty()

    Pred: true
    Post: n' == 0
        clear()



 */
public class ArrayQueueADT {
    private Object[] elements = new Object[4];
    private int head = 0;
    private int tail = 0;

    //    Pred: element != null;
    //    Post: n' == n + 1 && immutable(n) && a[n'] == element
    public static void enqueue(final ArrayQueueADT queue, final Object element) {
        Objects.requireNonNull(element);
        queue.elements[queue.tail] = element;
        queue.tail = (queue.tail + 1) % queue.elements.length;
        ensureCapacity(queue);
    }

    //    Pred: n > 0
//    Post: n' == n - 1 && (for i=1..n': a'[i] == a[i + 1]) && R == a[1]
    public static Object dequeue(final ArrayQueueADT queue) {
        final Object element = queue.elements[queue.head];
        queue.elements[queue.head] = null;
        queue.head = (queue.head + 1) % queue.elements.length;
        return element;
    }

    //    Pred: n > 0
    //    Post: n' == n && immutable(n) && R == a[n]
    public static Object element(final ArrayQueueADT queue) {
        return queue.elements[queue.head];
    }

    //Pred: element != null
    //Post: n' = n + 1 && immutable(n) && a[n'] = element
    public static void push(final ArrayQueueADT queue, final Object element) {
        queue.head = queue.head - 1 >= 0 ? queue.head - 1 : queue.elements.length - 1;
        queue.elements[queue.head] = element;
        ensureCapacity(queue);
    }

    //Pred: size(queue) > 0
    //Post: n' == n && immutable(n') && R == a[1]
    public static Object peek(final ArrayQueueADT queue) {
        assert size(queue) > 0;
        final int pos = queue.tail - 1 >= 0 ? queue.tail - 1 : queue.elements.length - 1;
        return queue.elements[pos];
    }

    //Pred: size > 0
    //Post: R == a[n] && n' == n - 1 && immutable(n')
    public static Object remove(final ArrayQueueADT queue) {
        assert size(queue) > 0;
        final int pos = queue.tail - 1 >= 0 ? queue.tail - 1 : queue.elements.length - 1;
        final Object result = queue.elements[pos];
        queue.elements[pos] = null;
        queue.tail = pos;
        return result;
    }

    //    Pred: true
    //    Post: R == n && n' == n && immutable(n)
    public static int size(final ArrayQueueADT queue) {
        return queue.head <= queue.tail ? queue.tail - queue.head : queue.elements.length - (queue.head - queue.tail);
    }

    //    Pred: true
    //    Post: R == (n == 0) && n' == n && immutable(n)
    public static boolean isEmpty(final ArrayQueueADT queue) {
        return queue.head == queue.tail;
    }

    //    Pred: true
    //    Post: n' == 0
    public static void clear(final ArrayQueueADT queue) {
        queue.elements = new Object[4];
        queue.head = 0;
        queue.tail = 0;
    }

    //Pred: element != null
    // :NOTE: Императивный
    //Post: R == (count : for i=1..n : a[i] == element ? count' = count + 1 : count' = count) && n' == n
    public static int count(final ArrayQueueADT queue, final Object element) {
        Objects.requireNonNull(element);
        int index = queue.head;
        int result = 0;
        while (index != queue.tail) {
            if (element.equals(queue.elements[index])) {
                result++;
            }
            index = index < queue.elements.length - 1 ? index + 1 : 0;
        }
        return result;
    }

    private static void ensureCapacity(final ArrayQueueADT queue) {
        if ((queue.tail + 1) % queue.elements.length == queue.head) {
            final Object[] elementsNew = new Object[queue.elements.length * 2];
            System.arraycopy(queue.elements, queue.head, elementsNew, 0, queue.elements.length - queue.head);
            System.arraycopy(queue.elements, 0, elementsNew, queue.elements.length - queue.head, queue.tail);
            queue.head = 0;
            queue.tail = queue.elements.length - 1;
            queue.elements = elementsNew;
        }
    }
}

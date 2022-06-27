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
public class ArrayQueueModule {
    private static Object[] elements = new Object[4];
    private static int head = 0;
    private static int tail = 0;

    //    Pred: element != null;
    //    Post: n' == n + 1 && immutable(n) && a[n'] == element
    public static void enqueue(Object element) {
        Objects.requireNonNull(element);
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
        ensureCapacity();
    }

    //    Pred: n > 0
    //    Post: n' == n - 1 && (for i=1..n': a'[i] == a[i + 1]) && R == a[1]
    public static Object dequeue() {
        assert size() > 0;
        Object element = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        return element;
    }

    //    Pred: n > 0
    //    Post: n' == n && immutable(n) && R == a[n]
    public static Object element() {
        return elements[head];
    }

    //Pred: element != null
    //Post: n' = n + 1 && immutable(n) && a[n'] = element
    public static void push(Object element) {
        head = head - 1 >= 0 ? head - 1 : elements.length - 1;
        elements[head] = element;
        ensureCapacity();
    }

    //Pred: size > 0
    //Post: n' == n && immutable(n') && R == a[1]
    public static Object peek() {
        assert size() > 0;
        int pos = tail - 1 >= 0 ? tail - 1 : elements.length - 1;
        return elements[pos];
    }

    //Pred: size > 0
    //Post: R == a[n] && n' == n - 1 && immutable(n')
    public static Object remove() {
        assert size() > 0;
        int pos = tail - 1 >= 0 ? tail - 1 : elements.length - 1;
        Object result = elements[pos];
        elements[pos] = null;
        tail = pos;
        return result;
    }

    //    Pred: true
    //    Post: R == n && n' == n && immutable(n)
    public static int size() {
        return head <= tail ? tail - head : elements.length - (head - tail);
    }

    //    Pred: true
    //    Post: R == (n == 0) && n' == n && immutable(n)
    public static boolean isEmpty() {
        return head == tail;
    }

    //    Pred: true
    //    Post: n' == 0
    public static void clear() {
        elements = new Object[4];
        head = 0;
        tail = 0;
    }

    //Pred: element != null
    //Post: R == (count : for i=1..n : a[i] == element ? count' = count + 1: count' = count) && n' == n
    public static int count(Object element) {
        Objects.requireNonNull(element);
        int index = head;
        int result = 0;
        while (index != tail) {
            if (element.equals(elements[index])) {
                result++;
            }
            index = elements.length - 1 > index ? index + 1 : 0;
        }
        return result;
    }

    private static void ensureCapacity() {
        if ((tail + 1) % elements.length == head) {
            Object[] elementsNew = new Object[elements.length * 2];
            System.arraycopy(elements, head, elementsNew, 0, elements.length - head);
            System.arraycopy(elements, 0, elementsNew, elements.length - head, tail);
            head = 0;
            tail = elements.length - 1;
            elements = elementsNew;
        }
    }
}

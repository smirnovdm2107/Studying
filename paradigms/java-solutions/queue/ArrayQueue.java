package queue;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

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

    Pred: element != null
    Post: n' = n + 1 && immutable(n) && a[n'] = element
        push(element)

    Pred: size > 0
    Post: n' == n && immutable(n') && R == a[1]
        peek()

    Pred: size > 0
    Post: R == a[n] && n' == n - 1 && immutable(n')
        remove()

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
public class ArrayQueue extends AbstractQueue {
    private Object[] elements = new Object[4];
    private int head = 0;
    private int tail = 0;

    //    Pred: element != null;
//    Post: n' == n + 1 && immutable(n) && a[n'] == element
    @Override
    public void enqueue(Object element) {
        Objects.requireNonNull(element);
        size++;
        enqueueImpl(element);
    }

    //    Pred: n > 0
//    Post: n' == n - 1 && (for i=1..n': a'[i] == a[i + 1]) && R == a[1]
    @Override
    public Object dequeue() {
        assert size() > 0;
        size--;
        return dequeueImpl();
    }

    @Override
    protected void enqueueImpl(Object element) {
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
        ensureCapacity();
    }

    @Override
    protected Object dequeueImpl() {
        Object element = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        return element;
    }

    @Override
    protected Object elementImpl() {
        return elements[head];
    }

    @Override
    protected void clearImpl() {
        elements = new Object[4];
        head = 0;
        tail = 0;
    }

    //    Pred: n > 0
//    Post: n' == n && immutable(n) && R == a[n]
    public Object element() {
        assert size > 0;
        return elements[head];
    }



    //Pred: element != null
    //Post: n' = n + 1 && immutable(n) && a[n'] = element
    public void push(Object element) {
        head = head - 1 >= 0 ? head - 1 : elements.length - 1;
        elements[head] = element;
        size++;
        ensureCapacity();
    }

    //Pred: size > 0
    //Post: n' == n && immutable(n') && R == a[1]
    public Object peek() {
        assert size() > 0;
        int pos = tail - 1 >= 0 ? tail - 1 : elements.length - 1;
        return elements[pos];
    }

    //Pred: size > 0
    //Post: R == a[n] && n' == n - 1 && immutable(n')
    public Object remove() {
        assert size() > 0;
        int pos = tail - 1 >= 0 ? tail - 1 : elements.length - 1;
        Object result = elements[pos];
        elements[pos] = null;
        tail = pos;
        size--;
        return result;
    }
    //    Pred: true
    //    Post: R == n && n' == n && immutable(n)
    public int size() {
        return size;
    }

    //    Pred: true
    //    Post: R == (n == 0) && n' == n && immutable(n)
    public boolean isEmpty() {
        return size == 0;
    }

    //    Pred: true
    //    Post: n' == 0
    @Override
    public void clear() {
        size = 0;
        elements = new Object[4];
        head = 0;
        tail = 0;
    }

    @Override
    protected Queue makeQueue() {
        return new ArrayQueue();
    }

    //Pred: element != null
    //Post: R == (count : for i=1..n : a[i] == element ? count' = count + 1 : count' = count) && n' == n
    public int count(Object element) {
        Objects.requireNonNull(element);
        int index = head;
        int result = 0;
        while(index != tail) {
            if (element.equals(elements[index])) {
                result++;
            }
            index = index < elements.length - 1 ? index + 1 : 0;
        }
        return result;
    }

    private void ensureCapacity() {
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

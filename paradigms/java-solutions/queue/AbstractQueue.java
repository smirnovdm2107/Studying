package queue;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue {

    protected int size;

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
        clearImpl();
    }

    protected abstract void clearImpl();

    @Override
    public void enqueue(final Object element) {
        Objects.requireNonNull(element);
        enqueueImpl(element);
        size++;
    }

    protected abstract void enqueueImpl(Object element);

    @Override
    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    protected abstract Object elementImpl();

    @Override
    public Object dequeue() {
        assert size > 0;
        size--;
        return dequeueImpl();
    }

    protected abstract Object dequeueImpl();

    public int size() {
        return size;
    }

    @Override
    public Queue filter(final Predicate predicate) {
        Objects.requireNonNull(predicate);
        return applyIf(o -> o, predicate);
    }

    @Override
    public Queue map(final Function function) {
        Objects.requireNonNull(function);
        return applyIf(function, o -> true);
    }

    private Queue applyIf(final Function function, final Predicate predicate) {
        final Queue queue = makeQueue();
        for (int i = 0; i < size; i++) {
            final Object element = dequeue();
            if (predicate.test(element)) {
                queue.enqueue(function.apply(element));
            }
            enqueue(element);
        }
        return queue;
    }

    protected abstract Queue makeQueue();
}

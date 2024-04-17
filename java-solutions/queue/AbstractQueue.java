package queue;

import java.util.Objects;


public abstract class AbstractQueue implements Queue {
    protected int size;

    @Override
    public void enqueue(Object element) {
        Objects.requireNonNull(element);
        enqueueImpl(element);
        size++;
    }

    @Override
    public Object dequeue() {
        assert size() > 0;
        size--;
        return dequeueImpl();
    }

    @Override
    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void dedup() {
        int cnt = size;
        for (int i = 0; i < cnt; i++) {
            Object element = dequeue();
            if (i == cnt - 1 || !element.equals(element())) {
                enqueue(element);
            }
        }
    }
}

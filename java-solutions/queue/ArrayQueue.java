package queue;

import java.util.Arrays;
import java.util.function.Predicate;

// Model: queue[head]...queue[tail] size - количество элементов в очереди
// Inv: n >= 0 && forall i=1..n: a[i] != null
// Let: immutable(k): forall i=head...tail: a'[i % elements.length] = a[i % elements.length]
// Let: tail = (size + head) % elements.length
public class ArrayQueue extends AbstractQueue {
    private int head;
    private Object[] elements = new Object[10];

    // Pre: element != null
    // Post: queue'[tail] = element && immutable(size)
    // Полная форма
    public void enqueueImpl(Object element) {
        ensureCapacity(this.tail() + 1);
        this.elements[this.tail()] = element;
    }

    // Pre: true
    // Post: size' = size && immutable(size)
    // Неявный this
    private void ensureCapacity(int capacity) {
        if (capacity % elements.length == head) {
            Object[] buffer = Arrays.copyOf(elements, elements.length);
            elements = Arrays.copyOf(elements, 2 * elements.length);
            System.arraycopy(buffer, head, elements, 0, buffer.length - head);
            System.arraycopy(buffer, 0, elements, buffer.length - head, head);
            head = 0;
        }
    }

    // Pre: size > 0
    // Post: R = queue[head] && immutable(size - 1)
    // Необязательный this
    public Object dequeueImpl() {
        Object object = elements[head];
        elements[head] = null;
        head = head + 1 == elements.length ? 0 : head + 1;
        return object;
    }

    // Pre: size > 0
    // Post: R = queue[head] && head' = head && immutable(size)
    public Object elementImpl() {
        return elements[head];
    }

    // Pre: true
    // Post: size = 0
    public void clear() {
        elements = new Object[10];
        head = 0;
        size = 0;
    }

    // Pre: elements.length != 0
    // Post: R = (size + head) % elements.length && size' = size && head' = head
    private int tail() {
        return (size + head) % elements.length;
    }

    // Pre: queue != null && Predicate != null
    // Post: R = количество элементов в очереди удовлетворяющих предикату && immutable(size)
    public int countIf(Predicate<Object> p) {
        int count = 0;
        for (int i = head; i < head + size; i++) {
            if (p.test(elements[i % elements.length])) {
                count++;
            }
        }
        return count;
    }
}

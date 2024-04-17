package queue;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

// Model: queue[head]...queue[tail] size - количество элементов в очереди
// Inv: n >= 0 && forall i=1..n: a[i] != null
// Let: immutable(k): forall i=head...tail: a'[i % elements.length] = a[i % elements.length]
// Let: tail = (size + head) % elements.length
public class ArrayQueueModule {
    private static int head;
    private static int size;
    private static Object[] elements = new Object[10];

    // Pre: element != null
    // Post: a'[tail] = element && immutable(size)
    public static void enqueue(Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(tail() + 1);
        elements[tail()] = element;
        size++;
    }

    // Pre: true
    // Post: size' = size && immutable(size)
    private static void ensureCapacity(int capacity) {
        if (capacity % elements.length == head) {
            Object[] buffer = Arrays.copyOf(elements, elements.length);
            elements = Arrays.copyOf(elements, 2 * elements.length);
            System.arraycopy(buffer, head, elements, 0, buffer.length - head);
            System.arraycopy(buffer, 0, elements, buffer.length - head, head);
            head = 0;
        }
    }

    // Pre: size > 0
    // Post: R = a[head] && immutable(head')
    public static Object dequeue() {
        assert size > 0;
        Object object = elements[head];
        elements[head] = null;
        head = head + 1 == elements.length ? 0 : head + 1;
        size--;
        return object;
    }

    // Pre: size > 0
    // Post: R = a[head] && head' = head && immutable(size)
    public static Object element() {
        assert size > 0;
        return elements[head];
    }

    // Pre: true
    // Post: R = size && size' = size && immutable(size)
    public static int size() {
        return size;
    }

    // Pre: true
    // Post: R = (size = 0) && size' = size && immutable(size)
    public static boolean isEmpty() {
        return size == 0;
    }

    // Pre: true
    // Post: size = 0
    public static void clear() {
        elements = new Object[10];
        head = 0;
        size = 0;
    }

    // Pre: elements.length != 0
    // Post: R = (size + head) % elements.length && size' = size && head' = head
    private static int tail() {
        return (size + head) % elements.length;
    }

    // Pre: queue != null && Predicate != null
    // Post: R = count && immutable(size)

    public static int countIf(Predicate<Object> p) {
        int count = 0;
        for (int i = head; i < head + size; i++) {
            if (p.test(elements[i % elements.length])) {
                count++;
            }
        }
        return count;
    }
}
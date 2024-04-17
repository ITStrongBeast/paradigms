package queue;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

// Model: queue[head]...queue[tail] size - количество элементов в очереди
// Inv: n >= 0 && forall i=1..n: a[i] != null
// Let: immutable(k): forall i=head...tail: a'[i % elements.length] = a[i % elements.length]
// Let: tail = (size + head) % elements.length
public class ArrayQueueADT {
    public int head;
    public int size;
    public Object[] elements = new Object[5];

    // Pre: true
    // Post: R.n = 0
    public static ArrayQueueADT create() {
        ArrayQueueADT queue = new ArrayQueueADT();
        queue.elements = new Object[5];
        return queue;
    }

    // Pre: queue != null && element != null
    // Post: a'[tail] = element && immutable(size)
    public static void enqueue(ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(queue, tail(queue) + 1);
        queue.elements[tail(queue)] = element;
        queue.size++;
    }

    // Pre: queue != null
    // Post: size' = size && immutable(size)
    private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
        if (capacity % queue.elements.length == queue.head) {
            Object[] buffer = Arrays.copyOf(queue.elements, queue.elements.length);
            queue.elements = Arrays.copyOf(queue.elements, 2 * queue.elements.length);
            System.arraycopy(buffer, queue.head, queue.elements, 0, buffer.length - queue.head);
            System.arraycopy(buffer, 0, queue.elements, buffer.length - queue.head, queue.head);
            queue.head = 0;
        }
    }

    // Pre: queue != null && size > 0
    // Post: R = a[head] && immutable(head')
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue.size > 0;
        Object object = queue.elements[queue.head];
        queue.elements[queue.head] = null;
        queue.head = queue.head + 1 == queue.elements.length ? 0 : queue.head + 1;
        queue.size--;
        return object;
    }

    // Pre: queue != null && size > 0
    // Post: R = a[head] && head' = head && immutable(size)
    public static Object element(ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[queue.head];
    }

    // Pre: queue != null
    // Post: R = size && size' = size && immutable(size)
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

    // Pre: queue != null
    // Post: R = (size = 0) && size' = size && immutable(size)
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }

    // Pre: queue != null
    // Post: size = 0
    public static void clear(ArrayQueueADT queue) {
        queue.elements = new Object[10];
        queue.head = 0;
        queue.size = 0;
    }

    // Pre: elements.length != 0
    // Post: R = (size + head) % elements.length && size' = size && head' = head
    private static int tail(ArrayQueueADT queue) {
        return (queue.size + queue.head) % queue.elements.length;
    }

    // Pre: queue != null && Predicate != null
    // Post: R = count && immutable(size)

    public static int countIf(ArrayQueueADT queue, Predicate<Object> p) {
        int count = 0;
        for (int i = queue.head; i < queue.head + queue.size; i++) {
            if (p.test(queue.elements[i % queue.elements.length])) {
                count++;
            }
        }
        return count;
    }
}
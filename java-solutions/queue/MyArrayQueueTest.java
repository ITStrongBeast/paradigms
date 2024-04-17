package queue;

import java.util.function.Predicate;

public class MyArrayQueueTest {
    public static void fill(ArrayQueue queue, String prefix) {
        for (int i = 0; i < 10; i++) {
            queue.enqueue(prefix + i);
        }
    }

    public static void fill_one(ArrayQueue queue, String prefix, int cnt, int count) {
        for (int i = 0; i < cnt; i++) {
            queue.enqueue(prefix + count);
        }
    }

    public static void dump(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.size() + " " + queue.dequeue());
        }
    }

    public static void testClear(ArrayQueue queue) {
        queue.clear();
        if (ArrayQueueModule.size() == 0 && ArrayQueueModule.element() == null) {
            System.out.println("testClear COMPLETE");
        } else {
            System.out.println("testClear FAILED");
        }
    }

    public static void fill_5(ArrayQueue queue) {
        for (int i = 0; i < 5; i++) {
            queue.enqueue(5);
        }
    }

    public static void testCountIf(ArrayQueue queue) {
        fill_5(queue);
        Predicate<Object> p = o -> o.equals(5);
        int count = queue.countIf(p);
        if (count == 5) {
            System.out.println("testCountIf COMPLETE");
        } else {
            System.out.println("testCountIf FAILED");
        }
    }

    public static void main(String[] args) {
        ArrayQueue queue1 = new ArrayQueue();
        ArrayQueue queue2 = new ArrayQueue();
        fill(queue1, "s1_");
        fill(queue2, "s2_");
        dump(queue1);
        dump(queue2);
        fill(queue1, "s1_");
        testClear(queue1);
        testCountIf(queue1);
    }
}

package queue;

import java.util.function.Predicate;

public class MyArrayQueueADTTest {
    public static void fill(ArrayQueueADT queue, String prefix) {
        for (int i = 0; i < 10; i++) {
            ArrayQueueADT.enqueue(queue, prefix + i);
        }
    }

    public static void dump(ArrayQueueADT queue) {
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(
                ArrayQueueADT.size(queue) + " " +
                ArrayQueueADT.element(queue) + " " +
                ArrayQueueADT.dequeue(queue)
            );
        }
    }

    public static void testClear(ArrayQueueADT queue) {
        ArrayQueueADT.clear(queue);
        if (ArrayQueueModule.size() == 0 && ArrayQueueModule.element() == null) {
            System.out.println("testClear COMPLETE");
        } else {
            System.out.println("testClear FAILED");
        }
    }

    public static void fill_5(ArrayQueueADT queue) {
        for (int i = 0; i < 5; i++) {
            ArrayQueueADT.enqueue(queue, 5);
        }
    }

    public static void testCountIf(ArrayQueueADT queue) {
        fill_5(queue);
        Predicate<Object> p = o -> o.equals(5);
        int count = ArrayQueueADT.countIf(queue, p);
        if (count == 5) {
            System.out.println("testCountIf COMPLETE");
        } else {
            System.out.println("testCountIf FAILED");
        }
    }

    public static void main(String[] args) {
        ArrayQueueADT queue1 = ArrayQueueADT.create();
        ArrayQueueADT queue2 = ArrayQueueADT.create();
        fill(queue1, "s1_");
        fill(queue2, "s2_");
        dump(queue1);
        dump(queue2);
        fill(queue1, "s1_");
        testClear(queue1);
        testCountIf(queue1);
    }
}

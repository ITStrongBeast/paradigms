package queue;

import java.util.function.Predicate;

public class MyArrayQueueModuleTest {
    public static void fill() {
        for (int i = 0; i < 10; i++) {
            ArrayQueueModule.enqueue(i);
        }
    }

    public static void dump() {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(
                ArrayQueueModule.size() + " " +
                ArrayQueueModule.element() + " " +
                ArrayQueueModule.dequeue()
            );
        }
    }

    public static void testClear() {
        ArrayQueueModule.clear();
        if (ArrayQueueModule.size() == 0 && ArrayQueueModule.element() == null) {
            System.out.println("testClear COMPLETE");
        } else {
            System.out.println("testClear FAILED");
        }
    }

    public static void fill_5() {
        for (int i = 0; i < 5; i++) {
            ArrayQueueModule.enqueue(5);
        }
    }

    public static void testCountIf() {
        fill_5();
        Predicate<Object> p = o -> o.equals(5);
        int count = ArrayQueueModule.countIf(p);
        if (count == 5) {
            System.out.println("testCountIf COMPLETE");
        } else {
            System.out.println("testCountIf FAILED");
        }
    }

    public static void main(String[] args) {
        fill();
        dump();
        fill();
        testClear();
        testCountIf();
    }
}

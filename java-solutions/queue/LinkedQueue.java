package queue;


public class LinkedQueue extends AbstractQueue {
    private Node head;
    private Node tail;

    private class Node {
        private final Object value;
        private Node post;

        public Node(Object value, Node post) {
            assert value != null;
            this.value = value;
            this.post = post;
        }
    }

    public void enqueueImpl(Object element) {
        if (size == 0) {
            head = new Node(element, null);
        } else if (size == 1) {
            tail = new Node(element, null);
            head.post = tail;
        } else {
            tail.post = new Node(element, null);
            tail = tail.post;
        }
    }

    public Object dequeueImpl() {
        Object result = head.value;
        head = head.post;
        return result;
    }

    public Object elementImpl() {
        return head.value;
    }

    public void clear() {
        size = 0;
        tail = null;
        head = null;
    }
}

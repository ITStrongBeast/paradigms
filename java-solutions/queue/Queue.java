package queue;

// Model: queue[head]...queue[tail] size - количество элементов в очереди
// Inv: n >= 0 && forall i=1..n: a[i] != null
// Let: immutable(k): forall i=head...tail: a'[i % elements.length] = a[i % elements.length]
// Let: tail = (size + head) % elements.length
public interface Queue {

    // Pre: element != null
    // Post: a'[tail] = element && immutable(size)
    public void enqueueImpl(Object element);

    // Pre: element != null
    // Post: a'[tail] = element && immutable(size)
    public void enqueue(Object element);

    // Pre: size > 0
    // Post: R = a[head] && immutable(size - 1)
    public Object dequeueImpl();

    // Pre: size > 0
    // Post: R = a[head] && immutable(size - 1)
    public Object dequeue();

    // Pre: size > 0
    // Post: R = a[head] && head' = head && immutable(size)
    public Object element();

    // Pre: size > 0
    // Post: R = a[head] && head' = head && immutable(size)
    public Object elementImpl();

    // Pre: true
    // Post: R = size
    public int size();

    // Pre: true
    // Post: R = size == 0
    public boolean isEmpty();

    // Pre: true
    // Post: R = void && queue' ⊂ queue && ∀ x∈queue ∃ i∈(size - 1): x == queue'[i]  && ∀ i∈(size - 1): queue[i] != queue[i+1]
    public void dedup();
}

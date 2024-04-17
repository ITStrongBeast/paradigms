package search;

public class BinarySearchClosestD {

    /* Условные обозначения:

        Invar - Инвариант
        Pred - Пред-условие
        Post - Пост-условие
        A - множество индексов i ∈ int[] a
        b - универсальное буферное значение
        result - возвращаемый результат

    */


    // Pred: Подаётся int x, после которого подаётся массив a[0...a.len-1], где ∀ i∈A, j∈A, i > j: a[i] <= a[j]
    // Post: void System.out.println(result) result ∈ a: ∀ y∈a: x - y минимально при y = result

    public static void main(String[] args) {
        // Pred: b = Integer.parseInt(args[0])
        int x = Integer.parseInt(args[0]);
        // Post: x = b
        // Pred: b = new int[args.length - 1]
        int[] a = new int[args.length - 1];
        // Post: a = b && a.len = args.length - 1 && ∀ i∈A: a[i] - int
        // Pred: b = 1
        int i = 1;
        // Post: int i = b = 1 ⇒ i > 0
        // Pred: b = 0
        int sum = 0;
        // Post: sum = b ⇒ sum ∈ Z
        // Pred: i > 0, a.len == args.len - 1
        // Invar: 0 < i' < args.length, a.len == args.len - 1 ⇒ 0 < i' <= a.len
        while (i < args.length) {
            // 0 < i' <= a.len, 0 < i' < args.length ⇒ ∃ a[i - 1], ∃ args[i]
            int y = Integer.parseInt(args[i]);
            // y - int ⇒ y ∈ Z
            // Pred: int args[i] = b
            a[i - 1] = y;
            // Post: a[i - 1] = b
            // Pred: b = sum + y
            sum += y;
            // Post: sum' = b && sum ∈ Z && y ∈ Z ⇒ sum' ∈ Z
            // Pred: i' + 1 = b
            i++;
            // Post: i' = b
        }
        // Post: i == args.length, ∀ i∈A, j∈A, i > j: a[i] <= a[j] && sum ∈ Z
        // Pred: sum ∈ Z ⇒ sum % 2 == (0 || 1)
        if (sum % 2 == 0) {
            print(recursiveBinSearch(x, a, -1, a.length), x, a);
        } else {
            print(iterBinSearch(x, a), x, a);
        }
        // Post: void System.out.println(result) result ∈ a: ∀ y∈a: x - y минимально при y = result
    }


    /*
        Pred: Был вызван recursiveBinSearch либо iterBinSearch и получен i ∈ A: i - min(A): x => a[i]: -1 <= i <= a.len &&
        ∀ y∈A: x - y минимально при y = (a[0] || a[a.len - 1] || a[i] || a[i + 1])
    */
    // Post: void System.out.println(result) result ∈ a: ∀ y∈A: x - y минимально при y = result

    private static void print(int i, int x, int[] a) {
        // Pred: i ∈ A: i - min(A): x => a[i]: -1 <= i <= a.len && ∀ y∈A: x - y минимально при
        // y = (a[0] || a[a.len - 1] || a[i] || a[i + 1])
        if (i == -1) {
            // i == -1 ⇒ x > a[0] ⇒ ∀ y∈A: x - y минимально при y = a[0] ⇒ result = a[0]
            System.out.println(a[0]);
        } else if (i == a.length) {
            // i == a.len ⇒ x < a[a.len - 1] ⇒ ∀ y∈A: x - y минимально при y = a[a.length - 1] ⇒ result = a[a.length - 1]
            System.out.println(a[a.length - 1]);
        } else if (i + 1 <= a.length - 1 && a[i] - x > x - a[i + 1]) {
            // a[i] - x > x - a[i + 1] ⇒ ∀ y∈A: x - y минимально при y = a[i + 1] ⇒ result = a[i + 1]
            System.out.println(a[i + 1]);
        } else {
            // a[i] - x <= x - a[i + 1] ⇒ ∀ y∈A: x - y минимально при y = a[i] ⇒ result = a[i]
            System.out.println(a[i]);
        }
        // Post: void System.out.println(result) result ∈ a: ∀ y∈A: x - y минимально при y = result
    }


    /* Pred: Был вызван main и получены:
    int[] a, ∀ i∈A, j∈A, i > j: a[i] <= a[j],
    int l ∈ A, int r ∈ A: l <= r - 1, r > l,
    int x
     */
    // :NOTE: Check closely
    // Post: int i ∈ A, i - min(A): x => a[i] && ∀ y∈a: x - y минимально при y = (a[0] || a[a.len - 1] || a[i] || a[i + 1])

    private static int recursiveBinSearch(int x, int[] a, int l, int r) {
        // Pred: -1 <= l' <= a.len && -1 <= r' <= a.len
        if (r <= l + 1) {
            // a[l'] <= x && a[r'] > x ⇒ l' - min(A): x => a[l']: -1 <= l' <= a.len ⇒ i = l'
            return l;
        }
        /* Pred:
         b = (l' + r') / 2
         (l' + r') / 2 = (l' + r' - 2l') / 2 + l' = l' + (r' - l') / 2 ⇒
         b = l' + (r' - l') / 2
         */
        int m = l + (r - l) / 2;
        // Post: int m = b &&  (l' <= m' < r' || l' < m' <= r')
        // -1 <= l' <= a.len && -1 <= r' <= a.len && l' <= m <= r' ⇒ -1 <= m <= a.len
        // Pred: -1 <= m' <= a.len
        if (a[m] < x) {
            // Pred: a[m] < x && l' <= r' - 1
            // a[m] < x ⇒ r' = m ⇒ a[r'] < x && -1 <= r' <= a.len
            return recursiveBinSearch(x, a, l, m);
        } else {
            // Pred: a[m] => x && l' <= r' - 1
            // a[m] => x ⇒ l' = m ⇒ a[l'] => x && -1 <= l' <= a.len
            return recursiveBinSearch(x, a, m, r);
        }
        // Post: int i ∈ A, i - min(A): x => a[i] ⇒ ∀ y∈a: x - y минимально при y = (a[0] || a[a.len - 1] || a[i] || a[i + 1])
    }


    // Pred: Был вызван main и получены: int[] a, ∀ i∈A, j∈A, i > j: a[i] <= a[j]; int x.
    // Post: int i ∈ A, i - min(A): x => a[i]: -1 <= i <= a.len && ∀ y∈a: x - y минимально при y = (a[0] || a[a.len - 1] || a[i] || a[i + 1])

    private static int iterBinSearch(int x, int[] a) {
        // b = -1
        int l = -1;
        // int l = b
        // b = a.len
        int r = a.length;
        // int r = b
        // Pred: l = -1 && r = a.len ⇒ l < r
        while (r > l + 1) {
            // Invar: -1 <= l' <= a.len && -1 <= r' <= a.len
            /* Pred:
             b = (l' + r') / 2
             (l' + r') / 2 = (l' + r' - 2l') / 2 + l' = l' + (r' - l') / 2 ⇒
             b = l' + (r' - l') / 2
             */
            int m = l + (r - l) / 2;
            // Post: int m' = b &&  (l' <= m' < r' || l' < m' <= r')
            // -1 <= l' <= a.len && -1 <= r' <= a.len && l' <= m' <= r' ⇒ -1 <= m' <= a.len
            // Pred: -1 <= m' <= a.len
            if (a[m] < x) {
                // a[m] < x ⇒ r' = m' ⇒ a[r'] < x
                // Pred: b = m'
                r = m;
                // Post: int r' = b && -1 <= r' <= a.len
            } else {
                // a[m] >= x ⇒ l' = m' ⇒ a[m'] >= x
                // Pred: b = m'
                l = m;
                // Post: int l' = b && -1 <= l' <= a.len
            }
            // Post: -1 <= l' <= a.len && -1 <= r' <= a.len
        }
        // Post: -1 <= l <= a.len && -1 <= r <= a.len && l = r - 1
        // l = r - 1  ⇒  l < r && a[l'] <= x && a[r'] > x ⇒ i = l'
        return l;
    }
}
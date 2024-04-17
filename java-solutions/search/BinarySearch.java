package search;

public class BinarySearch {

    /* Условные обозначения:

        Invar - Инвариант
        Pred - Пред-условие
        Post - Пост-условие
        A - множество индексов i ∈ int[] a
        b - универсальное буферное значение
        result - возвращаемый результат

     */


    // Pred: Подаётся int x, после которого подаётся массив a[0...a.len-1], где ∀ i∈A, j∈A, i > j: a[i] <= a[j]
    // Post: void System.out.println(result) result ∈ A, result - min(A): x => a[result], 0 <= result <= a.len - 1

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

        // Pred: i > 0, a.len == args.len - 1
        // Invar: 0 < i' < args.length, a.len == args.len - 1 ⇒ 0 < i' <= a.len
        while (i < args.length) {
            // 0 < i' <= a.len, 0 < i' < args.length ⇒ ∃ a[i - 1], ∃ args[i]

            // Pred: int args[i] = b
            a[i - 1] = Integer.parseInt(args[i]);;
            // Post: a[i - 1] = b

            // Pred: i' + 1 = b
            i++;
            // Post: i' = b
        }
        // Post: i == args.length, ∀ i∈A, j∈A, i > j: a[i] <= a[j]
        System.out.println(recursiveBinSearch(x, a, 0, a.length - 1));
    }


    /* Pred: Был вызван main и получены:
    int[] a, ∀ i∈A, j∈A, i > j: a[i] <= a[j],
    int l ∈ A, int r ∈ A: l <= r - 1, r > l,
    int x
     */
    // Post: int result ∈ A, result - min(A): x => a[result]: 0 <= result <= a.len - 1

    private static int recursiveBinSearch(int x, int[] a, int l, int r) {
        // Pred: l' == r' + 1 && a['r] <= x && a['l] > x ⇒ a[l'] <= x && a[r'] > x
        if (l == r + 1) {
            // a[l'] <= x && a[r'] > x ⇒ l' - min(A): x => a[l']: 0 <= l' <= a.len - 1 ⇒
            // result = l'
            return l;
        }

        /* Pred:
         b = (l' + r') / 2
         (l' + r') / 2 = (l' + r' - 2l') / 2 + l' = l' + (r' - l') / 2 ⇒
         b = l' + (r' - l') / 2
         */
        int m = l + (r - l) / 2;
        // Post: int m = b &&  l' <= m <= r'

        // 0 <= l' <= a.len - 1 && 0 <= r' <= a.len - 1 && l' <= m <= r' ⇒ 0 <= m <= a.len - 1

        // Pred: 0 <= m' <= a.len - 1 ⇒ ∃ a[m]
        if (a[m] <= x) {
            // Pred: a[m] <= x && l <= r - 1
            // a[m] <= x ⇒ r' = m - 1 ⇒ a[r'] <= x && 0 <= r' <= a.len - 1
            return recursiveBinSearch(x, a, l, m - 1);
        } else {
            // Pred: a[m] > x && l <= r - 1
            // a[m] > x ⇒ l' = m + 1 ⇒ a[l'] > x && 0 <= l' <= a.len - 1
            return recursiveBinSearch(x, a, m + 1, r);
        }
        // Post: result ∈ A, result - min(A): x => a[result]: 0 <= result <= a.len - 1
    }


    // Pred: Был вызван main и получены: int[] a, ∀ i∈A, j∈A, i > j: a[i] <= a[j]; int x.
    // Post: int result ∈ A, result - min(A): x => a[result]: 0 <= result <= a.len - 1

    private static int iterBinSearch(int x, int[] a) {
        // b = 0
        int l = 0;
        // int l = b

        // b = a.length - 1
        int r = a.length - 1;
        // int r = b

        // Pred: l < r && l = 0 && r = a.length - 1
        while (l <= r) {
            // Invar: 0 <= l' <= a.len - 1 && 0 <= r' <= a.len - 1

            /* Pred:
             b = (l' + r') / 2
             (l' + r') / 2 = (l' + r' - 2l') / 2 + l' = l' + (r' - l') / 2 ⇒
             b = l' + (r' - l') / 2
             */
            int m = l + (r - l) / 2;
            // Post: int m' = b &&  l' <= m' <= r'

            // 0 <= l' <= a.len - 1 && 0 <= r' <= a.len - 1 && l' <= m' <= r' ⇒ 0 <= m' <= a.len - 1

            // Pred: 0 <= m' <= a.len - 1 ⇒ ∃ a[m]
            if (a[m] <= x) {
                // a[m] <= x ⇒ r' = m' - 1 ⇒ a[r'] <= x
                // Pred: b = m' - 1
                r = m - 1;
                // Post: int r' = b && 0 <= r' <= a.len - 1
            } else {
                // a[m] > x ⇒ l' = m' + 1 ⇒ a[m'] > x
                // Pred: b = m' + 1
                l = m + 1;
                // Post: int l' = b && 0 <= l' <= a.len - 1
            }
            // Post: 0 <= l' <= a.len - 1 && 0 <= r' <= a.len - 1
        }
        // Post: 0 <= l' <= a.len - 1 && 0 <= r' <= a.len - 1 && l' = r' + 1
        // l' == r' + 1 && a['r] <= x && a['l] > x ⇒ a[l'] <= x && a[r'] > x ⇒ result = l'
        return l;
    }
}
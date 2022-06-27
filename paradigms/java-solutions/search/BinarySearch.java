package search;

public class BinarySearch {
    // monotony(array[n]) : for i=1..n array[i - 1] >= array[i + 1]
    // parse(args[n]) : array[n] : for i=0..n - 1 array[i] == Integer.parseInt(array[i])
    //Pred: monotone(parse(Arrays.copyOfRange(args, 1, args.length - 1)) && args[0] - string representation of number
    //Post: i - min : a[i]
    public static void main(String[] args) {
        // args.length > 0 && (i : -1 < i < args.length -> args[i] - строковое представление числа типа int)
        // && (j : -1 < j < args.length - 1 -> Integer.parseInt(args[i]) >= Integer.parseInt(args[i + 1])
        int key = Integer.parseInt(args[0]);
        // key == Integer.parseInt(args[0]) && args.length > 0
        // && (j : 0 < j < args.length - 1 -> Integer.parseInt(args[i]) >= Integer.parseInt(args[i + 1])
        int[] array = new int[args.length - 1];
        // key == Integer.parseInt(args[0]) &&df
        // && (j : 0 < j < args.length - 1 -> Integer.parseInt(args[i]) >= Integer.parseInt(args[i + 1])
        for (int i = 0; i < array.length; i++) {
            // args[i + 1] - строковое представление числа && i < array.length && i < args.length - 1
            array[i] = Integer.parseInt(args[i + 1]);
            // array[i] == Integer.parseInt(args[i+1]) && i < array.length && i < args.length - 1
        }
        // (i : -1 < i < array.length - 1 -> array[i] >= array[i + 1]
        int ans = binarySearch1(key, array);
        // (i : -1 < i < ans -> key < array[i]) && (j : ans <= j < -> key >= array[i])
        //
        //
        // (i : -1 < i < array.length - 1 -> array[i] >= array[i + 1]
        //int ans = binarySearch2(key, array, -1, array.length);
        // (i : -1 < i < ans && array[i] > key) && (j: key <= j < array.length -> array[i] <= key)
        System.out.println(ans);
    }

    private static int binarySearch1(int key, int[] array) {
        // t : -1 < t < array.length - 1 -> array[t] >= array[t+1]
        //                  true
        //                    |
        //                    v
        //(j : array.length <= i < array.length -> array[i] <= key )
        //      && (t : -1 < t < array.length - 1 -> array[t] >= array[t+1])
        int r = array.length;
        // (j : r <= i < array.length -> array[i] <= key)
        //     && (t : -1 < t < array.length - 1 -> array[t] >= array[t+1])
        //          true
        //            |
        //            v
        // (i : -1 < i < -1 -> array[i] > key) && (j : r <= i < array.length -> array[i] <= key)
        //              && (t : -1 < t < array.length - 1 -> array[t] >= array[t+1])
        int l = -1;
        // I : (i : -1 < i < l -> array[i] > key) && (j : r <= i < array.length -> array[i] <= key)
        //              && (t : -1 < t < array.length - 1 -> array[t] >= array[t+1])
        while (l != r - 1) {
            //(l != r - 1) -> l < (l + r) / 2 < r
            //        && (i : -1 < i < l -> array[i] > key) && (j : r <= i < array.length -> array[i] <= key)
            //       && (t : -1 < t < array.length - 1 -> array[t] >= array[t+1])
            int m = (l + r) / 2;
            // l < m <  r && (i : -1 < i < l -> array[i] > key) && (j : r <= i < array.length -> array[i] <= key)
            //            && (t : -1 < t < array.length - 1 -> array[t] >= array[t+1])
            //
            if (key < array[m]) {
                // l < m <  r && (i : -1 < i < l -> array[i] > key) && (j : r <= i < array.length -> array[i] <= key)
                //            && (t : -1 < t < array.length - 1 -> array[t] >= array[t+1])
                //            && key < array[m]
                //
                //  l < m < r
                // key < array[m]                                            -> i : -1 < i < m -> array[i] > key
                // (t : -1 < t < array.length - 1 -> array[t] >= array[t+1])
                // значит из предусловия ветвления и условия ветвления выходит:
                // (i : -1 < i < m -> array[i] > key) && (j : r <= i < array.length -> array[i] <= key)
                //           && (t : -1 < t < array.length - 1 -> array[t] >= array[t+1])
                l = m;
                // (i : -1 < i < l' -> array[i] > key) && (j : r <= i < array.length -> array[i] <= key)
                //           && (t : -1 < t < array.length - 1 -> array[t] >= array[t+1])
            } else {
                // l < m <  r && (i : -1 < i < l -> array[i] > key) && (j : r <= i < array.length -> array[i] <= key)
                //            && (t : -1 < t < array.length - 1 -> array[t] >= array[t+1])
                //            && key < array[m]
                //
                //  l < m < r
                // key >= array[m]                                      -> j : m <= i < array.length -> array[i] <= key
                // (t : -1 < t < array.length - 1 -> array[t] >= array[t+1])
                // значит из предусловия ветвления и наврушения условия ветвления выходит:
                // (i : -1 < i < l -> array[i] > key) && (j : m <= i < array.length -> array[i] <= key)
                //           && (t : -1 < t < array.length - 1 -> array[t] >= array[t+1])
                r = m;
                // (i : -1 < i < l -> array[i] > key) && (j : r' <= i < array.length -> array[i] <= key)
                //           && (t : -1 < t < array.length - 1 -> array[t] >= array[t+1])
            }
            // I: (i : -1 < i < l -> array[i] > key) && (j : r <= i < array.length -> array[i] <= key)
            //       && (t : -1 < t < array.length - 1 -> array[t] >= array[t+1])
            //       ||a
            // I: (i : -1 < i < l -> array[i] > key) && (j : r <= i < array.length -> array[i] <= key)
            //       && (t : -1 < t < array.length - 1 -> array[t] >= array[t+1])
            // -> I
        }
        // I : (i : -1 < i < l -> array[i] > key) && (j : r <= i < array.length -> array[i] <= key
        //&&
        //l  == r - 1 (так как с каждым циклом l = m || r = m, где l < m < r -> l == r - 1 после какой-то итерации
        // из этого следует, что l в выражении можно заменить на r, а значит
        // (i : -1 < i < r -> array[i] > key) && (j: r <= j < array.length -> array[i] <= key)
        return r;

    }

    private static int binarySearch2(int key, int[] array, int l, int r) {
        // (i : -1 < i < array.size() - 1 -> array[i]) >= array[i + 1]
        // && -1 <= l < r <= array.length
        // (может не то) && (j : -1 < j < l -> array[j] > key) && (t : r <= t < array.answer - > array[t] >= key)
        if (l != r - 1) {
            // i : -1 < i < array.size() - 1 -> array[i] >= array[i + 1]
            // && (j : -1 < j < l -> array[j] > key) && (t : r <= t < array.answer - > array[t] >= key)
            // && l != r - 1
            int m = (l + r) / 2;
            // m == (l + r) / 2 && i : -1 < i < array.size() - 1 -> array[i] >= array[i + 1]
            //    && (j : -1 < j < l -> array[j] > key) && (t : r <= t < array.answer - > array[t] >= key)
            // && l != r - 1
            if (key < array[m]) {
                //key < array[m]
                //  && m == (l + r) / 2 && i : -1 < i < array.size() - 1 -> array[i] >= array[i + 1]
                //    && (j : -1 < j < l -> array[j] > key) && (t : r <= t < array.answer - > array[t] >= key)
                // && l != r - 1
                // ->
                // l < m < r && key < array[m] && (i : -1 < i < array.size() - 1 -> array[i] >= array[i + 1])
                return binarySearch2(key, array, m, r);
                // (i : m < i < ans -> array[i] > key) && (j : ans <= j < r -> array[j] <= key)
                // && key < array[m] && (t : -1 < t < array.length - 1 -> array[i] >= array[i + 1]
                // -> (l < i < ans -> array[i] > key) && (j : ans <= j < r -> array[j] <= key)
            } else {
                //key < array[m]
                //  && m == (l + r) / 2 && i : -1 < i < array.size() - 1 -> array[i] >= array[i + 1]
                //    && (j : -1 < j < l -> array[j] > key) && (t : r <= t < array.answer - > array[t] >= key)
                // && l != r - 1
                // ->
                // l < m < r && key >= array[m] && (i : -1 < i < array.size() - 1 -> array[i] >= array[i + 1])
                return binarySearch2(key, array, l, m);
                // (i : l < i < ans -> array[i] > key) && (j : ans <= j < m -> array[j] <= key)
                // && key >= array[m] && (t : -1 < t < array.length - 1 -> array[i] >= array[i + 1]
                // -> (l < i < ans -> array[i] > key) && (j : ans <= j < r -> array[j] <= key)
            }
            //так как l < r ->  l == r - 1 || l < m < r -> на какой-то итерации l == r - 1
            //
            //
        }
        // (i : -1 < i < array.size() - 1 -> array[i] >= array[i + 1]) && l < r && l == r - 1
        // -> (i : l < i < ans -> array[i] > key) && (j: key <= j < r -> array[i] <= key)
        // (так как выполняется для любого i из пустого множества)
        return r;
    }
}
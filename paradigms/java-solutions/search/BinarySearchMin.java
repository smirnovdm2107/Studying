package search;

public class BinarySearchMin {
    // pseudoMonotone(args[n]) == (exists x : (for i=1..x - 1: args[i - 1] > args[i])
    //  && (for i=(x + 1)..args.length - 1 : args[i - 1] < args[i])
    //
    // string_representation(args[n]) == for i in args.length : args[i] - string representation of a number
    //
    // parsed(args[n]) == (array == int[n] : for i=0..n - 1: array[i] = Integer.parseInt(args[i])
    //
    // Pred : string_representation(args)
    // && pseudoMonotone(parsed(args))
    // Post : R in console && R - the smallest number value in array;
    public static void main(String[] args) {
        // string_representation(args)
        // && monotony(parse(args))
        int[] array = new int[args.length];
        // string_representation(args)
        // && monotony(parse(args))
        for (int i = 0; i < args.length; i++) {
            // string_representation(args)
            // && monotony(parse(args))
            // && i < args.length;
            array[i] = Integer.parseInt(args[i]);
            // array == parsed(args)
            // && monotony(array)
        }
        //Pred: pseudoMonotony(array)
        //Post: R - the smallest number in array
        System.out.println(binarySearchMin1(array));
        // R - in console && R - the smallest number in array
        //
        //Pred: pseudoMonotone(array) && l >= 0 && r <= array.length
        //Post: R in console && R - the smallest number in [l, r) -> R - the smallest number in array
        //System.out.println(binarySearchMin2(array, 0, array.length));
        // R- in console && R - the smallest number in array
    }
    // Pred : pseudoMonotone(array)
    // Post : R == x && x - the smallest number in array
    private static int binarySearchMin1(int[] array) {
        // pseudoMonotone(array)
        int l = 0;
        // pseudoMonotone(array) && l == 0
        int r = array.length;
        // pseudoMonotone(array)
        // && l == 0 && r == array.length
        while(l != r - 1) {
            // exists x: for i : l' < i <= x: array[i - 1] > array[i]
            // && for j : x < j < r': array[i - i] < array[i] && ( l != r - 1  => l < r - 1
            int m = (l + r) / 2;
            // l < m < r &&
            // exists x: for i : l' < i <= x: array[i - 1] > array[i]
            // && for j : x < j < r': array[i - i] < array[i]
            if (array[m - 1] > array[m]) {
                // exists x: for i : m < i <= x: array[i - 1] > array[i]
                // && for j : x < j < r': array[i - i] < array[i]
                l = m;
                // exists x: for i : l' < i <= x: array[i - 1] > array[i]
                // && for j : x < j < r': array[i - i] < array[i]
            } else {
                // exists x: for i : l' < i <= x: array[i - 1] > array[i]
                // && for j : x < j < m: array[i - i] < array[i]
                r = m;
                // exists x: for i : l' < i <= x: array[i - 1] > array[i]
                // && for j : x < j < r': array[i - i] < array[i]
            }
            // exists x: for i : l' < i <= x: array[i - 1] > array[i]
            // && for j : x < j < r': array[i - i] < array[i]
        }
        // for i : 0 < i <= l : array[i - 1] > array[i]
        // && for j : l < j < r : array[i - 1] < array[i]
        // -> array[l] - the smallest value in array
        return array[l];
    }
    // Pred : pseudoMonotone(array) && 0 <= l && l < r && r <= array.length
    // Post : R == x && x - smallest number in array[l..r)
    private static int binarySearchMin2(int[] array, int l, int r) {
        // pseudoMonotone(array) && 0 <= l && l < r && r <= array.length
        if (l != r - 1) {
            // Pred : pseudoMonotone(array) && 0 <= l && l < r - 1 && r <= array.length
            int m = (l + r) / 2;
            // exists m: for i : r < i <= x: array[i - 1] > array[i]
            // && for j : x < j < r: array[i - i] < array[i] &&
            // l < m < r
            if (array[m - 1] > array[m]) {
                // exists x: for i : l < i <= x: array[i - 1] > array[i]
                // && for j : x < j < l: array[i - i] < array[i] &&
                // l < m < r && array[m - 1] > array[m] ->
                //    exists m: for i : m < i <= x: array[i - 1] > array[i]
                //    && for j : x < j < r: array[i - i] < array[i]
                return binarySearchMin2(array, m, r);
                // R == x && for i : m < i <= x: array[i - 1] > array[i]
                //  && for j : x < j < r: array[i - i] < array[i] &&
                // -> array[l] - the smallest value in [l, r)
            } else {
                // exists x: for i : l < i <= x: array[i - 1] > array[i]
                // && for j : x < j < l: array[i - i] < array[i] &&
                // l < m < r && array[m - 1] > array[m] ->
                //         exists m: for i : l < i <= x: array[i - 1] > array[i]
                //         && for j : x < j < m: array[i - i] < array[i]
                return binarySearchMin2(array, l, m);
                // R == x && for i : l < i <= x: array[i - 1] > array[i]
                //  && for j : x < j < m: array[i - i] < array[i]
                // -> array[l] - the smallest value in [l, r)
            }
            // l < m < r -> recursion ends sooner or later
        }
        // R == x && for i : l < i <= x: array[i - 1] > array[i]
        //  && for j : x < j < m: array[i - i] < array[i] && l == r - 1
        //(obviously, cause l == r - 1)
        // -> array[l] - the smallest value in [l, r)
        return array[l];
    }
}

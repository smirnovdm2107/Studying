import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Contest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        String text = in.next();
        int[] ps = new int[n];
        int sum = 0;
        for (int i = 0; i < text.length(); i++) {
            ps[text.charAt(i) - 'a']++;
            sum++;
        }
        Arrays.sort(ps);
        Pair[] segs = new Pair[ps.length];
        int last = 0;
        for (int i = 0; i < ps.length; i++) {
            Pair pair = new Pair();
            pair.x = last;
            pair.y = last + ps[i];
            segs[i] = pair;
        }
    }


    Segment Encoding(int[] ps, Pair[] segs, String text, int pos, int sum) {
        Segment s = new Segment();
        s.left = BigInteger.ZERO;
        s.rigth = BigInteger.ONE;

        for (int i = 0; i < text.length(); i++) {

            s.left.add((segs[i].y - segs[i].x));
        }
    }

}

class Segment {
    BigInteger rigth;
    BigInteger left;
    BigInteger den;
}
class Pair {
    int x;
    int y;
}

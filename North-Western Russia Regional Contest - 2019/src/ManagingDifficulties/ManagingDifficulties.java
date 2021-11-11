package ManagingDifficulties;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ManagingDifficulties {
    public static void main(String[] args) {

        try(SuperScanner in = new SuperScanner(
                new InputStreamReader(
                        System.in
                )
        )) {
            try {
                int t = in.nextInt();
                while (t > 0) {
                    int n = in.nextInt();
                    int[] diffs = new int[n];
                    for (int i = 0; i < n; i++) {
                        diffs[i] = in.nextInt();
                    }
                    Map<Integer, Integer> amounts = new HashMap<>();
                    int wayCount = 0;
                    for (int j = n - 1; j >= 1; j--) {
                        for (int i = 0; i < j; i++) {
                            int ak = 2 * diffs[j] - diffs[i];
                            if (amounts.containsKey(ak)) {
                                wayCount += amounts.get(ak);
                            }
                        }
                        if (amounts.containsKey(diffs[j])) {
                            amounts.put(diffs[j], amounts.get(diffs[j]) + 1);
                        } else {
                            amounts.put(diffs[j], 1);
                        }
                    }
                    System.out.println(wayCount);
                    t--;
                }
            }catch (IOException e) {
                System.out.println("Input data error: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Can't open Scanner: " + e.getMessage());
        }
    }
}

class SuperScanner implements Closeable {
    private Reader in;
    private char[] now =  new char[1024];
    private int iterNow = 0;
    private enum Type {
        WORD, SPACE, ENDLINE;

    }

    public SuperScanner(InputStreamReader input) throws IOException {
        in = input;
        in.read(now, 0, now.length);
    }

    public String next() throws IOException {
        char[] inputStr = new char[16];
        while (isNext(now[iterNow], Type.SPACE) || isNext(now[iterNow], Type.ENDLINE)) {
            iterNow++;
            if (iterNow == now.length) {
                refillNow();
            }
        }

        int iter = 0;
        while (true) {
            if (isNext(now[iterNow], Type.SPACE) || now[iterNow] == 0) {
                return String.valueOf(Arrays.copyOfRange(inputStr, 0, iter));
            }
            if (inputStr.length == iter) {
                inputStr = Arrays.copyOf(inputStr, inputStr.length*2);
            }
            inputStr[iter] = now[iterNow];
            iter++;
            iterNow++;
            if (iterNow == now.length) {
                refillNow();
            }
        }
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(next());
    }




    private void refillNow() throws IOException {
        int SymCounter = in.read(now, 0, now.length);
        if (SymCounter == -1) {
            SymCounter = 0;
        }
        for (int i = SymCounter; i < now.length; i++) {
            now[i] = 0;
        }
        iterNow = 0;
    }


    public boolean isNext(char now, Type type) {
        switch (type) {
            case WORD:
                return 	Character.isLetter(now) || Character.getType(now) == Character.DASH_PUNCTUATION || now == '\'';
            case ENDLINE:
                return  now == '\n' || now == '\r' || Character.getType(now) == Character.LINE_SEPARATOR;
            case SPACE:
                return Character.isWhitespace(now) || Character.getType(now) == Character.LINE_SEPARATOR;
            default:
                return false;
        }

    }

    public void close() throws IOException {
        in.close();
    }

}
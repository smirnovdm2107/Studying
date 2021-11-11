package DoublePolindrome;

import java.io.*;
import java.util.Arrays;

public class DoublePorlindrome {
    public static void main(String[] args) {

    try(
    SuperScanner in = new SuperScanner(
            new InputStreamReader(System.in)
    )) {
        try {
            final int mod = 998_244_353;
            int n = in.nextInt();
            int k = in.nextInt();
            int ways = 0;
            for (int i = 0; i < n; i++) {
                ways += modulePow(k, (i+1)/2, mod) * modulePow(k, (n - i + 1)/2, mod);
            }

            int[] extraWays = new int[n];




        } catch (IOException e) {
            System.out.println("Input data error" + e.getMessage());
        }
    }catch (IOException e) {
        System.out.println("Can't open Scanner:" + e.getMessage());
    }

    }

    public static int modulePow(int k, int l, final int mod) {
        if (l == 0) return 1;
        int next = modulePow(k, l / 2, mod);
        if (l % 2 == 1) return next * next * k;
        else return next * next;
    }

}

 class SuperScanner implements Closeable {
    private Reader in;
    private char[] now =  new char[1024];
    private int iterNow = 0;
    private enum Type {
        WORD, SPACE, ENDLINE;

    }


    public SuperScanner(String input) throws IOException {
        in = new StringReader(input);
        in.read(now, 0, now.length);

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

    public boolean hasNext() throws IOException {
        if (isNext(now[iterNow], Type.SPACE)) {
            while (isNext(now[iterNow], Type.SPACE)) {
                iterNow++;
                if (iterNow == now.length) {
                    refillNow();
                }
            }
        }
        return (int) now[iterNow] != 0;
    }

    public String nextWord() throws IOException {
        char[] wordNow = new char[16];
        int iter = 0;
        while (!isNext(now[iterNow], Type.WORD) && now[iterNow] != 0) {
            iterNow++;
            if (iterNow == now.length) {
                refillNow();
            }
        }

        while (isNext(now[iterNow], Type.WORD)) {
            wordNow[iter] = now[iterNow];
            iterNow++;
            if (iterNow == now.length) {
                refillNow();
            }
            iter++;
            if (iter == wordNow.length) {
                wordNow = Arrays.copyOf(wordNow, wordNow.length * 2);
            }
        }

        return String.valueOf(Arrays.copyOfRange(wordNow, 0 , iter));
    }

    public boolean hasNextWord() throws IOException {
        while (!isNext(now[iterNow], Type.WORD) && now[iterNow] != 0) {
            iterNow++;
            if (iterNow == now.length) {
                refillNow();
            }
        }
        return now[iterNow] != 0;
    }

    public boolean hasNextWordInLine()  throws IOException {
        while (!isNext(now[iterNow], Type.WORD) && now[iterNow] != 0 && !isNext(now[iterNow], Type.ENDLINE)) {
            iterNow++;
            if (iterNow == now.length) {
                refillNow();
            }
        }
        return isNext(now[iterNow], Type.WORD);

    }

    public int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    public int nextInt(int radix) throws IOException {
        return Integer.parseInt(next(), radix);
    }

    public boolean hasNextLine() throws IOException {
        return now[iterNow] != 0;
    }

    public String nextLine() throws IOException {
        char[] strNow = new char[16];
        int iter = 0;
        while (!isNext(now[iterNow], Type.ENDLINE) && now[iterNow] != 0) {
            if (iter == strNow.length) {
                strNow = Arrays.copyOf(strNow, strNow.length * 2);
            }

            strNow[iter] = now[iterNow];
            iter++;
            iterNow++;
            if (iterNow == now.length) {
                refillNow();
            }

        }

        if (isNext(now[iterNow], Type.ENDLINE)) {
            iterNow++;
            if (iterNow >= now.length) {
                refillNow();
            }
            if (isNext(now[iterNow], Type.ENDLINE)) {
                iterNow++;
                if (iterNow >= now.length) {
                    refillNow();
                }
            }
        }
        return String.valueOf(Arrays.copyOfRange(strNow, 0, iter));
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
                return  now == '\n' || now == '\r';
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


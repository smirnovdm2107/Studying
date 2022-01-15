

import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.io.Reader;
import java.io.StringReader;
public class SuperScanner{
    private final Reader in;
    private final char[] now =  new char[1024];
    private int iterNow = 0;

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
        while (isSpaceNext(now[iterNow]) || isLineEnd(now[iterNow])) {
            iterNow++;
            if (iterNow == now.length) {
                refillNow();
            }
        }

        int iter = 0;
        while (true) {
            if (isSpaceNext(now[iterNow]) || now[iterNow] == 0) {
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
        if (isSpaceNext(now[iterNow])) {
            while (isSpaceNext(now[iterNow])) {
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
        while (!isPartOfWord(now[iterNow]) && now[iterNow] != 0) {
            iterNow++;
            if (iterNow == now.length) {
                refillNow();
            }
        }

        while (isPartOfWord(now[iterNow])) {
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
        while (!isPartOfWord(now[iterNow]) && now[iterNow] != 0) {
            iterNow++;
            if (iterNow == now.length) {
                refillNow();
            }
        }
        return now[iterNow] != 0;
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    public int nextInt(int radix) throws IOException {
        return Integer.parseInt(next(), radix);
    }

    public boolean hasNextLine() {
        return now[iterNow] != 0;
    }

    public String nextLine() throws IOException {
        char[] strNow = new char[16];
        int iter = 0;
        while (!isLineEnd(now[iterNow]) && now[iterNow] != 0) {
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

        if (isLineEnd(now[iterNow])) {
            char ch = now[iterNow];
            iterNow++;
            if (iterNow >= now.length) {
                refillNow();
            }
            if (now[iterNow] == '\n' && ch == '\r') {
                iterNow++;
                if (iterNow >= now.length) {
                    refillNow();
                }
            }
        }
        return String.valueOf(Arrays.copyOfRange(strNow, 0, iter));
    }

    private boolean isSpaceNext (char now) {
        return Character.isWhitespace(now) || Character.getType(now) == Character.LINE_SEPARATOR;
    }

    private boolean isLineEnd(char now) {
        return now == '\u000b' || now == '\n' || now == '\r' || now == '\u0085'
                || now == '\u2028' || now == '\u2029' || now == '\u000c';
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

    private static boolean isPartOfWord(char a) {
        return 	Character.isLetter(a) || Character.getType(a) == Character.DASH_PUNCTUATION || a == '\'';
    }

    public void close() throws IOException {
        in.close();
    }

}
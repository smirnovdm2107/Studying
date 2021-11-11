package IdealPyramid;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;

public class IdealPyramid {
    public static void main(String[] args) {
        try(SuperScanner in = new SuperScanner(
              new InputStreamReader(System.in)
        )) {
            try {
                int n = in.nextInt();
                final int mMax = 1_000_000_000;
                int xl = mMax;
                int xr = -mMax;
                int yl = mMax;
                int yr = -mMax;

                for (int i = 0; i < n; i++) {
                    int x = in.nextInt();
                    int y = in.nextInt();
                    int h = in.nextInt();
                    xl = Math.min(xl, x - h);
                    xr = Math.max(xr, x + h);
                    yl = Math.min(yl, y - h);
                    yr = Math.max(yr, y + h);
                }

                int xAns = (xl + xr) / 2;
                int yAns = (yl + yr) / 2;
                int hAns = (Math.max(xr - xl, yr - yl) + 1) / 2;

                System.out.println(xAns + " " + yAns + " " + hAns);

            }catch (IOException e) {
                System.out.println("Input data error: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Can't open Scanner : " + e.getMessage() );
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
        while (isNext(now[iterNow], SuperScanner.Type.SPACE) || isNext(now[iterNow], SuperScanner.Type.ENDLINE)) {
            iterNow++;
            if (iterNow == now.length) {
                refillNow();
            }
        }

        int iter = 0;
        while (true) {
            if (isNext(now[iterNow], SuperScanner.Type.SPACE) || now[iterNow] == 0) {
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


    public boolean isNext(char now, SuperScanner.Type type) {
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
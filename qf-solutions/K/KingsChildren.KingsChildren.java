package KingsChildren;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;



public class KingsChildren {
    public static void main(String[] args) {

        try(SuperScanner in = new SuperScanner(
                new InputStreamReader(System.in)
        )) {
            int n = in.nextInt();
            int m = in.nextInt();
            char[][] cells = new char[n][m];

            int ax = 0;
            int ay = 0;
            int[][] hs = new int[n][m];
            ArrayList<Pair> castles = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    cells[i][j] = in.nextChar();
                    if (cells[i][j] == 'A') {
                        ax = i;
                        ay = j;
                        cells[i][j] = '.';
                        hs[i][j] = -1;
                    } else if (cells[i][j] != '.') {
                        Pair crd = new Pair(i, j);
                        castles.add(crd);
                        hs[i][j] = i;
                    } else {
                        hs[i][j] = -1;
                    }
                }
            }


            for (int i = 1; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (cells[i][j] == '.') {
                        hs[i][j] = hs[i-1][j];
                    }
                }
            }

           maxFill(cells, hs, ax, ay, n, m);

            for (int i = 0; i < castles.size(); i++) {
                cellFill(cells, castles.get(i).getX(), castles.get(i).getY(), n, m);
            }
            PrintWriter out = new PrintWriter(System.out);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    out.print(cells[i][j]);
                }
                out.println();
            }
            out.close();
        }catch(IOException e) {
            System.out.println("Can't open Scanner:" + e.getMessage());
        }
    }
    public static void cellFill(char[][] cells, int x0, int y0, int n, int m) {
        char c0 = Character.toLowerCase(cells[x0][y0]);

        int y1 = y0 - 1;
        while (y1 >= 0) {
            if (cells[x0][y1] != '.') {
                break;
            }
            cells[x0][y1] = c0;
            y1--;
        }

        y1++;
        int y2 = y0 + 1;

        while(y2 < m) {
            if (cells[x0][y2] != '.') {
                break;
            }
            cells[x0][y2] = c0;
            y2++;
        }
        y2--;

        //checking next column
        int x1 = x0 + 1;

    mainLoop:
        while (x1 < n) {
            for (int i = y1; i <= y2; i++) {
                if (cells[x1][i] != '.'){
                    break mainLoop;
                }
            }
            for (int i = y1; i <= y2; i++) {
                cells[x1][i] = c0;
            }
            x1++;
        }

        int x2 = x0 - 1;
        mainLoop:
        while (x2 >= 0) {
            for (int i = y1; i <= y2; i++) {
                if (cells[x2][i] != '.'){
                    break mainLoop;
                }
            }
            for (int i = y1; i <= y2; i++) {
                cells[x2][i] = c0;
            }
            x2--;
        }

    }

    public static void maxFill(char[][] cells, int[][] hs,  int x0, int y0, int n, int m) {
        int maxS = 0;
        int rx = x0 + 1;
        int ry = y0 + 1;
        int lx = x0 - 1;
        int ly = y0 - 1;

        int[] ls = new int[m];
        int[] rs = new int[m];
        Stack <Integer> bounds = new Stack<>();
        for (int i = 0; i < n; i++) {

            for (int j = 0; j < m; j++) {
                while(!bounds.isEmpty() && hs[i][bounds.peek()] <= hs[i][j]) {
                    bounds.pop();
                }

                if (bounds.isEmpty()) {
                    ls[j] = -1;
                } else {
                    ls[j] = bounds.peek();
                }
                bounds.push(j);
            }

            while(!bounds.isEmpty()) {
                bounds.pop();
            }

            for (int j = m - 1; j >= 0; j--) {
                while (!bounds.isEmpty() && hs[i][bounds.peek()] <= hs[i][j]) {
                    bounds.pop();
                }

                if (bounds.isEmpty()) {
                    rs[j] = m;
                } else {
                    rs[j] = bounds.peek();
                }
                bounds.push(j);
            }

            while(!bounds.isEmpty()) {
                bounds.pop();
            }


            for (int j = 0; j < m; j++) {
                if (i >= x0 && x0 > hs[i][j] && ls[j] < y0 && y0 < rs[j] ) {
                    int sNow = (i - hs[i][j]) * (rs[j] - ls[j] - 1);
                    if (sNow > maxS) {
                        maxS = sNow;
                        lx = hs[i][j];
                        ly = ls[j];
                        rx = i + 1;
                        ry = rs[j];

                    }
                }
            }

        }

        for (int i = lx + 1; i < rx; i++) {
            for (int j = ly + 1; j < ry; j++) {

                cells[i][j] = 'a';

            }
        }
        cells[x0][y0] = 'A';


    }

}

class Pair{
    private int x;
    private int y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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

    public char nextChar() throws IOException {
        hasNext();
        if (iterNow == now.length) {
            refillNow();
        }
        return now[iterNow++];
    }

    public boolean hasNext() throws IOException {
        if (iterNow == now.length) {
            refillNow();
        }
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
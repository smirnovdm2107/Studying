import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class Wspp {
    public static void main(String[] args) {
        Map<String, IntList> ws = new LinkedHashMap<>();
        try {
            SuperScanner in = new SuperScanner(new InputStreamReader(
                    new FileInputStream(args[0]),
                    StandardCharsets.UTF_8
            ));
            try {
                int count = 1;
                while (in.hasNextWord()) {
                    String word = in.nextWord().toLowerCase(Locale.ROOT);
                    if (ws.containsKey(word)) {
                        ws.get(word).add(count);
                    } else {
                        IntList intList = new IntList();
                        intList.add(count);
                        ws.put(word, intList);
                    }

                    count++;
                }
            } catch (IOException e) {
                System.out.println("Input data problem:" + e.getMessage());
            } finally {
                in.close();
            }
        } catch (IOException e) {
            System.out.println("Can't find file: " + e.getMessage());
        }
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(args[1]),
                    StandardCharsets.UTF_8
            ));
            try {
                for (Map.Entry<String, IntList> entry : ws.entrySet()) {
                    out.write(entry.getKey() + " " + entry.getValue().size());
                    for (int i = 0; i < entry.getValue().size(); i++) {
                        out.write(" " + entry.getValue().get(i));
                    }
                    out.newLine();
                }
            } catch (IOException e) {
                System.out.println("Output data error: " + e.getMessage());
            } finally {
                out.close();
            }
        } catch (IOException e) {
            System.out.println("Output stream problem: " + e.getMessage());
        }
    }
}

import ms.SuperScanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WordFinder {
    TreeMap <String, ArrayList<String>> wats = new TreeMap<>();
    public WordFinder(String path) {
        try {
            SuperScanner in = new SuperScanner(
                    new InputStreamReader(
                            new FileInputStream(path),
                            StandardCharsets.UTF_8
                    )
            );
            try {
                while(in.hasNext()) {
                    String word = in.next();
                    ArrayList <String> trans = new ArrayList<>();
                    while (in.hasNextWordInLine()) {
                        trans.add(in.next());
                    }
                    wats.put(word, trans);
                }
            } finally {
                in.close();
            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }






}

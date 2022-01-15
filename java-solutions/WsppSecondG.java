import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WsppSecondG {

    public static void main(String[] args) {
        Map<String, IntList> wordStat = new LinkedHashMap<>();
        try {
            SuperScanner in = new SuperScanner(
                    new InputStreamReader(
                            new FileInputStream(args[0]),
                            StandardCharsets.UTF_8
                    )
            );
            try {
                int allCount = 1;
                while (in.hasNextLine()) {
                    try {
                        SuperScanner subIn = new SuperScanner(
                                in.nextLine()
                        );
                        int count = 0;
                        Collection<String> strStat = new HashSet<>();
                        while (subIn.hasNextWord()) {
                            String wordNow = subIn.nextWord().toLowerCase();
                            count++;
                            int t_count = allCount;
                            if (!strStat.contains(wordNow)) {
                                t_count = -1;
                                strStat.add(wordNow);
                            } else {
                                strStat.remove(wordNow);
                            }
                            if (wordStat.containsKey(wordNow)) {
                                wordStat.get(wordNow).add(t_count);
                            } else {
                                IntList ilist = new IntList();
                                ilist.add(t_count);
                                wordStat.put(wordNow, ilist);
                            }
                            allCount++;
                        }
                        subIn.close();
                    } catch (IOException e) {
                        System.out.println("Scanner is closed:" + e.getMessage());
                    }
                }
            } finally {
                in.close();
            }
        }catch (FileNotFoundException e) {
            System.out.println("File is not found:" +  e.getMessage());
            return;
        } catch (IOException e) {
            System.out.println("Can't open file:" + e.getMessage());
            return;
        }

        try {
            BufferedWriter out = new BufferedWriter(
                  new OutputStreamWriter(
                            new FileOutputStream(args[1]),
                          StandardCharsets.UTF_8
                    )
            );
            try {
                for (Map.Entry<String, IntList> entry : wordStat.entrySet()) {
                    out.write(entry.getKey() + " " + entry.getValue().size());
                    int arrSize = entry.getValue().size();
                    for (int i = 0; i < arrSize - 1; i++) {
                        if (entry.getValue().get(i) != -1) {
                            out.write(" " + entry.getValue().get(i));
                        }
                    }
                    if (entry.getValue().get(arrSize -1) != -1) {
                        out.write(" " + entry.getValue().get(arrSize - 1));
                    }
                    out.newLine();
                }

            } catch(IOException e) {
                System.out.println("Can't write file:" + e.getMessage());
            } finally {
                out.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File for writing is not found:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Can't write file:" + e.getMessage());
        }
    }
}

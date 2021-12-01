package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class Md2Html {

    public static void main(String[] args) {
        ArrayList<String> text = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(args[0]),
                        StandardCharsets.UTF_8
                )
            )) {

            try {

                String strNow = in.readLine();
                while (strNow != null) {
                    text.add(strNow);
                    strNow = in.readLine();
                }
            } catch (IOException e) {
                System.out.println("Input data error: " + e.getMessage());
                return;
            }
        } catch (IOException e) {
            System.out.println("Can't open file: " + e.getMessage());
            return;
        }
        try(BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(args[1]),
                        StandardCharsets.UTF_8
                )
        )) {
            try {

                HashMap<String, String> htmlOpenTable = new HashMap<>();
                htmlOpenTable.put("*", "<em>");
                htmlOpenTable.put("_", "<em>");
                htmlOpenTable.put("**", "<strong>");
                htmlOpenTable.put("__", "<strong>");
                htmlOpenTable.put("--", "<s>");
                htmlOpenTable.put("`", "<code>");


                HashMap<String, String> htmlCloseTable = new HashMap<>();
                htmlCloseTable.put("*", "</em>");
                htmlCloseTable.put("_", "</em>");
                htmlCloseTable.put("**", "</strong>");
                htmlCloseTable.put("__", "</strong>");
                htmlCloseTable.put("--", "</s>");
                htmlCloseTable.put("`", "</code>");

                HashMap<String, Boolean> markupFlags = new HashMap<>();
                markupFlags.put("*", false);
                markupFlags.put("_", false);
                markupFlags.put("**", false);
                markupFlags.put("__", false);
                markupFlags.put("--", false);
                markupFlags.put("`", false);

                String parOpen = "<p>";
                String parClose = "</p>";
                boolean isNewPar = true;

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < text.size(); i++) {
                    if (text.get(i).length() == 0) {
                        if (!isNewPar) {
                           out.write(parClose);
                           out.newLine();
                       }
                       isNewPar = true;
                       continue;
                    }

                    int iter = 0;
                    if(isNewPar) {
                        int headLevel = 0;
                        StringBuilder prefix = new StringBuilder();
                        while(iter < text.get(i).length() && text.get(i).charAt(iter) == '#') {
                            headLevel++;
                            iter++;
                            prefix.append('#');
                        }
                        parOpen = "<p>";
                        parClose = "</p>";
                        if (headLevel != 0) {
                            if (iter < text.get(i).length() && text.get(i).charAt(iter) == ' ') {
                                parOpen = "<h" + headLevel + ">";
                                parClose = "</h" + headLevel + ">";
                                iter++;
                                prefix = new StringBuilder();
                            }
                        }
                        sb.append(parOpen);
                        sb.append(prefix);
                        isNewPar = false;
                    } else {
                       out.newLine();
                    }

                    for (int j = iter; j < text.get(i).length(); j++) {

                        String token = String.valueOf(text.get(i).charAt(j));
                        switchLoop:
                        switch(text.get(i).charAt(j)) {
                            case '-':
                                if (j == text.get(i).length() - 1 || text.get(i).charAt(j+1) != '-'){
                                    sb.append(token);
                                    break;
                                }
                            case '*':
                            case '_':
                            case '`':
                                if (j < text.get(i).length() - 1 && text.get(i).charAt(j+1) == text.get(i).charAt(j) && token != "`") {
                                    token = token + token;
                                    j++;
                                }
                                if (markupFlags.get(token)) {
                                    markupFlags.put(token, false);
                                    sb.append(htmlCloseTable.get(token));
                                    break;
                                }
                                if (j == text.get(i).length() - 1) {
                                    sb.append(token);
                                    break;
                                }



                                for (int k = j+1; k < text.get(i).length(); k++) {
                                    if (text.get(i).startsWith(token, k) && text.get(i).charAt(k - 1) != '\\') {
                                        sb.append(htmlOpenTable.get(token));
                                        markupFlags.put(token, true);
                                        break switchLoop;
                                    }
                                }

                                for (int t = i + 1; t < text.size(); t++) {
                                    if (text.get(t).length() == 0) {
                                        break;
                                    }
                                    for (int k = 0; k < text.get(t).length(); k++) {
                                        if (text.get(t).startsWith(token, k) && text.get(t).charAt(k - 1) != '\\') {
                                            sb.append(htmlOpenTable.get(token));
                                            markupFlags.put(token, true);
                                            break switchLoop;
                                        }
                                    }
                                }

                                sb.append(token);
                                break;
                            case '\\':
                                if (j == text.get(i).length() - 1) {
                                    sb.append(text.get(i).charAt(j));
                                    break;
                                }
                                if (text.get(i).charAt(j+1) == '*' || text.get(i).charAt(j+1) == '_') {
                                    sb.append(text.get(i).charAt(++j));
                                } else {
                                    sb.append("\\");
                                }
                                break;
                            case '<':
                                sb.append("&lt;");
                                break;
                            case '>':
                                sb.append("&gt;");
                                break;
                            case '&':
                                sb.append("&amp;");
                                break;
                            default:
                                sb.append(text.get(i).charAt(j));
                        }

                    }
                    out.write(sb.toString());
                    sb = new StringBuilder();
                }
                if (text.get(text.size()-1).length() != 0) {
                    out.write(parClose);
                }
            } catch (IOException e) {
                System.out.println("Output data error: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Can't open file: " + e.getMessage());
        }




    }











}





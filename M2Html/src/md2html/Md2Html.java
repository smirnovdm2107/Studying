package md2html;

import markup.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class Md2Html {

    public static void main(String[] args) {
        ArrayList<Itemable> parts = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(args[0]),
                        StandardCharsets.UTF_8
                )
        )) {

            try {

                String text = in.readLine();
                StringBuilder sb = new StringBuilder();
                int level = 0;
                boolean isPar = false;
                boolean isHead = false;
                boolean isNewPar = true;
                while (text != null) {
                    if (text.length() == 0) {
                        if (sb.toString().endsWith("\n")) {
                            sb.deleteCharAt(sb.toString().length() - 1);
                        }
                        if (isPar) {
                            parts.add(new Paragraph(markParse(sb.toString())));
                            isPar = false;
                        }
                        if (isHead) {
                            parts.add(new Head(markParse(sb.toString()), level));
                            isHead = false;
                        }
                        level = 0;
                        sb = new StringBuilder();
                        isNewPar = true;
                        while (text.length() == 0) {
                            text = in.readLine();
                        }
                    }
                    if (isNewPar) {
                        int iter = 0;
                        while (iter < text.length() && text.charAt(iter) == '#') {
                            level++;
                            iter++;
                        }
                        if (level > 0 && iter < text.length() && text.charAt(iter) == ' ') {
                            isHead = true;
                            text = text.substring(level + 1);
                        } else {
                            isPar = true;
                        }
                        isNewPar = false;
                    }
                    sb.append(text);
                    sb.append('\n');
                    text = in.readLine();

                }
                String str = sb.toString();
                if (str.endsWith("\n")) {
                    str = str.substring(0, str.length() - 1);
                }
                if (isPar) {
                    parts.add(new Paragraph(markParse(str)));
                }
                if (isHead) {
                    parts.add(new Head(markParse(str), level));
                }
            } catch (IOException e) {
                System.out.println("Input data error: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Can't open file: " + e.getMessage());
        }
        try (BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(args[1]),
                        StandardCharsets.UTF_8
                )
        )) {
            try {
                StringBuilder sb = new StringBuilder();
                for (Itemable part : parts) {
                    part.toHtml(sb);
                    out.write(sb.toString());
                    out.newLine();
                    sb = new StringBuilder();
                }


            } catch (IOException e) {
                System.out.println("Output data error: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Can't open write file: " + e.getMessage());
        }

    }

    public static ArrayList<Texts> markParse(String text) {
        ArrayList<Texts> texts = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            String token = String.valueOf(text.charAt(i));
            switch (token) {
                case "*":
                case "_":
                    if (i < text.length() - 1 && String.valueOf(text.charAt(i + 1)).equals(token)) {
                        token = token + token;
                        i++;
                    }

                    i = tokenChecker(token, text, texts, sb, i);
                    break;
                case "`":
                    if (text.startsWith("```", i)) {
                        token += token + token;
                        i += 2;
                    }
                    i = tokenChecker(token, text, texts, sb, i);
                    break;
                case "-":
                    if (i == text.length() - 1 || text.charAt(i + 1) != '-') {
                        sb.append(token);
                        break;
                    }
                    token = token + token;
                    i++;
                    i = tokenChecker(token, text, texts, sb, i);
                    break;
                case "\\":
                    if (i == text.length() - 1 || !charChecker(text.charAt(i + 1))) {
                        sb.append(token);
                    } else {
                        sb.append(text.charAt(i + 1));
                        i++;
                    }
                    break;
                case "<":
                    sb.append("&lt;");
                    break;
                case ">":
                    sb.append("&gt;");
                    break;
                case "&":
                    sb.append("&amp;");
                    break;
                default:
                    sb.append(token);
            }

        }
        texts.add(new Text(sb.toString()));
        return texts;
    }

    public static boolean charChecker(char ch) {
        if (ch != '*' && ch != '_' && ch != '`') {
            return false;
        } else {
            return true;
        }
    }

    public static int tokenChecker(String token, String text, ArrayList<Texts> texts, StringBuilder sb, int start) {
        for (int j = start + 1; j < text.length(); j++) {
            if (text.startsWith(token, j) && text.charAt(j - 1) != '\\') {
                if (j < text.length() - token.length() && text.charAt(j + token.length()) == token.charAt(0)) {
                    while(j < text.length() && text.startsWith(token, j)) {
                        j++;

                    }
                    continue;
                }

                texts.add(new Text(sb.toString()));
                sb.setLength(0);
                markupSwitch(token, texts, text, start + 1, j);
                return j + token.length() - 1;

            }
        }
        sb.append(token);
        return start;
    }

    public static void markupSwitch(String token, ArrayList<Texts> texts, String text, int start, int length) {
        String substr = text.substring(start, length);
        switch (token) {
            case "_":
            case "*":
                texts.add(new Emphasis(markParse(substr)));
                break;
            case "--":
                texts.add(new Strikeout(markParse(substr)));
                break;
            case "__":
            case "**":
                texts.add(new Strong(markParse(substr)));
                break;
            case "`":
                texts.add(new Code(markParse(substr)));
                break;
            case "```":
                texts.add(new Pre(substr));
        }
    }
}





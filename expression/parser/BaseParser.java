package expression.parser;

public class BaseParser {
    protected static final char END = '\0';
    private final CharSource sourse;
    private char ch;
    public BaseParser(CharSource sourse) {
        this.sourse = sourse;
        ch = sourse.next();
    }



    protected boolean test(char expected) {
        return ch == expected;
    }

    protected char take(){
        final char result = ch;
        ch = sourse.hasNext() ? sourse.next() : 0;
        return result;
    }

    protected boolean take(char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected void expect(final String expected) {
        for (int i = 0; i < expected.length(); i++) {
            expect(expected.charAt(i));
        }
    }

    protected void expect(final char expected){
        if (!take(expected)) {
            throw new IllegalArgumentException(String.format(
                    "Expected '%s', found '%s'", expected, ch));
        }
    }

    protected int getPos() {
        return sourse.getPos();
    }

    protected boolean end() {
        return test(END);
    }

    public boolean isWhiteSpace() {
        return Character.isWhitespace(ch);
    }

    public IllegalArgumentException error(String message) {
        return sourse.error(message);
    }

    protected  boolean between(char min, char max) {
        return min <= ch && ch <= max;
    }
}
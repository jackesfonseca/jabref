package org.jabref.logic.util.strings;

/**
 * A String tokenizer that works just like StringTokenizer, but considers quoted
 * characters (which do not act as delimiters).
 */
public class QuotedStringTokenizer {

    private final String content;
    private final int contentLength;
    private final String delimiters;
    private final char quoteChar;
    private int index;

    /**
     * @param content        The String to be tokenized.
     * @param delimiters     The delimiter characters.
     * @param quoteCharacter The quoting character. Every character (including, but not limited to, delimiters) that is preceded by this character is not treated as a delimiter, but as a token component.
     */
    public QuotedStringTokenizer(String content, String delimiters, char quoteCharacter) {
        this.content = content;
        this.delimiters = delimiters;
        quoteChar = quoteCharacter;
        contentLength = this.content.length();
        // skip leading delimiters
        while (index < contentLength && isDelimiter(this.content.charAt(index))) {
            ++index;
        }
    }

    /**
     * @return the next token from the content string, ending at the next
     * unquoted delimiter. Does not unquote the string itself.
     */
    public String nextToken() {
        char c;
        StringBuilder stringBuilder = new StringBuilder();
        while (index < contentLength) {
            c = content.charAt(index);
            if (c == quoteChar) { // next is quoted
                ++index;
                stringBuilder.append(c);
                if (index < contentLength) {
                    stringBuilder.append(content.charAt(index));
                    // ignore for delimiter search!
                }
            } else if (isDelimiter(c)) { // unit finished
                // advance index until next token or end
                ++index;
                return stringBuilder.toString();
            } else {
                stringBuilder.append(c);
            }
            ++index;
        }
        return stringBuilder.toString();
    }

    private boolean isDelimiter(char c) {
        return delimiters.indexOf(c) >= 0;
    }

    public boolean hasMoreTokens() {
        return index < contentLength;
    }
}

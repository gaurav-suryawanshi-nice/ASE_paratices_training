package org.nice.ase.tdd.wordwrap;

public class WordWrapper {

    public static final char WHITE_SPACE = ' ';
    public static final String NEW_LINE = "\n";

    public static String wrap(String input, int width) {
        if (input.length() <= width) {
            return input;
        }
        int pos = 0;
        StringBuilder accum = new StringBuilder();
        while (pos < input.length() - width) {
            int whiteSpace = input.lastIndexOf(WHITE_SPACE, pos + width);
            int splitAt = 0;
            int offset = 0;
            if (whiteSpace > pos) {
                splitAt = whiteSpace;
                offset = 1;
            } else {
                splitAt = pos + width;
                offset = 0;
            }
            accum.append(input.substring(pos, splitAt));
            accum.append(NEW_LINE);
            pos = splitAt + offset;
        }
        accum.append(input.substring(pos));
        return accum.toString();
    }
}

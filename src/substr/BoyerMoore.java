package substr;

import org.junit.Test;

public class BoyerMoore {
    private static final int R = 256;
    int[] right = new int[R];
    String pattern = "";

    public BoyerMoore() {
    }

    private BoyerMoore compile(String pattern) {
        right = new int[R];
        this.pattern = pattern;
        for (int c = 0; c < R; c++)
            right[c] = -1;
        for (int j = 0; j < pattern.length(); j++)
            right[pattern.charAt(j)] = j;
        return this;
    }

    public int search(String text) {
        int n = text.length();
        int m = pattern.length();
        int skip;
        for (int i = 0; i <= n - m; i += skip) {
            skip = 0;
            for (int j = m - 1; j >= 0; j--) {
                if (pattern.charAt(j) != text.charAt(i + j)) {
                    skip = Math.max(1, j - right[text.charAt(i + j)]);
                    break;
                }
            }
            if (skip == 0) return i;
        }
        return n;
    }

    @Test
    public void testSearch() {
        int lo = new BoyerMoore().compile("lo").search("1, Hello, world!");
        assert lo == 6;
    }

}
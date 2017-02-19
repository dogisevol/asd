package sort.radix;

import java.util.Arrays;

public class LongestRepeatedString {
    public static void main(String[] args) {
        LongestRepeatedString lrs = new LongestRepeatedString();
        System.out.println(lrs.find("abcdefjabcdefjabdefjabbsdfsfsdgdgdgsdsd"));
    }

    private String find(String str) {
        if (str == null) {
            return null;
        }
        String[] suffixes = new String[str.length()];
        for (int i = 0; i < str.length(); i++) {
            suffixes[i] = str.substring(i);
        }
        Arrays.sort(suffixes);

        String result = "";

        for (int i = 0; i < suffixes.length - 1; i++) {
            int length = 0;
            String lcpStr = suffixes[i].length() < suffixes[i + 1].length() ? suffixes[i] : suffixes[i + 1];
            for (; length < lcpStr.length() - 1; length++) {
                System.out.println(i);
                if (suffixes[i].charAt(length) != suffixes[i + 1].charAt(length)) {
                    break;
                }
            }

            if (length > result.length()) {
                result = suffixes[i].substring(0, length);
            }
        }

        return result;
    }
}

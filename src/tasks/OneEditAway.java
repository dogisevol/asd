package tasks;

import org.junit.Test;

/**
 * One Away: There are three types of edits that can be performed on strings: insert a character,
 * remove a character, or replace a character. Given two strings, write a function to check if they are
 * one edit (or zero edits) away.
 * EXAMPLE
 * pale, ple - true
 * pales, pale - true
 * pale, bale - true
 * pale, bae - false
 */
public class OneEditAway {

    public boolean check1(String s1, String s2) {
        if (s1 == null || s2 == null)
            return false;
        int offset = s1.length() - s2.length();
        if (offset > 1 || offset < -1) return false;
        if (offset > 0) {
            String temp = s1;
            s1 = s2;
            s2 = temp;
        }
        offset = Math.abs(offset);

        int diffs = 0;
        for (int i = 0; i < s1.length(); i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(diffs == 0 ? i : i + offset);
            if (c1 != c2) {
                i = i - offset;
                if (++diffs > 1)
                    return false;
            }
        }
        if (diffs == 0) {
            diffs += offset;
        }
        return diffs == 1;
    }

    boolean check(String first, String second) {
        if (Math.abs(first.length() - second.length()) > 1) {
            return false;
        }
        String s1 = first.length() < second.length() ? first : second;
        String s2 = first.length() < second.length() ? second : first;
        int index1 = 0;
        int index2 = 0;
        boolean foundDifference = false;
        while (index2 < s2.length() && index1 < s1.length()) {
            if (s1.charAt(index1) != s2.charAt(index2)) {
                if (foundDifference) return false;
                foundDifference = true;
                if (s1.length() == s2.length()) {
                    index1++;
                }
            } else {
                index1++;
            }
            index2++;
        }
        return true;
    }


    @Test
    public void testInsertCheck() {
        OneEditAway oea = new OneEditAway();
        assert oea.check("pale", "ple");
    }

    @Test
    public void testReplaceCheck() {
        OneEditAway oea = new OneEditAway();
        assert oea.check("pale", "bale");
    }

    @Test
    public void testDeleteCheck() {
        OneEditAway oea = new OneEditAway();
        assert oea.check("pale", "pales");
    }

    @Test
    public void testTwoEditsCheck() {
        OneEditAway oea = new OneEditAway();
        assert !oea.check("pale", "bae");
    }

}

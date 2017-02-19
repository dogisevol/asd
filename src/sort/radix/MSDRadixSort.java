package sort;

/*
is cache-inefficient
Too much memory storing count[]
Too much overhead reinitializing count[] and aux[]
 */

public class MSDRadixSort {

    private static final int R = 1024;

    public static void main(String[] args) {
        MSDRadixSort sort = new MSDRadixSort();
        String[] strings = {"test", "est", "st", "este", "estt", "stet", "test"};
        sort.sort(strings);
        for (String s : strings) {
            System.out.println(s);
        }
    }

    public void sort(String[] strings) {
        String[] aux = new String[strings.length];
        sort(strings, aux, 0, strings.length - 1, 0);
    }

    public void sort(String[] strings, String[] aux, int low, int high, int d) {
        if (low > high) {
            return;
        }
        int[] count = new int[R + 2];//TODO
        for (int i = low; i <= high; i++)
            count[charAt(strings[i], d) + 2]++;
        for (int r = 0; r < R + 1; r++)
            count[r + 1] += count[r];
        for (int i = low; i <= high; i++) {
            aux[count[charAt(strings[i], d) + 1]++] = strings[i];
        }
        for (int i = low; i <= high; i++)
            strings[i] = aux[i - low];

        for (int r = 0; r < R; r++) {
            sort(strings, aux, low + count[r], low + count[r + 1] - 1, d + 1);
        }
    }

    public int charAt(String s, int d) {
        if (d < s.length()) {
            return s.charAt(d);
        } else {
            return -1;
        }
    }
}

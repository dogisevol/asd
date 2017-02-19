package sort.radix;

/*
Comparing to standards quicksort

1) Uses ~2NlnN CHARACTER compares on average (while standard quicksort uses ~2NlnN STRING compares)
2) Avoid re-comparing long common prefixes (standards quicksort is costly for keys with long common prefixes
and this is a common case)

Comparing to MSD string sort
1) Has a short inner loop
2) Is cache-friendly
3) Is in-place

 */
public class ThreeWayQuickStringSort extends MSDRadixSort {
    public static void main(String[] args) {
        ThreeWayQuickStringSort sort = new ThreeWayQuickStringSort();
        String[] strings = {"test", "est", "st", "este", "estt", "stet", "test"};
        sort.sort(strings);
        for (String s : strings) {
            System.out.println(s);
        }
    }

    public void sort(String[] strings) {
        sort(strings, 0, strings.length - 1, 0);

    }

    private void sort(String[] strings, int low, int high, int d) {
        if (low > high) return;
        int lt = low, gt = high;
        int v = charAt(strings[low], d);
        int i = low + 1;
        while (i <= gt) {
            int t = charAt(strings[i], d);
            String temp;
            if (t < v) {
                exchange(strings, lt++, i++);
            } else if (t > v) {
                exchange(strings, i, gt--);
            } else {
                i++;
            }
        }
        sort(strings, low, lt - 1, d);
        if (v >= 0)
            sort(strings, lt, gt, d + 1);
        sort(strings, gt + 1, high, d);
    }

    private void exchange(String[] strings, int i, int j) {
        String temp = strings[i];
        strings[i] = strings[j];
        strings[j] = temp;
    }
}

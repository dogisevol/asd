package sort.count;

import java.util.Arrays;

public class CountSort {

    public static void main(String[] args) {
        CountSort countSort = new CountSort();
        int[] arr = new int[]{6, 5, 4, 3, 2, 1, 0, 6};
        Arrays.stream(countSort.countingSort(arr)).forEach(i -> System.out.println(Integer.toUnsignedString(i, 16)));

        //TODO
//        arr = new int[]{109, 108, 107, 106, 10, 9};
//        Arrays.stream(countSort.radixSort(arr, 3)).forEach(i -> System.out.println(Integer.toUnsignedString(i, 16)));

    }

//    int[] radixSort(int[] a, int d) {
//        int[] b = null;
//        for (int p = 0; p < d; p++) {
//            int c[] = new int[1 << d];
//            // the next three for loops implement counting-sort
//            b = countingSort(a);
//            a = b;
//        }
//        return b;
//    }


    private int[] countingSort(int[] a) {
        int k = Integer.MIN_VALUE;
        int[] b = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            if (a[i] > k) {
                k = a[i];
            }
        }
        k++;
        int[] c = new int[k];
        for (int i = 0; i < k; i++) {
            c[i] = 0;
        }

        for (int i = 0; i < a.length; i++)
            c[a[i]]++;
        for (int i = 1; i < k; i++)
            c[i] += c[i - 1];
        for (int i = a.length - 1; i >= 0; i--)
            b[--c[a[i]]] = a[i];
        return b;
    }
}

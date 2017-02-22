package sort.quicksort;

import org.testng.annotations.Test;

public class QuickSort {
    public void sort(int[] array) {
        sort(array, 0, array.length - 1);
    }

    private void sort(int[] array, int low, int high) {
        if (low < high) {
            int p = partition(array, low, high);
            sort(array, low, p - 1);
            sort(array, p + 1, high);
        }
    }

    private int partition(int[] array, int low, int high) {
        //TODO median
        int pivot = array[high];
        int result = low;
        int temp;
        for (int i = low; i < high; i++) {
            if (array[i] <= pivot) {
                temp = array[result];
                array[result] = array[i];
                array[i] = temp;
                result++;
            }
        }
        temp = array[result];
        array[result] = array[high];
        array[high] = temp;

        return result;
    }

    @Test
    public void testSort() {
        int[] array = new int[]{3, 8, 2, 4, 5, 0, 9, 1, 6, 7};
        new QuickSort().sort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

}

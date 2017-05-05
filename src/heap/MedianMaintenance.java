package heap;


import java.io.*;
import java.util.*;

public class MedianMaintenance {
    private PriorityQueue<Integer> minHeap;
    private PriorityQueue<Integer> maxHeap;
    private int size;

    public MedianMaintenance() {
        minHeap = new PriorityQueue<Integer>(5000);
        maxHeap = new PriorityQueue<Integer>(5000, Collections.reverseOrder());
        size = 0;
    }

    public void add(int n) {
        if (size == 0) {
            maxHeap.add(n);
        } else if (size % 2 == 0) {
            if (n > minHeap.peek()) {
                maxHeap.add(minHeap.poll());
                minHeap.add(n);
            } else {
                maxHeap.add(n);
            }

        } else {
            if (n < maxHeap.peek()) {
                minHeap.add(maxHeap.poll());
                maxHeap.add(n);
            } else {
                minHeap.add(n);
            }
        }
        size++;
    }

    public int median() {
        return maxHeap.peek();
    }

    public static void main(String[] args) throws FileNotFoundException {
        int sum = 0;
        MedianMaintenance m = new MedianMaintenance();
        Scanner in = new Scanner(new File("src/heap/input"));
        while (in.hasNextInt()) {
            int i = in.nextInt();
            m.add(i);

            sum = sum + m.median();
            System.out.println(sum);
            Object[] arr = m.maxHeap.toArray();
            Arrays.sort(arr);
//            System.out.println(Arrays.asList(arr));
        }
        System.out.println(sum % 10000);
    }
}
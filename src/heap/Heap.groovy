package heap

class Heap {
    public int[] heap;
    boolean minHeap = true
    int n = 0

    Heap() {
        this(true)
    }

    Heap(boolean minHeap) {
        this.minHeap = minHeap
        this.heap = new int[10000]

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(i);
        }
    }


    void heapify(int i) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largeOrSmall
        if (left < n && ((!minHeap && heap[left] > heap[i]) || (minHeap && heap[left] < heap[i]))) {
            largeOrSmall = left;
        } else {
            largeOrSmall = i;
        }

        if (right < n && ((!minHeap && heap[right] > heap[largeOrSmall]) || (minHeap && heap[right] < heap[largeOrSmall]))) {
            largeOrSmall = right;
        }
        if (largeOrSmall != i) {
            exchange(i, largeOrSmall);
            heapify(largeOrSmall);
        }
    }

    int poll() {
        int root = heap[0]
        heap[0] = heap[n - 1]
        heap[n - 1] = -1
        n--
        heapify(0)
        return root
    }

    void add(int key) {
        int childPosition = n
        heap[n++] = key
        while (childPosition > 0) {
            int parentPosition = (childPosition - 1).intdiv(2)
            def child = heap[childPosition]
            def parent = heap[parentPosition]
            if ((minHeap && parent > child) || (!minHeap && parent < child)) {
                exchange(childPosition, parentPosition)
                childPosition = parentPosition
            } else {
                break
            }
        }

    }

    int peek() {
        return heap[0]
    }

    void exchange(int i, int j) {
        int temp = heap[i]
        heap[i] = heap[j]
        try {
            heap[j] = temp
        } catch (Exception e) {
            throw new RuntimeException(e)
        }
    }

    @Override
    String toString() {
        return "[" + heap.toList()[0..n - 1].join(", ") + "]"
    }
}

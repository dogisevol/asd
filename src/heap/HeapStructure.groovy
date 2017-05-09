package heap


Scanner scanner = new Scanner(new File("median"));
def size = 0
def minHeap = new Heap()
def maxHeap = new Heap(false)
def mediansSum = 0
while (scanner.hasNextInt()) {
    def n = scanner.nextInt()
    if (size == 0) {
        maxHeap.add(n)
    } else if (size % 2 == 0) {
        if (n > minHeap.peek()) {
            maxHeap.add(minHeap.poll())
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
    mediansSum += maxHeap.peek()
    println mediansSum
}

//BigDecimal mediansSum = 0
//while (minHeap.n > 0) {
//    mediansSum += (minHeap.extractRoot() + maxHeap.extractRoot()) / 2
//}
//
println mediansSum % 10000




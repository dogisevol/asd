package hashtable

final Set map = new HashSet()

Scanner scanner = new Scanner(new File("2sum.txt"));
while (scanner.hasNextLong()) {
    map.add(scanner.nextLong())
}
//final def array = map.sort().toArray(new long[map.size()])
def counter = 0
def sums = [:]
(-10000..10000).each {
    sum ->
        if (sum % 1000 == 0) println sum
        if (sum % 10 == 0) print "."
        map.each { item ->
            if (map.contains(sum - item)) {
                counter++
                sums[sum] = sum
            }
        }
}

println(sums.size())

//private int byArray(int sum, long[] array) {
//    def counter = 0
//    int start = 0;
//    int end = array.size() - 1;
//
//    boolean found = false;
//
//    while (!found && start < end) {
//        if (array[start] + array[end] == sum) {
//            if (array[start].longValue() != array[end].longValue()) {
//                found = true;
//            }
//        } else if (array[start] + array[end] > sum) {
//            end--;
//        } else if (array[start] + array[end] < sum) {
//            start++;
//        }
//    }
//
//    if (found) {
//        counter++;
//    }
//    counter
//}



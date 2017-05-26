package stanford.course3.week3


Scanner scanner = new Scanner(new File("mwis.txt"))
def c = 0;
def n = scanner.nextInt()
int[] arr = new int[n];
while (scanner.hasNext()) {
    arr[c++] = scanner.nextInt()
}

def v = [1, 2, 3, 4, 17, 117, 517, 997]
def r = test(arr)
println r
v.each {
    if (r.contains(it))
        print 1
    else print 0
}

println()


arr = [280,
       618,
       762,
       908,
       409,
       34,
       59,
       277,
       246,
       779]

println test(arr)

arr = [460,
       250,
       730,
       63,
       379,
       638,
       122,
       435,
       705,
       84]

println test(arr)


private List test(int[] arr) {
    def len = arr.length;

    def sum = [arr[0], Math.max(arr[0], arr[1])];
    for (def i = 2; i < len; i++) {
        sum.push(Math.max(sum[i - 1], sum[i - 2] + arr[i]));
    }

    def res = []
    for (def i = len - 1; i >= 0;) {
        if (i == 0) {
            res.add(i + 1)
            i -= 2;
        } else if (sum[i] > sum[i - 1]) {
            res.add(i + 1)
            i -= 2;
        } else {
            i--;
        }
    }
    return res
}

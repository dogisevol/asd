package stanford.course3.week3

Scanner scanner = new Scanner(new File("test.txt"))
def c = 1;
def n = scanner.nextInt();
def array = new int[n + 1]
array[0] = 0
while (scanner.hasNext()) {
    array[c++] = scanner.nextInt()
}

int[] A = new int[n + 1]
A[0] = array[0]
A[1] = array[1]

(2..n).each {
    A[it] = Math.max(A[it - 1], A[it - 2] + array[it])
}

println A[A.length-1]

S = []
I = []
i = n
while (i >= 1) {
    if (i == 1) {
        S.add(array[i])
        I.add(i)
        i--
    } else if (A[i - 1] >= A[i - 2] + array[i])
        i -= 1
    else {
        S.add(array[i])
        I.add(i)
        i -= 2
    }
}

def w = [4962786, 6395702, 5601590, 3803402, 3889157, 7463231, 5906419, 7546051]
w.each {
    if (S.contains(it))
        print 1
    else print 0
}

println(S)
println I
def v = [1, 2, 3, 4, 17, 117, 517, 997]

v.each {
    if (I.contains(it))
        print 1
    else print 0
}
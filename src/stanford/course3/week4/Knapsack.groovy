package stanford.course3.week4

// Input:
// Values (stored in array v)
// Weights (stored in array w)
// Number of distinct items (n)
// Knapsack capacity (W)

Scanner scanner = new Scanner(new File("knapsack1.txt"))
def c = 0;
def W = scanner.nextInt() + 1
def n = scanner.nextInt() + 1
int[] v = new int[n]
int[] w = new int[n]
while (scanner.hasNext()) {
    v[c] = scanner.nextInt()
    w[c++] = scanner.nextInt()
}


int[][] A = new int[n][W]
for (int x = 0; x < W; x++) {
    A[0][x] = 0
}

for (int i = 1; i < n; i++) {
    for (int x = 0; x < W; x++) {
        if (w[i-1] > x) {
            A[i][x] = A[i - 1][x]
        } else {
            A[i][x] = Math.max(A[i - 1][x], A[i - 1][x - w[i-1]] + v[i-1])
        }
    }

}
println A[n-1][W-1]

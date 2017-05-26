package stanford.course4.week2

Stack tsp(int[][] matrix) {
    Stack<Integer> stack = new Stack<Integer>();
    int numberOfNodes = matrix[1].length - 1;
    int[] visited = new int[numberOfNodes + 1];
    visited[1] = 1;
    stack.push(1);
    int element, dst = 0, i;
    int min = Integer.MAX_VALUE;
    boolean minFlag = false;
    System.out.print(1 + "\t");

    while (!stack.isEmpty()) {
        element = stack.peek();
        i = 1;
        min = Integer.MAX_VALUE;
        while (i <= numberOfNodes) {
            if (matrix[element][i] > 1 && visited[i] == 0) {
                if (min > matrix[element][i]) {
                    min = matrix[element][i];
                    dst = i;
                    minFlag = true;
                }
            }
            i++;
        }
        if (minFlag) {
            visited[dst] = 1;
            stack.push(dst);
            System.out.print(dst + "\t");
            minFlag = false;
            continue;
        }
        stack.pop();
    }
    return stack
}

//Base case:
//        A[S, 1] =
//
//0 if S = {1}
//+∞ otherwise [no way to avoid visiting vertex (twice)]
//For m = 2, 3, . . . , n [m = subproblem size]
//For each set S ⊆ {1, 2, . . . , n} of size m that contains 1
//For each j ∈ S, j 6= 1
//A[S, j] = mink∈S,k6=j{A[S − {j}, k] + ckj} [same as recurrence]
//Return minj=2,...,n{ A[{1, 2, . . . , n}, j] + cj1 }


package tasks;

/**
 * Created by AUALAVE on 20/02/2017.
 */
public class RotateMatrix {

    public static void main(String[] args) {
        int[][] array = new int[][]{
                {1, 2, 3, 4},
                {5, 1, 2, 8},
                {9, 4, 3, 2},
                {3, 4, 5, 6}
        };

//        array = new int[][]{
//                {1, 2},
//                {3, 4}
//        };

//in place
        for (int layer = 0; layer < array.length / 2; layer++) {
            int first = layer;
            int last = array.length - layer - 1;
            for (int i = first; i < last; i++) {
                int offset = i - first;
                int topLeft = array[first][i];
                array[first][i] = array[last - offset][first];
                array[last - offset][first] = array[last][last - offset];
                array[last][last - offset] = array[i][last];
                array[i][last] = topLeft;
            }
        }


        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                System.out.print(array[i][j]);
                System.out.print(",");
            }
            System.out.println();
        }
        System.out.println("____________________________________");


        int[][] rotated = new int[array.length][array.length];

        for (int i = 0; i < array.length; ++i) {
            for (int j = 0; j < array.length; ++j) {
                rotated[i][j] = array[array.length - j - 1][i];
            }
        }

        for (int i = 0; i < rotated.length; i++) {
            for (int j = 0; j < rotated.length; j++) {
                System.out.print(rotated[i][j]);
                System.out.print(",");
            }
            System.out.println();
        }
    }

}

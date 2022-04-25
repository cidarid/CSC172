import java.util.ArrayList;
import java.util.Scanner;

public class Lab3Task1 {

    /**
     * This function creates a scanner, getting the users input to populate the 4x4 2D array.
     * It then creates a 2D ArrayList using the values from the array.
     * Finally, it prints the array and then the ArrayList to console.
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] split = input.split(";");
        int[][] array = new int[4][4];
        for (int i = 0; i < split.length; i++) {
            Scanner splitScanner = new Scanner(split[i]);
            for (int j = 0; splitScanner.hasNext(); j++) {
                array[i][j] = splitScanner.nextInt();
            }
        }
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        for (int[] subArray : array) {
            ArrayList<Integer> subList = new ArrayList<>();
            for (int i : subArray) {
                subList.add(i);
            }
            list.add(subList);
        }

        print2dArray(array);

        print2dList(list);
    }

    /**
     * This function loops through the columns and rows of a passed 2D array and prints it to console.
     * @param array
     */
    public static void print2dArray(int[][] array) {
        for (int[] ints : array) {
            for (int j = 0; j < 4; j++) {
                System.out.print(ints[j] + "\t");
            }
            System.out.println();
        }
    }

    /**
     *This function loops through the columns and rows of a passed 2D ArrayList and prints it to console.
     * @param list
     */
    public static void print2dList(ArrayList<ArrayList<Integer>> list) {
        for (ArrayList<Integer> subList : list) {
            for (Integer i : subList) {
                System.out.print(i + "\t");
            }
            System.out.println();
        }
    }

}

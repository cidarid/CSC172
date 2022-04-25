import java.util.ArrayList;
import java.util.Scanner;

public class Lab3Task2 {

    /**
     * This function uses the same Scanner method as Task 1 to get the user input and populate
     * respective arrays and ArrayLists.
     * It then executes a running sum method to the left and then to the right for the array, and prints it
     * Finally it executes a running sum method up and then down for the ArrayList, and prints it
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
        runningSum2dArray(array, 1);
        Lab3Task1.print2dArray(array);
        runningSum2dArray(array, 2);
        Lab3Task1.print2dArray(array);

        runningSum2dArrayList(list, 3);
        Lab3Task1.print2dList(list);
        runningSum2dArrayList(list, 4);
        Lab3Task1.print2dList(list);
    }

    /**
     * This function takes a passed direction dir and adds up all the numbers in that direction, i.e.
     * a dir of 2 (right) will add up all the numbers cumulatively in a row left to right.
     * @param array
     * @param dir
     */
    public static void runningSum2dArray(int[][] array, int dir) {
        if (dir == 1) {
            for (int[] subArray : array) {
                int start = subArray[subArray.length - 1];
                for (int i = subArray.length - 2; i > -1; i--) {
                    start += subArray[i];
                    subArray[i] = start;
                }
            }
        } else if (dir == 2) {
            for (int[] subArray : array) {
                int start = subArray[0];
                for (int i = 1; i < subArray.length; i++) {
                    start += subArray[i];
                    subArray[i] = start;
                }
            }
        } else if (dir == 3) {
            for (int i = array.length - 1; i > 0; i--) {
                for (int j = 0; j < array[i].length; j++) {
                    array[i - 1][j] += array[i][j];
                }
            }
        } else if (dir == 4) {
            for (int i = 0; i < array.length - 1; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    array[i + 1][j] += array[i][j];
                }
            }
        } else {
            System.out.println("Invalid direction passed to runningSum2dArray().");
        }
    }

    /**
     * This function does the same thing as runningSum2dArray, but for a 2D ArrayList.
     * @param list
     * @param dir
     */
    public static void runningSum2dArrayList(ArrayList<ArrayList<Integer>> list, int dir) {
        if (dir == 1) {
            for (ArrayList<Integer> subList : list) {
                int start = subList.get(subList.size() - 1);
                for (int i = subList.size() - 2; i > -1; i--) {
                    start += subList.get(i);
                    subList.set(i, start);
                }
            }
        } else if (dir == 2) {
            for (ArrayList<Integer> subList : list) {
                int start = subList.get(0);
                for (int i = 1; i < subList.size(); i++) {
                    start += subList.get(i);
                    subList.set(i, start);
                }
            }
        } else if (dir == 3) {
            for (int i = list.size() - 1; i > 0; i--) {
                for (int j = 0; j < list.get(i).size(); j++) {
                    list.get(i - 1).set(j, list.get(i).get(j) + list.get(i - 1).get(j));
                }
            }
        } else if (dir == 4) {
            for (int i = 0; i < list.size() - 1; i++) {
                for (int j = 0; j < list.get(i).size(); j++) {
                    list.get(i + 1).set(j, list.get(i).get(j) + list.get(i + 1).get(j));
                }
            }
        } else {
            System.out.println("Invalid direction passed to runningSum2dArray().");
        }
    }

}

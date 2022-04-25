import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;

public class Lab3Task3 {

    /**
     * This method takes a set of numbers and adds them to an ArrayList. It then prints that
     * ArrayList using various methods.
     * @param args
     */
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] inputs = input.split(" ");
        for (String s : inputs) {
            list.add(Integer.parseInt(s));
        }
        printArrayListBasicLoop(list);
        printArrayListForLoopListIterator(list);
        printArrayListWhileLoopListIterator(list);
        printArrayListEnhancedLoop(list);
    }

    /**
     * This method uses a basic for loop to print an ArrayList to console.
     * @param list
     */
    public static void printArrayListBasicLoop(ArrayList<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    /**
     *This method uses a for each loop to print an ArrayList to console.
     * @param list
     */
    public static void printArrayListEnhancedLoop(ArrayList<Integer> list) {
        for (int i : list) {
            System.out.println(i);
        }
    }

    /**
     * This method uses a basic for loop with an iterator to print an ArrayList to console.
     * @param list
     */
    public static void printArrayListForLoopListIterator(ArrayList<Integer> list) {
        ListIterator<Integer> integerIterator = list.listIterator();
        for (Iterator<Integer> i = integerIterator; i.hasNext();) {
            System.out.println(i.next());
        }
    }

    /**
     * This method uses a while loop with an iterator to print an ArrayList to console.
     * @param list
     */
    public static void printArrayListWhileLoopListIterator(ArrayList<Integer> list) {
        ListIterator<Integer> integerIterator = list.listIterator();
        while (integerIterator.hasNext())
            System.out.println(integerIterator.next());
    }
}

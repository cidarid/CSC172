/******************************************************************************
 *  Compilation:  javac Sorting.java
 *  Execution:    java Sorting input.txt AlgorithmUsed
 *  Dependencies: StdOut.java In.java Stopwatch.java
 *  Data files:   http://algs4.cs.princeton.edu/14analysis/1Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/2Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/4Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/8Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/16Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/32Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/1Mints.txt
 *
 *  A program to play with various sorting algorithms.
 *
 *
 *  Example run:
 *  % java Sorting 2Kints.txt  2
 *
 ******************************************************************************/
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Arrays;
import java.io.*;

public class Sorting {

    /**
     * Sorts the numbers present in the file based on the algorithm provided.
     * 0 = Arrays.sort() (Java Default)
     * 1 = Bubble Sort
     * 2 = Selection Sort
     * 3 = Insertion Sort
     * 4 = Mergesort
     * 5 = Quicksort
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args)  {
        In in = new In(args[0]);

        // Storing file input in an array
        int[] a = in.readAllInts();

        // b is a sorted copy of a
        int[] b = Arrays.copyOf(a, a.length);
        Arrays.sort(b);

        // c is a reverse order sorted copy of a
        int[] c = new int[a.length];
        for (int i = 0, j = a.length - 1; i < b.length && j >= 0; i++, j--) {
            c[i] = b[j];
        }

        // d is an almost-sorted copy of b
        int[] d = Arrays.copyOf(b, b.length);
        int swaps = (int) (d.length * 0.1);
        for (int i = 0; i < swaps; i++) {
            int x = StdRandom.uniform(d.length);
            int y = StdRandom.uniform(d.length);
            swap(d, x, y);
        }

        try {
            File af = new File("a.txt");
            FileWriter aOut = new FileWriter(af, false);
            File bf = new File("b.txt");
            FileWriter bOut = new FileWriter(bf, false);
            File cf = new File("c.txt");
            FileWriter cOut = new FileWriter(cf, false);
            File df = new File("d.txt");
            FileWriter dOut = new FileWriter(df, false);

            String algorithmUsed;
            String netID = "jtrokel, sfletch9";
            switch (args[1]) {
                case "0" -> {
                    algorithmUsed = "Arrays.sort()";

                    Stopwatch timer1 = new Stopwatch();

                    defaultSort(a);

                    double time1 = timer1.elapsedTimeMillis();
                    String timeStamp1 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "a", time1, timeStamp1, netID, args[0]);

                    for (int i : a)
                        aOut.write(i + "\n");
                    aOut.close();

                    Stopwatch timer2 = new Stopwatch();

                    defaultSort(b);

                    double time2 = timer2.elapsedTimeMillis();
                    String timeStamp2 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "b", time2, timeStamp2, netID, args[0]);

                    for (int i : b)
                        bOut.write(i + "\n");
                    bOut.close();

                    Stopwatch timer3 = new Stopwatch();

                    defaultSort(c);

                    double time3 = timer3.elapsedTimeMillis();
                    String timeStamp3 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "c", time3, timeStamp3, netID, args[0]);

                    for (int i : c)
                        cOut.write(i + "\n");
                    cOut.close();

                    Stopwatch timer4 = new Stopwatch();

                    defaultSort(d);

                    double time4 = timer4.elapsedTimeMillis();
                    String timeStamp4 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "d", time4, timeStamp4, netID, args[0]);

                    for (int i : d)
                        dOut.write(i + "\n");
                    dOut.close();
                }
                case "1" -> {
                    algorithmUsed = "Bubble sort";

                    Stopwatch timer1 = new Stopwatch();

                    bubbleSort(a);

                    double time1 = timer1.elapsedTimeMillis();
                    String timeStamp1 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "a", time1, timeStamp1, netID, args[0]);

                    for (int i : a)
                        aOut.write(i + "\n");
                    aOut.close();

                    Stopwatch timer2 = new Stopwatch();

                    bubbleSort(b);

                    double time2 = timer2.elapsedTimeMillis();
                    String timeStamp2 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "b", time2, timeStamp2, netID, args[0]);

                    for (int i : b)
                        bOut.write(i + "\n");
                    bOut.close();

                    Stopwatch timer3 = new Stopwatch();

                    bubbleSort(c);

                    double time3 = timer3.elapsedTimeMillis();
                    String timeStamp3 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "c", time3, timeStamp3, netID, args[0]);

                    for (int i : c)
                        cOut.write(i + "\n");
                    cOut.close();

                    Stopwatch timer4 = new Stopwatch();

                    bubbleSort(d);

                    double time4 = timer4.elapsedTimeMillis();
                    String timeStamp4 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "d", time4, timeStamp4, netID, args[0]);

                    for (int i : d)
                        dOut.write(i + "\n");
                    dOut.close();
                }
                case "2" -> {
                    algorithmUsed = "Selection sort";

                    Stopwatch timer1 = new Stopwatch();

                    selectionSort(a);

                    double time1 = timer1.elapsedTimeMillis();
                    String timeStamp1 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "a", time1, timeStamp1, netID, args[0]);

                    for (int i : a)
                        aOut.write(i + "\n");
                    aOut.close();

                    Stopwatch timer2 = new Stopwatch();

                    selectionSort(b);

                    double time2 = timer2.elapsedTimeMillis();
                    String timeStamp2 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "b", time2, timeStamp2, netID, args[0]);

                    for (int i : b)
                        bOut.write(i + "\n");
                    bOut.close();

                    Stopwatch timer3 = new Stopwatch();

                    selectionSort(c);

                    double time3 = timer3.elapsedTimeMillis();
                    String timeStamp3 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "c", time3, timeStamp3, netID, args[0]);

                    for (int i : c)
                        cOut.write(i + "\n");
                    cOut.close();

                    Stopwatch timer4 = new Stopwatch();

                    selectionSort(d);

                    double time4 = timer4.elapsedTimeMillis();
                    String timeStamp4 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "d", time4, timeStamp4, netID, args[0]);

                    for (int i : d)
                        dOut.write(i + "\n");
                    dOut.close();
                }
                case "3" -> {
                    algorithmUsed = "Insertion sort";

                    Stopwatch timer1 = new Stopwatch();

                    insertionSort(a);

                    double time1 = timer1.elapsedTimeMillis();
                    String timeStamp1 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "a", time1, timeStamp1, netID, args[0]);

                    for (int i : a)
                        aOut.write(i + "\n");
                    aOut.close();

                    Stopwatch timer2 = new Stopwatch();

                    insertionSort(b);

                    double time2 = timer2.elapsedTimeMillis();
                    String timeStamp2 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "b", time2, timeStamp2, netID, args[0]);

                    for (int i : b)
                        bOut.write(i + "\n");
                    bOut.close();

                    Stopwatch timer3 = new Stopwatch();

                    insertionSort(c);

                    double time3 = timer3.elapsedTimeMillis();
                    String timeStamp3 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "c", time3, timeStamp3, netID, args[0]);

                    for (int i : c)
                        cOut.write(i + "\n");
                    cOut.close();

                    Stopwatch timer4 = new Stopwatch();

                    insertionSort(d);

                    double time4 = timer4.elapsedTimeMillis();
                    String timeStamp4 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "d", time4, timeStamp4, netID, args[0]);

                    for (int i : d)
                        dOut.write(i + "\n");
                    dOut.close();
                }
                case "4" -> {
                    algorithmUsed = "Mergesort";

                    Stopwatch timer1 = new Stopwatch();

                    mergeSort(a);

                    double time1 = timer1.elapsedTimeMillis();
                    String timeStamp1 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "a", time1, timeStamp1, netID, args[0]);

                    for (int i : a)
                        aOut.write(i + "\n");
                    aOut.close();

                    Stopwatch timer2 = new Stopwatch();

                    mergeSort(b);

                    double time2 = timer2.elapsedTimeMillis();
                    String timeStamp2 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "b", time2, timeStamp2, netID, args[0]);

                    for (int i : b)
                        bOut.write(i + "\n");
                    bOut.close();

                    Stopwatch timer3 = new Stopwatch();

                    mergeSort(c);

                    double time3 = timer3.elapsedTimeMillis();
                    String timeStamp3 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "c", time3, timeStamp3, netID, args[0]);

                    for (int i : c)
                        cOut.write(i + "\n");
                    cOut.close();

                    Stopwatch timer4 = new Stopwatch();

                    mergeSort(d);

                    double time4 = timer4.elapsedTimeMillis();
                    String timeStamp4 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "d", time4, timeStamp4, netID, args[0]);

                    for (int i : d)
                        dOut.write(i + "\n");
                    dOut.close();
                }
                case "5" -> {
                    algorithmUsed = "Quicksort";

                    Stopwatch timer1 = new Stopwatch();

                    quickSort(a);

                    double time1 = timer1.elapsedTimeMillis();
                    String timeStamp1 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "a", time1, timeStamp1, netID, args[0]);

                    for (int i : a)
                        aOut.write(i + "\n");
                    aOut.close();

                    Stopwatch timer2 = new Stopwatch();

                    quickSort(b);

                    double time2 = timer2.elapsedTimeMillis();
                    String timeStamp2 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "b", time2, timeStamp2, netID, args[0]);

                    for (int i : b)
                        bOut.write(i + "\n");
                    bOut.close();

                    Stopwatch timer3 = new Stopwatch();

                    quickSort(c);

                    double time3 = timer3.elapsedTimeMillis();
                    String timeStamp3 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "c", time3, timeStamp3, netID, args[0]);

                    for (int i : c)
                        cOut.write(i + "\n");
                    cOut.close();

                    Stopwatch timer4 = new Stopwatch();

                    quickSort(d);

                    double time4 = timer4.elapsedTimeMillis();
                    String timeStamp4 = new SimpleDateFormat("yyyyMMdd_HHmmss").
                            format(Calendar.getInstance().getTime());
                    StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed,
                            "d", time4, timeStamp4, netID, args[0]);

                    for (int i : d)
                        dOut.write(i + "\n");
                    dOut.close();
                }
                default -> {
                    StdOut.println("Invalid input passed to args[1].");
                }
            }
        } catch (IOException e) {
            StdOut.print("Error: " + e);
        }

    }

    /**
     * This method sorts the passed array arr using the default Arrays.sort().
     * @param arr Array to sort
     */
    public static void defaultSort(int[] arr) {
        Arrays.sort(arr);
    }

    /**
     * This method sorts the passed array arr using bubble sort
     * It loops through the array arr and "bubbles" the largest
     * number to the top, then repeats til all numbers are sorted
     * @param arr Array to sort
     */
    public static void bubbleSort(int[] arr) {
        int len = arr.length;
        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j < len - i - 1; j++)
                if (arr[j] > arr[j+1])
                    swap(arr, j, j+1);
        }
    }

    /**
     * Selection sorts arr by finding the minimum element from the unsorted
     * section of the array and placing it at the beginning, and repeating
     * until sorted.
     * @param arr Array to sort
     */
    public static void selectionSort(int[] arr) {
        int len = arr.length;
        for (int i = 0; i < len - 1; i++) {
            int min_element = i;
            for (int j = i+1; j < len; j++)
                if (arr[j] < arr[min_element])
                    min_element = j;

            swap(arr, min_element, i);
        }
    }

    /**
     * Insertion sort sorts arr by looping through the unsorted array and then
     * inserting the new unsorted number into the correct spot in the
     * already sorted array.
     * @param arr Array to sort
     */
    public static void insertionSort(int[] arr) {
        int j, key;
        int len = arr.length;

        for (int i = 1; i < len; i++) {
            key = arr[i];

            for (j = i-1; j >= 0 && key < arr[j]; j--)
                arr[j+1] = arr[j];
            arr[j + 1] = key;
        }
    }

    /**
     * Merge sort sorts a by using divide and conquer. It splits
     * arr into two halves, then recursively calls itself on the halves,
     * repeating until arrays of size 1 are reached, and then merging those
     * arrays in sorted order. It uses the helper methods mergeSortHelper to
     * split the arrays, and then uses merge to merge the arrays.
     * @param arr Array to sort
     */
    public static void mergeSort(int[] arr) {
        mergeSortHelper(arr, 0, arr.length - 1);
    }

    /**
     * Helps mergeSort by splitting the passed array
     * @param arr Array to split
     * @param left Min position of sub array
     * @param right Max position of sub array
     */
    public static void mergeSortHelper(int[] arr, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;

            mergeSortHelper(arr, left, middle);
            mergeSortHelper(arr, middle + 1, right);

            merge(arr, left, middle, right);
        }
    }

    /**
     * Helps mergeSort by merging the passed array
     * @param arr Array to merge
     * @param left Min position of left sub array
     * @param middle Where the two arrays will merge
     * @param right Max position of right sub array
     */
    public static void merge(int[] arr, int left, int middle, int right) {
        int leftSize = middle - left + 1;
        int rightSize = right - middle;

        int[] leftArr = new int[leftSize];
        int[] rightArr = new int[rightSize];

        for (int i = 0; i < leftSize; i++)
            leftArr[i] = arr[left + i];
        for (int j = 0; j < rightSize; j++)
            rightArr[j] = arr[middle + 1 + j];

        int i = 0, j = 0;
        int k = left;

        while (i < leftSize && j < rightSize) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            }
            else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }

        while (i < leftSize) {
            arr[k] = leftArr[i];
            i++;
            k++;
        }

        while (j < rightSize) {
            arr[k] = rightArr[j];
            j++;
            k++;
        }
    }

    /**
     * Quick sort sorts arr similarly to merge sort. It picks an element in
     * the array (or sub array) as a pivot, and partitions the array around
     * that pivot. It then sorts those partitioned array separately by calling
     * itself. It uses quickSortHelper to split the arrays, and uses partition to
     * find the split point and sport the arrays.
     * @param arr Array to sort
     */
    public static void quickSort(int[] arr) {
        quickSortHelper(arr, 0, arr.length - 1);
    }

    /**
     * This method helps quick sort by recursively splitting the
     * original array into multiple sub arrays
     * @param arr Array to sort
     * @param low Min of current sub array
     * @param high Max of current sub array
     */
    public static void quickSortHelper(int[] arr, int low, int high) {
        if (low < high) {
            int pivot = getMedian(arr, low, high);
            int partition = partition(arr, low, high, pivot);

            quickSortHelper(arr, low, partition - 1);
            quickSortHelper(arr, partition + 1, high);
        }
    }

    /**
     * Partition makes sure the arrays on either side of where
     * the pivot will be are going to be less than the pivot
     * on the left and more than the pivot on the right.
     * Once those are done being switched, the pivot is moved
     * to its proper position.
     * @param arr Array to sort
     * @param low Min of current sub array
     * @param high Max of current subarray
     * @return The position where the partition will happen
     */
    public static int partition(int[] arr, int low, int high, int pivot) {
        // Sets last element of array as pivot
        int leftCursor = low - 1;
        int rightCursor = high;
        while (leftCursor < rightCursor) {
            while(arr[++leftCursor] < pivot);
            while(rightCursor > 0 && arr[--rightCursor] > pivot);
            if(leftCursor >= rightCursor){
                break;
            }
            else{
                swap(arr, leftCursor, rightCursor);
            }
        }
        swap(arr, leftCursor, high);
        return leftCursor;
    }

    public static int getMedian(int[] arr, int left, int right) {
        int center = (left+right)/2;

        if(arr[left] > arr[center])
            swap(arr,center, left);

        if(arr[left] > arr[right])
            swap(arr, left, right);

        if(arr[center] > arr[right])
            swap(arr, center, right);

        swap(arr, center, right);
        return arr[right];
    }

    public static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }


}



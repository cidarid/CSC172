import java.util.Arrays;
import java.util.Collections;

public class Main {

    static class GenArray<T extends Comparable<? super T>> {
        T[] objs;

        GenArray(T[] objs) {
            this.objs = objs;
        }

        public String toString() {
            return Arrays.toString(objs);
        }

        public Object getMaxGen() {
            Arrays.sort(objs);
            return objs[objs.length - 1];
        }
    }

    public static void main(String[] args) {
        Integer [] intArray = {1, 2, 3, 4, 5 };
        Double [] doubArray = {1.1, 2.2, 3.3, 4.4};
        Character [] charArray = {'H','E','L', 'L', 'O' };
        String [] strArray = {"once", "upon", "a", "time" };
        printArrayGen(new GenArray<>(intArray));
        printArrayGen(new GenArray<>(doubArray));
        printArrayGen(new GenArray<>(charArray));
        printArrayGen(new GenArray<>(strArray));
        System.out.println("Max of intArray is: " + new GenArray<>(intArray).getMaxGen());
        System.out.println("Max of doubArray is: " + new GenArray<>(doubArray).getMaxGen());
        System.out.println("Max of charArray is: " + new GenArray<>(charArray).getMaxGen());
        System.out.println("Max of strArray is: " + new GenArray<>(strArray).getMaxGen());

    }

    static void printArrayNonGen(Object[] array) {
        for (Object o : array) {
            System.out.print(o + " ");
        }
        System.out.println();
    }

    static void printArray(Integer[] array) {
        System.out.println(Arrays.toString(array));
    }

    static void printArray(Double[] array) {
        System.out.println(Arrays.toString(array));
    }

    static void printArray(Character[] array) {
        System.out.println(Arrays.toString(array));
    }

    static void printArray(String[] array) {
        System.out.println(Arrays.toString(array));
    }

    static void printArrayGen(GenArray<?> array) {
        System.out.println(array.toString());
    }

    static Comparable getMax(Comparable[] array) {
        Arrays.sort(array);
        return array[array.length - 1];
    }


}

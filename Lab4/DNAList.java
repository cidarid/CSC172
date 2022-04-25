import java.io.*;
import java.util.Scanner;

public class DNAList {

    enum SeqType { DNA, RNA, empty }

    // Stores all sequences
    static Sequence[] sequences;

    /**
     * Runs main program
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int arraySize = Integer.parseInt(args[0]);
        sequences = new Sequence[arraySize];
        String commandFile = System.getProperty("user.dir") + File.separator + args[1];
        File file = new File(commandFile);
        Scanner scanner = new Scanner(file);
        // Loops through file and executes commands
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] keywords = line.split(" ");
            switch (keywords[0]) {
                case "insert": {
                    insert(Integer.parseInt(keywords[1]), SeqType.valueOf(keywords[2]), keywords[3]);
                    break;
                }
                case "print": {
                    if (keywords.length == 1) {
                        print();
                    } else {
                        print(Integer.parseInt(keywords[1]));
                    }
                    break;
                }
                case "remove": {
                    remove(Integer.parseInt(keywords[1]));
                    break;
                }
                case "clip": {
                    clip(Integer.parseInt(keywords[1]), Integer.parseInt(keywords[2]), Integer.parseInt(keywords[3]));
                    break;
                }
                case "copy": {
                    copy(Integer.parseInt(keywords[1]), Integer.parseInt(keywords[2]));
                    break;
                }
                case "transcribe": {
                    transcribe(Integer.parseInt(keywords[1]));
                    break;
                }
            }
        }
    }

    /**
     * Inserts sequence into list
     * @param pos Position to insert into
     * @param type DNA or RNA
     * @param sequenceString Actual sequnce
     */
    static void insert(int pos, SeqType type, String sequenceString) {
        Sequence sequence;
        // If it's valid make the sequence otherwise return
        if (validSequence(type, sequenceString)) {
            sequence = new Sequence(type, listFromString(sequenceString));
        }
        else {
            System.out.println("Error occurred while inserting.");
            return;
        }
        sequences[pos] = sequence;
    }

    /**
     * Checks if its possible to remove an item and removes it if possible
     * @param pos Position to remove
     */
    static void remove(int pos) {
        if (sequences[pos] == null) {
            System.out.println("No sequence to remove at specified position");
            return;
        }
        if (sequences[pos].type == SeqType.empty) {
            System.out.println("No sequence to remove at specified position");
            return;
        }
        sequences[pos].sequence = null;
        sequences[pos].type = SeqType.empty;
    }

    /**
     * Prints all sequences that exist
     */
    static void print() {
        for (int i = 0; i < sequences.length; i++) {
            if (sequences[i] == null) {
                continue;
            }
            if (sequences[i].type == SeqType.empty) {
                continue;
            }
            System.out.println(i + "\t" + sequences[i]);
        }
    }

    /**
     * Prints a sequence at a specific position
     * @param pos Position to print
     */
    static void print(int pos) {
        if (sequences[pos] == null) {
            System.out.println("No sequence to print at specified position.");
            return;
        }
        if (sequences[pos].type == SeqType.empty) {
            System.out.println("No sequence to print at specified position.");
            return;
        }
        System.out.println(sequences[pos]);
    }

    /**
     * Clips a position, changing an item to a substring of itself
     * @param pos What to clip
     * @param start Where to start substring
     * @param end Where to end substring
     */
    static void clip(int pos, int start, int end) {
        if (sequences[pos] == null) {
            System.out.println("No sequence to clip at specified position");
            return;
        }
        if (sequences[pos].type == SeqType.empty) {
            System.out.println("No sequence to clip at specified position");
            return;
        }
        LList sequence = sequences[pos].sequence;
        if (sequence == null) {
            System.out.println("No sequence to clip at specified position");
            return;
        }
        if (start < 0) {
            System.out.println("Start index is out of bounds");
            return;
        }
        if (end > sequence.size()) {
            System.out.println("End index is out of bounds");
            return;
        }
        if (start > end) {
            sequences[pos] = new Sequence(sequences[pos].type, listFromString(""));
            return;
        }
        String clippedList = "";
        for (int i = start; i <= end; i++) {
            sequence.moveToPos(i);
            clippedList += sequence.getValue();
        }
        sequences[pos] =  new Sequence(sequences[pos].type, listFromString(clippedList));
    }

    /**
     * Copies one sequence to another position
     * @param pos1 Sequence to copy
     * @param pos2 Sequence to overwrite with copy
     */
    static void copy(int pos1, int pos2) {
        Sequence sequence = sequences[pos1];
        if (sequence == null) {
            System.out.println("No sequence to copy at specified position");
            return;
        }
        if (sequence.type == SeqType.empty) {
            System.out.println("No sequence to copy at specified position");
            return;
        }
        String stringFromList = stringFromList(sequence.sequence);
        LList listFromString = listFromString(stringFromList);
        sequences[pos2] = new Sequence(sequence.type, listFromString);
    }

    /**
     * Changes DNA sequence to RNA sequence
     * @param pos DNA sequence to transcribe
     */
    static void transcribe(int pos) {
        if (sequences[pos] == null) {
            System.out.println("No sequence to transcribe at specified position");
            return;
        }
        if (sequences[pos].type == SeqType.empty) {
            System.out.println("No sequence to transcribe at specified position");
            return;
        }
        if (sequences[pos].type == SeqType.RNA) {
            System.out.println("Cannot transcribe RNA");
            return;
        }
        String removedTs = stringFromList(sequences[pos].sequence).replaceAll("T", "U");
        char[] sequence = removedTs.toCharArray();
        for (int i = 0; i < sequence.length; i++) {
            if (sequence[i] == 'T') {
                sequence[i] = 'U';
            }
            else if (sequence[i] == 'A') {
                sequence[i] = 'U';
            }
            else if (sequence[i] == 'U') {
                sequence[i] = 'A';
            }
            else if (sequence[i] == 'C') {
                sequence[i] = 'G';
            }
            else if (sequence[i] == 'G') {
                sequence[i] = 'C';
            }
        }
        insert(pos, SeqType.RNA, new String(sequence));
    }

    /**
     * Checks if a sequence is valid
     * @param type Type to check validity
     * @param sequence Sequence to check
     * @return Returns whether or not sequence is valid
     */
    static boolean validSequence(SeqType type, String sequence) {
        if (sequence == null) {
            return true;
        }
        if (sequence.length() == 0) {
            return true;
        }
        String replace = sequence.replace("A", "").replace("C", "").replace("G", "");
        if (type == SeqType.DNA) {
            sequence = replace.replace("T", "");
            return sequence.length() <= 0;
        }
        if (type == SeqType.RNA) {
            sequence = replace.replace("U", "");
            return sequence.length() <= 0;
        }
        System.out.print("Error with validSequence, returning false.");
        return false;
    }

    /**
     * Converts a string to a linked list
     * @param sequence String to convert
     * @return Linked list made from string
     */
    static LList listFromString(String sequence) {
        if (sequence == null) {
            return null;
        }
        LList<Character> list = new LList<>();
        char[] sequenceArray = sequence.toCharArray();
        for (char c : sequenceArray) {
            list.append(c);
        }
        return list;
    }

    /**
     * Converts a linked list to a string
     * @param sequence Linked list to convert
     * @return String made from linked list
     */
    static String stringFromList(LList sequence) {
        String str = "";
        sequence.moveToStart();
        for (int i = 0; i < sequence.size(); i++) {
            str += sequence.getValue();
            sequence.next();
        }
        return str;
    }
}

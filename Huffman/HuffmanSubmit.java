// Import any package as required


import java.util.*;

class Node {
    int freq;
    String value;
    Node left;
    Node right;

    // Returns String as binary, i.e. A will become 01100001
    public String binaryValue() {
        StringBuilder binary = new StringBuilder(Integer.toBinaryString(value.charAt(0)));
        while (binary.length() < 8) {
            binary.insert(0, "0");
        }
        return String.valueOf(binary);
    }
}

/**
 * Compares nodes, higher frequency is higher comparison
 */
class NodeComparator implements Comparator<Node> {
    public int compare(Node a, Node b) {
        return a.freq - b.freq;
    }
}

public class HuffmanSubmit implements Huffman {

    // Feel free to add more methods and variables as required.

    public void encode(String inputFile, String outputFile, String freqFile) {
        BinaryIn in = new BinaryIn(inputFile);
        BinaryOut out = new BinaryOut(outputFile);
        BinaryOut testOut = new BinaryOut("ur_test.jpg");
        // Stores characters and their frequencies
        Hashtable<String, Integer> table = new Hashtable<>();

        // Loops through file and gets bytes and their frequencies
        while (!in.isEmpty()) {
            // Get byte and convert to string
            String next = Character.toString(in.readChar());
            // Add byte to hashtable and increment frequency
            table.put(next, table.getOrDefault(next, 0) + 1);
        }

        // Creates Huffman tree from hashtable
        Node root = treeFromHashTable(table);

        // Outputs frequencies to frequency file
        BinaryOut freqFileOut = new BinaryOut(freqFile);
        outputToFreqFile(freqFileOut, root);
        freqFileOut.flush();

        // Creating hashtable to store character to huffman value relation
        Hashtable<String, Boolean[]> encodedValues = new Hashtable<>();
        // Adds character huffman value relations to hashtable
        encodedValuesFromTree(encodedValues, root, "");

        // Resets binary stream for input
        in = new BinaryIn(inputFile);

        // Loops through input file and outputs huffman encoded version to output file
        while (!in.isEmpty()) {
            char next = in.readChar();
            String nextAsString = Character.toString(next);
            // Outputs bits to encoded file, using the binary string from the encoded values hashtable
            Boolean[] bitString = encodedValues.get(nextAsString);
            for (Boolean b : bitString) {
                out.write(b);
            }
            Set<String> keySet = encodedValues.keySet();
            // Find the bit string's key (byte) and write it
            for (String key : keySet) {
                if (bitString == encodedValues.get(key)) {
                    testOut.write(key.charAt(0));
                }
            }
        }
        testOut.flush();
        out.flush();
    }

    /**
     * Loops through a Huffman tree and puts their binary codes in a Hashtable, where
     * the key is the character and the value is the binary code (stored as a boolean array)
     * @param table Hashtable to place binary codes and characters
     * @param root Root of Huffman tree
     * @param s Stores binary string for recursion
     */
    public void encodedValuesFromTree(Hashtable<String, Boolean[]> table, Node root, String s) {
        if (root.left == null && root.right == null) {
            if (!Objects.equals(root.value, "")) {
                Boolean[] boolArray = new Boolean[s.length()];
                char[] charArray = s.toCharArray();
                for (int i = 0; i < charArray.length; i++) {
                    boolArray[i] = charArray[i] != '0' ;
                }
                table.put(root.value, boolArray);
            }
            return;
        }
        assert root.left != null;
        encodedValuesFromTree(table, root.left, s + "0");
        encodedValuesFromTree(table, root.right, s + "1");
    }

    /**
     * Prints a representation of a Huffman tree
     * @param root Root of tree
     * @param s Stores string for recursion
     */
    public void print(Node root, String s) {
        if (root.left == null && root.right == null) {
            if (!Objects.equals(root.value, "")) {
                System.out.println("Byte of node is " + root.value + " with a frequency of " + root.freq + " represented by " + s);
            }
            return;
        }
        assert root.left != null;
        print(root.left, s + "0");
        print(root.right, s + "1");
    }


    /**
     * Outputs Huffman nodes in a tree, storing a character and its frequency,
     * to a file.
     * @param out File to output to
     * @param root Root of binary tree
     */
    public void outputToFreqFile(BinaryOut out, Node root) {
        if (root.left == null && root.right == null) {
            // If not internal node
            if (root.value != null) {
                out.write(root.binaryValue() + ":" + root.freq + "\n");
            }
            return;
        }
        assert root.left != null;
        outputToFreqFile(out, root.left);
        outputToFreqFile(out, root.right);
    }


    public void decode(String inputFile, String outputFile, String freqFile){
        BinaryIn in = new BinaryIn(inputFile);
        BinaryIn freqIn = new BinaryIn(freqFile);
        BinaryOut out = new BinaryOut(outputFile);
        // Stores characters and frequencies from freqFile
        Hashtable<String, Integer> freqTable = new Hashtable<>();

        // Loops through frequency files, populating freqTable with character frequencies
        while (!freqIn.isEmpty()) {
            // Get character
            StringBuilder binaryBuilder = new StringBuilder();
            // Collect byte until separator is reached
            while (!(binaryBuilder.toString().contains(":"))) {
                binaryBuilder.append(freqIn.readChar());
            }
            // Remove colon
            String binaryString = binaryBuilder.toString().replaceAll(":", "");
            int charCode = Integer.parseInt(binaryString, 2);
            String s = Character.toString(((char) charCode));


            // Get frequency
            char freqFromBytes = freqIn.readChar();
            StringBuilder intString = new StringBuilder();
            // Collect characters until line separator (marked here as 10 to increase compatability)
            while (freqFromBytes != 10)  {
                intString.append(freqFromBytes);
                freqFromBytes = freqIn.readChar();
            }
            int freq = Integer.parseInt(intString.toString());

            // Add to table
            freqTable.put(s, freq);
        }

        // Get tree from frequency hash table
        Node root = treeFromHashTable(freqTable);

        // Reads encoded file byte by byte, getting binary tree equivalent and outputting to out file
        while (!in.isEmpty()) {
            Node curr = root;
            // Loop through while the node is an internal node (has a value of "")
            do {
                // Read bit
                boolean bool = in.readBoolean();
                // If 1 go right
                if (bool) {
                    curr = curr.right;
                }
                // If 0 go left
                else {
                    curr = curr.left;
                }
            } while (curr.value == null && !in.isEmpty());
            if (curr.value != null) {
                out.write(curr.value.charAt(0));
                //out.write((char)(Integer.parseInt(curr.binaryValue(), 2)));
            }
        }
        out.flush();
    }

    /**
     * Converts a frequency table to a Huffman tree
     * @param table Stores a Huffman byte to frequency relation
     * @return Returns root of created tree
     */
    public Node treeFromHashTable(Hashtable<String, Integer> table) {
        // Priority queue to compare nodes to start with the least frequent
        PriorityQueue<Node> q = new PriorityQueue<>(table.size(), new NodeComparator());
        // Gets all keys in hashtable
        Set<String> keySet = table.keySet();
        // Populates priority queue with all Nodes in the passed hashtable
        for (String key : keySet) {
            Node n = new Node();

            n.value = key;
            n.freq = table.get(key);
            n.left = n.right = null;

            q.add(n);
        }

        // Initializing root as null
        Node root = null;
        // While more than one item remains in tree, root has not been found yet
        while (q.size() > 1) {
            // Get smallest (left) current Node
            Node a = q.peek();
            q.poll();
            // Get second smallest (right) current Node
            Node b = q.peek();
            q.poll();

            // Create an internal node
            Node internal = new Node();
            assert b != null;
            internal.freq = a.freq + b.freq;
            internal.left = a;
            internal.right = b;
            internal.value = null;

            root = internal;

            // Add internal node to queue
            q.add(internal);
        }
        return root;
    }


    public static void main(String[] args) {
        Huffman huffman = new HuffmanSubmit();
        huffman.encode("ur.jpg", "ur.enc", "freq_img.txt");
        huffman.decode("ur.enc", "ur_dec.jpg", "freq_img.txt");
        huffman.encode("alice30.txt", "alice30.enc", "freq_txt.txt");
        huffman.decode("alice30.enc", "alice30_dec.txt", "freq_txt.txt");
        // After decoding, both ur.jpg and ur_dec.jpg should be the same.
        // On linux and mac, you can use `diff' command to check if they are the same.
    }

}

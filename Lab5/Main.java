import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Trie trie = new Trie();
        File file = new File(args[0]);
        Scanner scanner = new Scanner(file);
        // Handles file input
        while (scanner.hasNext()) {
            String s = scanner.next();
            switch (s) {
                case "insert": {
                    insert(trie, scanner.next());
                }
                case "search": {
                    System.out.println(search(trie, scanner.next()));
                }
                case "print": {
                    System.out.println(trieToList(trie).toString()
                            .replace("[", "")
                            .replace("]", "")
                            .replace(",", "")
                    );
                }
                case "largest": {
                    System.out.println(largest(trie));
                }
                case "smallest": {
                    System.out.println(smallest(trie));
                }
                case "height": {
                    System.out.println(height(trie));
                }
                case "size": {
                    System.out.println(size(trie));
                }
            }
        }
        scanner.close();
    }

    /**
     * Inserts string into trie using trie.insert
     * @param trie Trie to insert into
     * @param str String to insert
     */
    public static void insert(Trie trie, String str) {
        trie.insert(str);
    }

    /**
     * Converts a trie to a list of all of its values in order
     * @param trie Trie to convert into list
     * @return Converted list in lexographic order
     */
    public static ArrayList<String> trieToList(Trie trie) {
        ArrayList<String> arr = new ArrayList<>();
        trieToListHelper(trie.root, arr);
        return arr;
    }

    /**
     * Recursive function to collect all nodes of trie and add to list
     * @param node Current node in recursion
     * @param list List to add to
     */
    public static void trieToListHelper(Node node, ArrayList<String> list) {
        // If empty node
        if(node == null)
            return;
        // Goes left
        trieToListHelper(node.getLeft(), list);
        // Adds if value exists
        if (node.value != null) {
            list.add(node.value);
        }
        // Goes right
        trieToListHelper(node.getRight(), list);
    }

    /**
     * Collects the largest node from a trie
     * @param trie Trie to collect from
     * @return The largest node
     */
    public static String largest(Trie trie) {
        Node curr = trie.root;
        while (curr.getRight() != null) {
            curr = curr.getRight();
        }
        return curr.value;
    }

    /**
     * Collects the smallest node from a trie
     * @param trie Trie to collect from
     * @return The smallest node
     */
    public static String smallest(Trie trie) {
        Node curr = trie.root;
        while (curr.getLeft() != null) {
            curr = curr.getLeft();
        }
        return curr.value;
    }

    /**
     * Searches a trie for a value using trie.search
     * @param trie Trie to search
     * @param str String to search for
     * @return Closest value to search string
     */
    public static String search (Trie trie, String str) {
        return trie.search(str).value;
    }

    /**
     * Gets the size of a trie (how many nodes there are)
     * @param trie Trie to get size of
     * @return Size
     */
    public static int size(Trie trie) {
        return sizeHelper(trie.root);
    }

    /**
     * Helper recursive function for size()
     * @param node Current node in recursion
     * @return Size
     */
    public static int sizeHelper(Node node) {
        if (node == null) {
            return 0;
        }
        if (node.value != null) {
            return 1;
        }
        return sizeHelper(node.getLeft()) + sizeHelper(node.getRight());
    }

    /**
     * Gets the height of a trie
     * @param trie Trie to get height of
     * @return Height
     */
    public static int height (Trie trie) {
        return heightHelper(trie.root);
    }

    /**
     * Recursive helper function for height()
     * @param node Current node in recursion
     * @return Height
     */
    static int heightHelper(Node node) {
        if (node == null) {
            return 0;
        }
        else {
            int left = heightHelper(node.getLeft());
            int right = heightHelper(node.getRight());

            // Gets max of left and right height
            if (left > right)
                return left + 1;
            else
                return right + 1;
        }
    }
}

/**
 * Node class of trie
 */
class Node {
    private Node left, right, parent;
    public String value;


    public Node() {
        this.value = null;
    }

    public Node(String s) {
        this.value = s;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
        left.parent = this;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
        right.parent = this;
    }

    public Node getParent() {
        return parent;
    }

}

/**
 * Trie class, stores root of node
 */
class Trie {
    public Node root;

    public Trie() {
        root = null;
    }

    public Trie(String s) {
        root = new Node(s);
    }

    /**
     * First, attempts to find searched string in tree
     * Does this by going left for 0, right for 1, til a node is hit
     * If no node is hit (i.e. item doesn't exist in tree)
     * Find closest node either larger or smaller alphabetically, then return that
     * @param s String to search
     * @return Node of searched item or closest item
     */
    public Node search(String s) {
        // If string is empty, impossible to search
        if (s.length() == 0) {
            System.out.println("Empty string passed to search(). Returning null.");
            return null;
        }
        char[] arr = s.toCharArray();
        Node pos = root;
        // Loop through characters in string
        for (char c : arr) {
            // If we've reached a node return node
            if (pos.value != null) {
                return pos;
            }
            // Go left in tree for 0, right for 1
            if (c == '0') {
                // If there's nothing to the left, find the closest node and return
                if (pos.getLeft() == null) {
                    return search_not_found(s, pos);
                }
                // Move left
                pos = pos.getLeft();
            }
            else if (c == '1') {
                // If there's nothing to the right, find the closest node and return
                if (pos.getRight() == null) {
                    return search_not_found(s, pos);
                }
                // Move right
                pos = pos.getRight();
            }
            else {
                System.out.println("Non binary string passed to search(). Returning null.");
                return null;
            }
        }
        return null;
    }

    /**
     * Helper function for search that handles cases where no item was found at the
     * position of the search string.
     * @param s Original search string
     * @param n Node where searching stopped
     * @return Closest node to original
     */
    private Node search_not_found(String s, Node n) {
        Node nodeBefore = getClosestBefore(n);
        Node nodeAfter = getClosestAfter(n);
        // If there's no node before, the closest must be the node after, so return
        // And vice versa
        if (nodeBefore == null)
            return nodeAfter;
        if (nodeAfter == null)
            return nodeBefore;
        // Stores the before and after string's similarities to the search string
        int beforeStartSimilarity = 0;
        int afterStartSimilarity = 0;
        int minBefore = Math.min(s.length(), nodeBefore.value.length());
        int minAfter = Math.min(s.length(), nodeAfter.value.length());
        // Gets the similarities to the search string
        for (int i = 0; i < minBefore && nodeBefore.value.charAt(i) == s.charAt(i); i++) {
            beforeStartSimilarity++;
        }
        for (int i = 0; i < minAfter && nodeAfter.value.charAt(i) == s.charAt(i); i++) {
            afterStartSimilarity++;
        }
        // Returns the highest similarity string, with priority going to the lexicographically larger string
        // if the two are equal
        if (beforeStartSimilarity > afterStartSimilarity)
            return nodeBefore;
        else
            return nodeAfter;
    }

    /**
     * Helper function for search that gets the closest node before the search node
     * Goes up til it can go left, then goes left, and keeps going left until
     * it can go right, and then once it can go right, goes as far right as it can,
     * then returns that node. Will be null if no node before exists.
     * @param curr Node to search for
     * @return Closest node alphabetically before the search node
     */
    public Node getClosestBefore(Node curr) {
        Node previous = curr;
        // Step up until we can step left and not step into where we just left or til we reach the root
        while (curr.getLeft() == null || curr.getLeft() == previous) {
            // If we have reached the root, and going to the left gets us back to where we were,
            // we started at the lowest alphabetically possible node so return null
            if (curr == root && root.getLeft() == previous) {
                return null;
            }
            previous = curr;
            curr = curr.getParent();
        }
        // Keep stepping downwards til a non-empty node is found
        // Try to go right first, if not possible, go left
        while (curr.value == null) {
            if (curr.getRight() != null && curr.getRight() != previous) {
                curr = curr.getRight();
            } else if (curr.getLeft() != null && curr.getLeft() != previous) {
                curr = curr.getLeft();
            }
        }
        return curr;
    }

    /**
     * Helper function for search that gets the closest node after the search node
     * Goes up til it can go right, then goes right, and keeps going right until
     * it can go left, and then once it can go left, goes as far left as it can,
     * then returns that node. Will be null if no node before exists.
     * @param curr Node to search for
     * @return Closest node alphabetically after the search node
     */
    public Node getClosestAfter(Node curr) {
        Node previous = curr;
        // Step up until we can step left and not step into where we just left or til we reach the root
        while (curr.getRight() == null || curr.getRight() == previous) {
            // If we have reached the root, and going to the left gets us back to where we were,
            // we started at the lowest alphabetically possible node so return null
            if (curr == root && root.getRight() == previous) {
                return null;
            }
            previous = curr;
            curr = curr.getParent();
        }
        // Keep stepping downwards til a non-empty node is found
        // Try to go right first, if not possible, go left
        while (curr.value == null) {
            if (curr.getLeft() != null && curr.getLeft() != previous) {
                curr = curr.getLeft();
            } else if (curr.getRight() != null && curr.getRight() != previous) {
                curr = curr.getRight();
            }
        }
        return curr;
    }


    /**
     * Inserts string str into trie
     * @param str String to insert to tree
     * @return True if successful insert, false if not
     */
    public boolean insert(String str) {
        // If there's no root, make this string the root and retrun
        if (root == null) {
            root = new Node(str);
            return true;
        }

        Node currNode = root;
        // Loop through string to insert going left and right
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // If the current char says to go left
            if (c == '0') {
                // If there is a node already where the inserted node should go, call insertOverlap()
                if (currNode.value != null) {
                    insertOverlap(str, i, currNode);
                    return true;
                }
                // If there's nothing to the left, insert the new node and return
                else if (currNode.getLeft() == null) {
                    currNode.setLeft(new Node(str));
                    return true;
                }
                // Otherwise, go left and keep going down the tree
                else {
                    currNode = currNode.getLeft();
                }
            }
            // If the current char says to go right
            else if (c == '1') {
                // If there is a node already where the inserted node should go, call insertOverlap()
                if (currNode.value != null) {
                    insertOverlap(str, i, currNode);
                    return true;
                }
                // If there's nothing to the left, insert the new node and return
                else if (currNode.getRight() == null) {
                    currNode.setRight(new Node(str));
                    return true;
                }
                // Otherwise, go right and keep going down the trees
                else {
                    currNode = currNode.getRight();
                }
            }
            // If there is not a 0 or 1, this is a non-binary string and therefore an error
            else {
                System.out.println("Non binary string passed to insert(). Aborting.");
                return false;
            }
        }
        return false;
    }

    /**
     * Helper function for insert that handles when the position to insert into
     * already has a node there
     * @param newStr String that is being inserted
     * @param depth Depth of intersection
     * @param curr Current node
     */
    private void insertOverlap(String newStr, int depth, Node curr) {
        // Stores original node value
        String origStr = curr.value;
        // If the two are equal, return as there is no need to make a new node
        if (Objects.equals(newStr, origStr)) {
            return;
        }
        // Set current node to an internal node
        curr.value = null;
        // Loop until difference found
        for (int i = depth; i < newStr.length(); i++) {
            // If they are still equal, go in that direction
            if (origStr.charAt(i) == newStr.charAt(i)) {
                if (origStr.charAt(i) == '0') {
                    curr.setLeft(new Node());
                    curr = curr.getLeft();
                } else {
                    curr.setRight(new Node());
                    curr = curr.getRight();
                }
            }
            // If not, we have found a place where both nodes can be inserted
            // Take the current internal node, add the two nodes as children, and return
            else {
                if (origStr.charAt(i) == '0') {
                    curr.setLeft(new Node(origStr));
                    curr.setRight(new Node(newStr));
                } else {
                    curr.setLeft(new Node(newStr));
                    curr.setRight(new Node(origStr));
                }
                return;
            }
        }
    }


}

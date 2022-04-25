import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;

public class StopContagion {
    static boolean degrees = false;
    static boolean trace = false;
    static int radius = 2;
    static int num_nodes;
    static File input_file;

    /**
     * This function handles the command line arguments and then creates a graph
     * using those arguments.
     * 
     * @param args The arguments
     */
    public static void main(String[] args) {
        // If less than 2 or more than 6 arguments are passed, invalid, abort
        if (args.length < 2 || args.length > 6) {
            System.out.println("Error: invalid number of arguments passed. Aborting.");
            System.exit(-1);
        }
        // Loops through args finding -d, -r, and -t if they exist, if not then continue
        // to mandatory args
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-d")) {
                degrees = true;
            } else if (args[i].equals("-r")) {
                i++;
                radius = Integer.parseInt(args[i]);
            } else if (args[i].equals("-t")) {
                trace = true;
            } else {
                break;
            }
        }
        num_nodes = Integer.parseInt(args[args.length - 2]);
        input_file = new File(args[args.length - 1]);
        // Creates graph from file
        Graph g = createGraph(input_file);
        System.out.println(g);
        // Uses selected method to isolate graph
        if (degrees) {
            g.degreeIsolate(num_nodes);
        } else {
            g.influenceIsolate(num_nodes, radius);
        }
    }

    /**
     * This method creates a graph from the passed file.
     * It assumes that the lines will be the format "a b", where
     * a is the first node to add and b is the second node, and
     * a and b are connected.
     * 
     * @param input Input file used to create graph
     * @return Created graph
     */
    public static Graph createGraph(File input) {
        Graph g = new Graph();
        Scanner scanner;
        // Try catch to catch file not found
        try {
            scanner = new Scanner(input);
            String[] lineNodes;
            // Loops thru file
            while (scanner.hasNextLine()) {
                // Splits into two nodes
                lineNodes = scanner.nextLine().split(" ");
                // Creates new nodes, if their value is already in the graph's vertices, replace
                // the reference, if not then add to the vertices
                Node a = new Node(lineNodes[0]);
                Node b = new Node(lineNodes[1]);
                if (g.vertices.contains(a))
                    a = getNode(g.vertices, a);
                else
                    g.addNode(a);
                if (g.vertices.contains(b))
                    b = getNode(g.vertices, b);
                else
                    g.addNode(b);
                // Connect the nodes
                a.addConnection(b);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File passed in args not found. Aborting.");
            System.exit(-1);
        }
        return g;
    }

    /**
     * This function searches for and gets a node n in linked list l
     * 
     * @param l Linked list to search for node
     * @param n Node to search fro in linked lsit
     * @return Node (if found), null (if not found)
     */
    public static Node getNode(LinkedList<Node> l, Node n) {
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).equals(n)) {
                return l.get(i);
            }
        }
        System.out.println("Node " + n.value + " doesn't exist. Returning null.");
        return null;
    }

    /**
     * This function searches for and gets a node with value v in linked list l
     * 
     * @param l Linked list to search
     * @param v Value to search for
     * @return Node with value v (if found),
     */
    public static Node getNode(LinkedList<Node> l, int v) {
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).value == v) {
                return l.get(i);
            }
        }
        System.out.println("Value " + v + " doesn't exist. Returning null.");
        return null;
    }
}

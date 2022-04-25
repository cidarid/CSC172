import java.util.LinkedList;

/**
 * The node object used by Graph
 */
public class Node implements Comparable<Node> {
    // Value of node
    public int value;
    // Nodes that the node is connected to
    public LinkedList<Node> connections = new LinkedList<>();

    // Used by Graph in influence isolate
    public int collectiveInfluence = 0;
    public boolean visited = false;

    public Node(int n) {
        this.value = n;
    }

    public Node(String s) {
        this.value = Integer.parseInt(s);
    }

    /**
     * Adds a connection between node n and this
     * 
     * @param n Node to connect to
     * @return True if successful, false if unsuccesful
     */
    public boolean addConnection(Node n) {
        return connections.add(n) && n.connections.add(this);
    }

    /**
     * Removes connection between this node and node n
     * 
     * @param n Node to remove connection
     * @return True if successful, false if unsuccessful
     */
    public boolean removeConnection(Node n) {
        return connections.remove(n);
    }

    public int getDegree() {
        return connections.size();
    }

    /**
     * Compares nodes, returning negative if smaller, positive if larger, 0 if the
     * same
     * 
     * @param n Node to compare to this
     * @return Result of compare
     */
    @Override
    public int compareTo(Node n) {
        if (this.value < n.value)
            return -1;
        else if (this.value > n.value)
            return 1;
        else
            return 0;
    }

    /**
     * Converts node to a string in the format node.value, [node.connections]
     * 
     * @return Returns node as string
     */
    @Override
    public String toString() {
        String s = Integer.toString(value) + ",";
        // Print out using [a, b, c, d] formatting
        if (connections.size() > 1) {
            s += "[";
            for (int i = 0; i < connections.size() - 1; i++) {
                s += (connections.get(i).value + ", ");
            }
            s += connections.get(connections.size() - 1).value + "]";
        } else if (connections.size() > 0) { // Only one connection
            s += "[" + connections.get(0).value + "]";
        }
        return s;
    }

    /**
     * Custom equals comparer that compares based on node.value
     */
    @Override
    public boolean equals(Object o) {
        Node n = (Node) o;
        if (o == this) {
            return true;
        }
        if (n.value == this.value) {
            return true;
        }
        return false;
    }

    /**
     * Gets all connections of string without also printing the connections of the
     * connection
     */
    public String getConnections() {
        String s = "[";
        for (int i = 0; i < connections.size() - 1; i++) {
            s += connections.get(i).value + ",";
        }
        s += connections.getLast().value + "]";
        return s;
    }
}

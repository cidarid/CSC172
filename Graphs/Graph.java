import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;
import java.util.Collections;

/**
 * This class handles the graph used in the program
 */
public class Graph {

    LinkedList<Node> vertices; // List of vertices

    /**
     * Constructor for graph
     */
    public Graph() {
        vertices = new LinkedList<>();
    }

    /**
     * Get the number of vertices
     */
    public int weight() {
        return vertices.size();
    }

    /**
     * Adds node to graph by adding to vertices
     * 
     * @param n Node to add
     */
    public void addNode(Node n) {
        if (!vertices.contains(n))
            vertices.add(n);
        Collections.sort(vertices);
    }

    /**
     * Removes node from graph by removing it from the list of vertices and
     * removing all connections to and from the node
     * 
     * @param n Node to remove
     */
    public void removeNode(Node n) {
        int size = n.connections.size();
        for (int i = size - 1; i >= 0; i--) {
            n.connections.get(i).removeConnection(n);
            n.removeConnection(n.connections.get(i));
        }
        vertices.remove(n);
    }

    /**
     * Isolates nodes from graph based on the number of links from each node.
     * We find the node with the most links and remove it, then repeat.
     * 
     * @param numToRemove Number of nodes to remove
     */
    public void degreeIsolate(int numToRemove) {
        // Go through enough times to remove as many nodes as indicated
        for (int i = 0; i < numToRemove; i++) {
            // Find the node with the highest degree
            Node maxNode = vertices.peekFirst();
            for (Node n : vertices) {
                if (n.getDegree() > maxNode.getDegree()) {
                    maxNode = n;
                }
            }

            // Print the node to be removed and its degree.
            // If -t was specified, also print out the connections from that node.
            System.out.println(maxNode.value + " " + maxNode.getDegree());
            if (StopContagion.trace) {
                System.out.println("Connected components of node that is being removed are "
                        + maxNode.getConnections());
            }
            // Remove the node with highest degree.
            removeNode(maxNode);
        }
    }

    /**
     * Isolates nodes from the graph based on the collective influence of each node.
     * We find the node with the highest collective influence and remove it.
     * 
     * @param numToRemove Number of nodes to remove
     * @param radius      Radius to use for collective influence caluclations
     */
    public void influenceIsolate(int numToRemove, int radius) {
        // Loops through vertices to get collective influence for each node
        for (Node n : vertices) {
            n.collectiveInfluence = findCollectiveInfluence(n, radius);
        }

        // Go through enough times to remove as many nodes as indicated
        for (int j = 0; j < numToRemove; j++) {
            // Find the node with the highest collective influence
            Node maxNode = vertices.peekFirst();
            for (Node n : vertices) {
                if (n.collectiveInfluence > maxNode.collectiveInfluence) {
                    maxNode = n;
                }
            }

            // We have to update collective influence of some nodes after we remove a node.
            // Specifically, we update nodes in Ball(n, r+1), where n is the removed node
            // and r is the radius. Here, we find the nodes we have to update
            LinkedList<Node> nodesToUpdate = new LinkedList<>();
            nodesToUpdate = findBall(maxNode, radius + 1);

            // Print the node to be removed and its collective influence.
            // If -t was specified, also print out the connections from that node.
            System.out.println(maxNode.value + " " + maxNode.collectiveInfluence);
            if (StopContagion.trace) {
                System.out.println("Connected components of node that is being removed are "
                        + maxNode.getConnections());
            }
            // Remove the max node.
            removeNode(maxNode);

            // Find the new collective influence of the nodes affected by removal.
            for (Node n : nodesToUpdate) {
                n.collectiveInfluence = findCollectiveInfluence(n, radius);
            }
        }

    }

    /**
     * Gets the list of nodes with a shortest path less than or equal to a specified
     * radius from some node.
     * Uses a modified BFS to keep track of distance from source.
     * 
     * @param s      The source node
     * @param radius The radius to use
     * @return The list of nodes
     */
    public LinkedList<Node> findBall(Node s, int radius) {
        // List of nodes whose shortest path to s is less than or equal to the radius.
        LinkedList<Node> ball = new LinkedList<>();
        // Queue to use in BFS
        Queue<LinkedList<Object>> queue = new LinkedList<>();
        // Set visited to false for all the nodes
        for (Node n : vertices)
            n.visited = false;

        // Visit the source
        s.visited = true;
        // Add a LinkedList with the source and the distance from the source to the
        // queue
        queue.add(new LinkedList<Object>(Arrays.asList(s, 0)));
        // While there are unvisited nodes
        while (!queue.isEmpty()) {
            // Pop the first object from the queue and save it as tempL
            LinkedList<Object> tempL = queue.remove();
            // Get the node from tempL
            Node tempNode = (Node) tempL.remove();
            // Get the distance from source from tempL
            int distanceFromSource = (int) tempL.remove();

            // If the distance from source is less than or equal to the radius,
            // save the node to be returned later
            if (distanceFromSource <= radius) {
                ball.add(tempNode);
                // If the distance from source equals the radius, don't need to go further
                if (distanceFromSource == radius)
                    continue;
            }

            // Add all the unvisited connections from the current node along with an updated
            // distance from source back into the queue
            for (Node _n : tempNode.connections) {
                if (!_n.visited) {
                    _n.visited = true;
                    queue.add(new LinkedList<Object>(Arrays.asList(_n, distanceFromSource + 1)));
                }
            }
        }

        return ball;
    }

    /**
     * Finds collective influence using a modified breadth first search (BFSh)
     * 
     * @param s      Node to find collective influence for
     * @param radius Radius of links from original node
     * @return Collective influence
     */
    public int findCollectiveInfluence(Node s, int radius) {
        int collectiveInfluence = 0;
        // Set of nodes whose shortest path to s is exactly r
        LinkedList<Node> deltaBall = new LinkedList<>();
        // For BFSh
        Queue<LinkedList<Object>> queue = new LinkedList<>();
        // Sets nodes to unvisited
        for (Node n : vertices)
            n.visited = false;

        // Sets initial node to visited, do not need to visit
        s.visited = true;
        // Adds linked list of source and distance from the source
        queue.add(new LinkedList<Object>(Arrays.asList(s, 0)));
        // While items remain
        while (!queue.isEmpty()) {
            // Pop first object into tempL
            LinkedList<Object> tempL = queue.remove();
            // Retrieve node from templ
            Node tempNode = (Node) tempL.remove();
            // Retrieve distance from source from tempL
            int distanceFromSource = (int) tempL.remove();

            // If the distance from source is the radius, add to deltaBall and continue
            if (distanceFromSource == radius) {
                deltaBall.add(tempNode);
                continue;
            }

            // If the distance does not equal the radius, find all of its univisited
            // connections to try to find one that does and add them to the queue with
            // updated distanceFromSource.
            for (Node _n : tempNode.connections) {
                if (!_n.visited) {
                    _n.visited = true;
                    queue.add(new LinkedList<Object>(Arrays.asList(_n, distanceFromSource + 1)));
                }
            }
        }

        // Does math to get collective influence
        for (Node n : deltaBall) {
            collectiveInfluence += (n.getDegree() - 1);
        }
        collectiveInfluence *= (s.getDegree() - 1);

        return collectiveInfluence;
    }

    /**
     * Returns the graph as a string of all of its nodes as strings, with each node
     * on a different line.
     */
    @Override
    public String toString() {
        String s = "";
        for (Node n : vertices) {
            s += (n + "\n");
        }
        return s;
    }

}

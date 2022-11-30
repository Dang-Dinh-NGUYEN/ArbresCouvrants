import org.w3c.dom.Node;

import java.util.*;

/* créer un arbre couvrant de poids minimum en utilisant l'algorithme de Prim*/
public class MinSpanningTree {
    Graph graph;
    ArrayList<Arc> frontier;
    ArrayList<Arc> tree;
    Boolean[] reached;

    private void push(int vertex) {
        frontier.addAll(graph.outNeighbours(vertex));
    }

    private Arc minWeight(){
        int minWeight = (int) frontier.get(0).support.weight;
        Arc selected = frontier.get(0);
        for (Arc arc : frontier){
            if((int) arc.support.weight < minWeight){
                minWeight = (int) arc.support.weight;
                selected = arc;
            }
        }
        return selected;
    }

    private void explore(Arc nextArc) {
        if (reached[nextArc.getDest()]) return;
        reached[nextArc.getDest()] = true;
        tree.add(nextArc);
        push(nextArc.getDest());
    }

    private void minWeightWalker(int startingVertex) {
        reached[startingVertex] = true;
        frontier.addAll(graph.outNeighbours(startingVertex));
        while (tree.size() < graph.order - 1) {
            Arc currentRoot = minWeight();
            explore(currentRoot);
            frontier.remove(currentRoot);
        }
    }

    private MinSpanningTree (Graph graph) {
        this.graph = graph;
        Iterator<Edge> iterator = this.graph.iterator();
        while(iterator.hasNext()){
            Edge currentEdge = iterator.next();
            currentEdge.weight = new Random().nextInt(2);
        }
        this.frontier = new ArrayList<>();
        this.tree = new ArrayList<>();
        this.reached = new Boolean[graph.order];
        for(int i = 0; i < graph.order; i++) reached[i] = false;
    }

    public static ArrayList<Arc> generateTreePrim(Graph graph, int root) {
        MinSpanningTree algo = new MinSpanningTree(graph);
        algo.minWeightWalker(root);
        //System.out.println(algo.tree.size() + " " + algo.reached.length + " " + graph.order);
        return algo.tree;
    }

    /*************************************************** Dijkstra *****************************************************/
    /*On se realise les similarités entre l'algorithme de Prim et l'un de Dijkstra. En fait, l'algorithme de Dijkstra
    * passera tous les sommets du graphe avec un plus court chemin, autrement dit poids minimum. Lorsqu'on sauvegarde
    * les arcs passés par Dijkstra, on obtient un arbre couvrant de poids minimum*/

    public void DijkstraShortestPath(int start, int end) {
        // We keep track of which path gives us the shortest path for each node
        // by keeping track how we arrived at a particular node, we effectively
        // keep a "pointer" to the parent node of each node, and we follow that
        // path to the start
        HashMap<Integer, Integer> changedAt = new HashMap<>();
        changedAt.put(start, null);

        // Keeps track of the shortest path we've found so far for every node
        HashMap<Integer, Double> shortestPathMap = new HashMap<>();

        // Setting every node's shortest path weight to positive infinity to start
        // except the starting node, whose shortest path weight is 0
        for (int node = 0; node < graph.order; node++) {
            if (node == start)
                shortestPathMap.put(start, 0.0);
            else shortestPathMap.put(node, Double.POSITIVE_INFINITY);
        }

        // Now we go through all the nodes we can go to from the starting node
        // (this keeps the loop a bit simpler)
        for (Arc edge : graph.outNeighbours(start)) {
            shortestPathMap.put(edge.getDest(), edge.support.weight);
            changedAt.put(edge.getDest(), start);
        }

        reached[start] = true;

        // This loop runs as long as there is an unvisited node that we can
        // reach from any of the nodes we could till then
        int count = 0;
        while (true) {
            int currentNode = closestReachableUnvisited(shortestPathMap);
            count++;
            //System.out.println(currentNode);

            // If we haven't reached the end node yet, and there isn't another
            // reachable node the path between start and end doesn't exist
            // (they aren't connected)
            if (currentNode == Integer.MIN_VALUE) {
                System.out.println(count);
                return;
            }

            // If the closest non-visited node is our destination
            if (currentNode == end - 1) {
                System.out.println("ending here");
                int child = end - 1;

                LinkedList<Integer> path = new LinkedList<>();
                path.addFirst(end);
                while (true) {
                    try{
                        // Since our changedAt map keeps track of child -> parent relations
                        // in order to print the path we need to add the parent before the child and
                        // it's descendants
                        int parent = changedAt.get(child);
                        path.addFirst(parent);
                        //if(containsArc(new Arc(new Edge(parent,child,0),false)))
                        tree.add(new Arc(new Edge(parent,child,0),false));
                        System.out.println(tree.size());
                        child = parent;
                    }catch (NullPointerException n){return;}
                }
            }
            reached[currentNode] = true;

            // Now we go through all the unvisited nodes our current node has an edge to
            // and check whether its shortest path value is better when going through our
            // current node than whatever we had before
            for (Arc edge : graph.outNeighbours(currentNode)) {
                if (reached[edge.getDest()])
                    continue;

                if (shortestPathMap.get(currentNode)
                        + edge.support.weight
                        < shortestPathMap.get(edge.getDest())) {
                    shortestPathMap.put(edge.getDest(),
                            shortestPathMap.get(currentNode) + edge.support.weight);
                    changedAt.put(edge.getDest(), currentNode);

                }
            }
        }

    }

    private Integer closestReachableUnvisited(HashMap<Integer, Double> shortestPathMap) {

        double shortestDistance = Double.POSITIVE_INFINITY;
        int closestReachableNode = Integer.MIN_VALUE;
        for(int node = 0; node < graph.order; node++){
            if (reached[node])
                continue;

            double currentDistance = shortestPathMap.get(node);
            if (currentDistance == Double.POSITIVE_INFINITY)
                continue;

            if (currentDistance < shortestDistance) {
                shortestDistance = currentDistance;
                closestReachableNode = node;
            }
        }
        //System.out.println(closestReachableNode);
        return closestReachableNode;
    }

    public boolean containsArc(Arc arc){
        for(Arc a : tree){
            if(a.getSource() == arc.getSource() && a.getDest() == arc.getDest()) return true;
        }
        return false;
    }

    public static ArrayList<Arc> generateTreeDijskstra(Graph graph, int root) {
        MinSpanningTree algo = new MinSpanningTree(graph);
        algo.DijkstraShortestPath(0, graph.order);
        System.out.println(algo.tree.size() + " " + algo.reached.length + " " + graph.order);
        return algo.tree;
    }
}


import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.LinkedList;

/* parcours en largeur aléatoire */
public class RandomSearch {
    Graph graph;
    ArrayList<Arc> frontier;
    ArrayList<Arc> tree;
    BitSet reached;

    private void push(int vertex) {
        for (Arc arc : graph.outNeighbours(vertex)) frontier.add(arc);
    }

    private void explore(Arc nextArc) {
        if (reached.get(nextArc.getDest())) return;
        reached.set(nextArc.getDest());
        tree.add(nextArc);
        push(nextArc.getDest());
    }

    private void bfs(int startingVertex) {
        reached.set(startingVertex);
        push(startingVertex);
        while (!frontier.isEmpty()) {
            Collections.shuffle(frontier);
            explore(frontier.remove(0));
        }
    }

    private RandomSearch (Graph graph) {
        this.graph = graph;
        this.frontier = new ArrayList<>();
        this.tree = new ArrayList<>();
        this.reached = new BitSet(graph.order);
    }

    public static ArrayList<Arc> generateTree(Graph graph, int root) {
        RandomSearch algo = new RandomSearch(graph);
        algo.bfs(root);
        return algo.tree;
    }
}

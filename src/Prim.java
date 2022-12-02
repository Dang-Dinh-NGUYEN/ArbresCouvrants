import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Prim implements MinSpanningTree{
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

    public Prim (Graph graph) {
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

    public ArrayList<Arc> generateTree(Graph graph, int root) {
        Prim algo = new Prim(graph);
        algo.minWeightWalker(root);
        return algo.tree;
    }
}

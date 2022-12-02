import java.util.*;

public class RandomInsert {
    Graph graph;
    ArrayList<Arc> tree;
    List<Edge> frontier;

    public RandomInsert(Graph graph){
        this.graph = graph;
        this.tree = new ArrayList<>(graph.order);
        this.frontier = new ArrayList<>();
        Iterator<Edge> iterator = this.graph.iterator();
        while(iterator.hasNext()){
            Edge currentEdge = iterator.next();
            frontier.add(currentEdge);
        }
    }

    private class SubSet{
        int parent,rank;
    }

    /* Vérifier si l'arc au courant contient un cycle*/
    private int find(RandomInsert.SubSet SubSets[], int i) {
        if (SubSets[i].parent != i)
            SubSets[i].parent = find(SubSets, SubSets[i].parent);
        return SubSets[i].parent;
    }

    private void Union(RandomInsert.SubSet SubSets [], int x, int y) {
        int xroot = find(SubSets , x);
        int yroot = find(SubSets , y);

        if (SubSets [xroot].rank < SubSets [yroot].rank)
            SubSets [xroot].parent = yroot;
        else if (SubSets [xroot].rank > SubSets [yroot].rank)
            SubSets [yroot].parent = xroot;
        else {
            SubSets [yroot].parent = xroot;
            SubSets [xroot].rank++;
        }
    }

    private void execute(){
        Edge result[] = new Edge[graph.order];
        int e = 0;

        SubSet subsets[] = new SubSet[graph.order];
        for (int i = 0; i < graph.order; ++i)
            subsets[i] = new SubSet();

        for (int v = 0; v < graph.order; ++v) {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }
        while (e < graph.order - 1) {
            Edge next_edge = frontier.get(new Random().nextInt(frontier.size()));
            int x = find(subsets, next_edge.source);
            int y = find(subsets, next_edge.dest);
            if (x != y) {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
            frontier.remove(next_edge);
        }
        for (int i = 0; i < e; ++i)
            tree.add(new Arc(result[i],false));
    }

    public static ArrayList<Arc> generateTree(Graph graph, int root){
        RandomInsert algo = new RandomInsert(graph);
        algo.execute();
        return  algo.tree;
    }
}

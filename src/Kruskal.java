import java.util.*;

public class Kruskal implements MinSpanningTree{
    Graph graph;
    ArrayList<Arc> tree;
    List<Edge> frontier;

    public Kruskal(Graph graph){
        this.graph = graph;
        this.frontier = new LinkedList<>();
        Iterator<Edge> iterator = this.graph.iterator();
        while(iterator.hasNext()){
            Edge currentEdge = iterator.next();
            currentEdge.weight = new Random().nextInt(2);
            frontier.add(currentEdge);
        }
        Collections.sort(frontier);
        this.tree = new ArrayList<>(graph.order);
    }

    private class SubSet{
        int parent,rank;
    }

    private int find(SubSet SubSets[], int i) {
        if (SubSets[i].parent != i)
            SubSets[i].parent = find(SubSets, SubSets[i].parent);
        return SubSets[i].parent;
    }

    private void Union(SubSet SubSets [], int x, int y) {
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
        int i = 0;

        SubSet subsets[] = new SubSet[graph.order];
        for (i = 0; i < graph.order; ++i)
            subsets[i] = new SubSet();

        for (int v = 0; v < graph.order; ++v) {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }
        i = 0;
        while (e < graph.order - 1) {
            Edge next_edge = frontier.get(i++);
            int x = find(subsets, next_edge.source);
            int y = find(subsets, next_edge.dest);
            if (x != y) {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
        }
        for (i = 0; i < e; ++i)
            tree.add(new Arc(result[i],false));
    }

    public ArrayList<Arc> generateTree(Graph graph, int root){
        Kruskal algo = new Kruskal(graph);
        algo.execute();
        return  algo.tree;
    }
}

import java.util.*;

public class RandomInsert extends SubSet{
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

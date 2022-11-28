import java.util.*;

public class AldousBroder {
    Graph graph;
    ArrayList<Arc> tree;
    int sommetActuel;
    HashMap<Integer,Integer> reached;

    private void randomWalker(){
        while(reached.containsValue(0)){
            Arc direction = graph.outNeighbours(sommetActuel).get(new Random().nextInt(graph.outNeighbours(sommetActuel).size()));
            sommetActuel = direction.getDest();
            reached.put(sommetActuel, reached.get(sommetActuel) + 1);
            if(reached.get(sommetActuel) == 1){
                tree.add(direction);
            }
        }
    }

    private AldousBroder(Graph graph){
        this.graph = graph;
        this.tree = new ArrayList<>();
        this.sommetActuel = new Random().nextInt(graph.order);
        this.reached = new HashMap<>();
        for(int i = 0; i < graph.order; i++){
            reached.put(i,0);
        }
    }

    public static ArrayList<Arc> generateTree(Graph graph){
        AldousBroder algo =  new AldousBroder(graph);
        algo.randomWalker();
        return algo.tree;
    }
}

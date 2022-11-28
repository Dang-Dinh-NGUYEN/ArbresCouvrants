import java.util.*;

public class MinSpanningTree {
    Graph graph;
    ArrayList<Arc> tree = new ArrayList<>();
    ArrayList<Arc> arcs = new ArrayList<>();
    int root;
    BitSet reached;

    private int min_weight() {
        int minWeight = (int) arcs.get(0).support.weight;
        Arc arcMin = arcs.get(0);
        for(Arc arc : arcs){
            if (arc.support.weight < minWeight && !reached.get(arc.getDest())){
                minWeight = (int) arc.support.weight;
                arcMin = arc;
            }
        }
        tree.add(arcMin);
        arcs.remove(arcMin);
        System.out.println(arcs.size());
        return  arcMin.getDest();
    }

    private void randomWalker(int root){
        reached.set(root);

        while(tree.size() < graph.order) {
            for (Arc arc : graph.outNeighbours(root)){
                if(!reached.get(arc.getDest())) {
                    reached.set(arc.getDest());
                    arcs.add(arc);
                    //System.out.println(arcs.size());
                }
            }
            root = min_weight();
            if(arcs.size() == 0) return;
        }
    }

    private MinSpanningTree(Graph graph, int root){
        this.graph = graph;

        /* attribuer à chaque sommet un poids aléatoire */
        Iterator<Edge> iterator = this.graph.iterator();
        while(iterator.hasNext()){
            Edge currentEdge = iterator.next();
            currentEdge.weight = new Random().nextInt(2);
        }

        this.reached = new BitSet(graph.order);
    }

    public static ArrayList<Arc> generateTree(Graph graph,int root){
        MinSpanningTree algo =  new MinSpanningTree(graph,root);
        algo.randomWalker(root);
        return algo.tree;
    }
}

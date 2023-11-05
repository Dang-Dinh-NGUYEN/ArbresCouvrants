import java.util.*;

public class Wilson {
    Graph graph;
    ArrayList<Arc> tree;
    int V; //taille du graph
    HashSet<Integer> reached;
    HashSet<Integer> remains;
    Random random = new Random();

    private Wilson(Graph graph){
        this.graph = graph;
        this.V = graph.order;
        this.tree = new ArrayList<>(V);
        this.reached = new HashSet<>();
        this.remains = new HashSet<>();
        for(int i = 0; i < V; i++)
            remains.add(i);

    }

    private void explore(Arc[] path, int vertex){
        ArrayList<Integer> visitedVertex = new ArrayList<>();
        visitedVertex.add(vertex);
        while(!reached.contains(vertex)) {
            Arc next_edge = graph.outNeighbours(vertex).get(random.nextInt(graph.outNeighbours(vertex).size()));
            /* vérifier s'il y a un cycle. Si oui, supprimer les arcs dans ce cycle */
            if(visitedVertex.contains(next_edge.getSource())){
                for(int i = visitedVertex.indexOf(next_edge.getSource()); i < visitedVertex.size(); i++){
                    path[visitedVertex.get(i)] = null;
                    visitedVertex.remove(i);
                }
            }
            path[next_edge.getSource()] = next_edge;
            vertex = next_edge.getDest();
            visitedVertex.add(vertex);
        }
    }

    public void execute() {
        /* choisir un sommet de degré maximum */
        int root = 0;
        int maxDegree = graph.degree(root);
        for (int i = 1; i < V; i++) {
            if (graph.degree(i) > maxDegree) {
                maxDegree = graph.degree(i);
                root = i;
            }
        }
        reached.add(root);
        remains.remove(root);

        while (remains.size() > 0) {

            /* choisir un sommet qui n'est pas dans l'arbre */
            Integer[] remainsAsArray = remains.toArray(new Integer[remains.size()]);
            int u = remainsAsArray[random.nextInt(remains.size())];
            while (reached.contains(u)) {
                u =remainsAsArray[random.nextInt(remains.size())];
            }

            Arc[] path = new Arc[V];
            /* marche aléatoire depuis u */
            explore(path, u);
            for (int i = 0; i < V; i++) {
                if(path[i] != null) {
                    tree.add(path[i]);
                    int src = path[i].getSource();
                    int dst = path[i].getDest();
                    reached.add(path[i].getSource());
                    reached.add(path[i].getDest());
                    remains.remove(src);
                    remains.remove(dst);
                }
            }
        }
    }

    public static ArrayList<Arc> generateTree(Graph graph){
        Wilson algo = new Wilson(graph);
        algo.execute();
        return algo.tree;
    }

}

import java.util.*;

//parcours en largeur (version normal et version aléatoire)
public class BreadthFirstSearch {

	Graph graph;
	Queue<Arc> frontier;
	ArrayList<Arc> tree;
	BitSet reached;
	
	private void push(int vertex) {
		for (Arc arc : graph.outNeighbours(vertex)) frontier.offer(arc);
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
			explore(frontier.poll());
		}
	}
	
	private BreadthFirstSearch (Graph graph) {
		this.graph = graph;
		this.frontier = new LinkedList<>();
		this.tree = new ArrayList<>();
		this.reached = new BitSet(graph.order);
	}
	
	public static ArrayList<Arc> generateTree(Graph graph, int root) {
		BreadthFirstSearch algo = new BreadthFirstSearch(graph);
		algo.bfs(root);
		return algo.tree;
	}

	/*********************************************** Random Walker ****************************************************/
	private void random_push(int vertex) {
		/* ajouter les arcs sortants d'un sommet dans la frontier en ordre aléatoire */
		Collections.shuffle(graph.outNeighbours(vertex));
		for (Arc arc : graph.outNeighbours(vertex)) frontier.offer(arc);
	}

	private void random_explore(Arc nextArc) {
		if (reached.get(nextArc.getDest())) return;
		reached.set(nextArc.getDest());
		tree.add(nextArc);
		random_push(nextArc.getDest());
	}

	private void random_bfs(int startingVertex) {
		reached.set(startingVertex);
		push(startingVertex);
		while (!frontier.isEmpty()) {
			random_explore(frontier.poll());
		}
	}

	public static ArrayList<Arc> generateTreeAtRandom(Graph graph) {
		BreadthFirstSearch algo = new BreadthFirstSearch(graph);
		int root = new Random().nextInt(graph.order);
		algo.random_bfs(root);
		return algo.tree;
	}

}

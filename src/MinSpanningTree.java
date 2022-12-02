import org.w3c.dom.Node;

import java.util.*;

/* créer un arbre couvrant de poids minimum en utilisant l'algorithme de Prim/ Kruskal */
/*
public class MinSpanningTree {
   public static ArrayList<Arc> generateTreePrim(Graph graph, int root){
       return Prim.generateTreePrim(graph,root);
   }

    public static ArrayList<Arc> generateTreeKruskal(Graph graph){
        return Kruskal.generateTree(graph);
    }
 */
 public interface MinSpanningTree {
     ArrayList<Arc> generateTree(Graph graph,int root);
}


/*cette classe se serve à gérer des cycles dans un graphe*/
public class SubSet {
    int parent,rank;

    public int find(SubSet SubSets[], int i) {
        if (SubSets[i].parent != i)
            SubSets[i].parent = find(SubSets, SubSets[i].parent);
        return SubSets[i].parent;
    }

    public void Union(SubSet SubSets [], int x, int y) {
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
}

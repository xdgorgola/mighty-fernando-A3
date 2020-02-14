import java.util.LinkedList;
import java.util.ListIterator;

public class BFS {

    private Grafo graph;

    /**
     * Does the activity with BFS
     * 
     * @param origin If the origins of the nodes are calculated
     * @param trunc  How deep is the algorithm going to explore
     * @param arb    If the algorithm route is showed up
     * @param ord    If the appearance order of the nodes are calculated
     * @param pred   If the predecessors of the nodes are calculated
     */
    public void DoNodeBFS(int origin, int trunc, boolean arb, boolean ord, boolean pred) {
        // Tracks how many paths have been created
        int count = 0;

        // No truncating
        if (trunc == 0) {
            trunc -= 1;
        }

        LinkedList<NodePath> open = new LinkedList<NodePath>();
        LinkedList<NodePath> closed = new LinkedList<NodePath>();

        LLNode initial = graph.GetNode(origin);
        open.addLast(new NodePath(origin));
        initial.SetColor(1);

        if (pred) {
            initial.SetImmediatePred(origin);
        }
        if (ord) {
            initial.SetOrd(0);
        }
        if (arb) {
            System.out.println(initial.GetID() + "-" + initial.GetID() + " (Raiz)");
        }

        while (open.size() > 0 && (open.peekFirst().GetPath().size() - 1) != trunc) {

            NodePath actPath = open.peekFirst();
            LLNode act = graph.GetNode(actPath.GetLast());

            // We get the path last node adjacents
            LinkedList<Integer> adjAct = act.GetSucesors();
            for (Integer id : adjAct) {
                if (ord) {
                    count += 1;
                }
                LLNode expNode = graph.GetNode(id);
                if (pred) {
                    expNode = graph.GetNode(id);
                    if (expNode.GetImmediatePred() == -1) {
                        expNode.SetImmediatePred(act.GetID());
                    }
                }
                if (ord) {
                    if (expNode.GetOrd() == -1) {
                        expNode.SetOrd(count);
                    }
                }
                LLNode adj = graph.GetNode(id);
                // We mark the adj as visited node
                adj.SetColor(1);

                // We create the new path
                NodePath expanded = actPath.Clone();
                expanded.AddNext(id);

                // We check if it should be removed
                if (!RemovePath(expanded, open, closed, arb)) {
                    open.addLast(expanded);
                }
            }

            open.removeFirst();
            closed.addLast(actPath);
        }
        if (pred) {
            System.out.println("Pred:");
            for (LLNode v : graph.GetGraph()) {
                System.out.println(v.GetID() + ": " + v.GetImmediatePred());
            }
        }
        if (ord) {
            System.out.println("Ord:");
            for (LLNode v : graph.GetGraph()) {
                System.out.println(v.GetID() + ": " + v.GetOrd());
            }
        }
        LinkedList<Integer> unreacheable = new LinkedList<Integer>();
        for (LLNode v : graph.GetGraph()) {
            if (v.GetColor() == 0) {
                unreacheable.add(v.GetID());
            }
        }
        if (unreacheable.isEmpty()) {
            System.out.println("All the nodes are reachable!");
        } else {
            System.out.println("Unreacheable nodes");
            for (Integer unr : unreacheable) {
                if (unr != unreacheable.getLast()) {
                    System.out.print(unr + ", ");
                } else {
                    System.out.print(unr + "\n");
                }
            }
        }
    }

    /**
     * Checks if a path should be removed/ignored in the BFS path and process the node type.
     * 
     * @param path Path to examine
     * @param open Open paths
     * @param closed Closed paths
     * @param arb If the algorithm route must be showed
     * @return true if the path is removed/else false
     */
    public boolean RemovePath(NodePath path, LinkedList<NodePath> open, LinkedList<NodePath> closed, 
                              boolean arb) {
        for (NodePath openPath : open) {
            if (openPath.GetLast() == path.GetLast()) {
                if (arb) {
                    ProcessSpecialArc(path, open, closed);
                }
                return true;
            }
        }
        for (NodePath closedPath : closed) {
            if (closedPath.GetLast() == path.GetLast()) {
                if (arb) {
                    ProcessSpecialArc(path, open, closed);
                }
                return true;
            }
        }
        if (arb) {
            int depth = path.GetPath().size();
            String tabuleo = new String(new char[depth]).replace("\0", " ");
            System.out.println(tabuleo + path.GetPrevLast() + "-" + path.GetLast() + "  (Arco de camino)");
        }
        return false;
    }

    /**
     * Checks if a path is a return path.
     * 
     * @param path Path to check
     * @return If the path is a return path or not
     */
    public boolean IsGoingUp(NodePath path) {
        LinkedList<Integer> intPath = path.GetPath();
        ListIterator<Integer> iterator = intPath.listIterator();
        while (iterator.hasNext()) {
            Integer act = iterator.next();
            if (!iterator.hasNext()) {
                return false;
            }
            if (act == path.GetLast()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a path is a crossed path.
     * 
     * @param path Path to check
     * @param open Open paths
     * @param closed Closed paths
     * @return If the path is crossed or not
     */
    public boolean IsOtherCrossed(NodePath path, LinkedList<NodePath> open, LinkedList<NodePath> closed) {
        for (NodePath closedPath : closed) {
            if (closedPath.GetLast() == path.GetLast() && closedPath != path) {
                return true;
            }
        }
        for (NodePath openPath : open) {
            if (openPath.GetLast() == path.GetLast() && openPath != path) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks the type of a path to remove in BFS and prints its type accordingly.
     * 
     * @param path Path to process
     * @param open Open paths
     * @param closed Closed paths
     */
    public void ProcessSpecialArc(NodePath path, LinkedList<NodePath> open, LinkedList<NodePath> closed) {
        int depth = path.GetPath().size();
        String tabuleo = new String(new char[depth]).replace("\0", " ");
        if (IsGoingUp(path)) {
            System.out.println(tabuleo + path.GetPrevLast() + "-" + path.GetLast() + "  (Arco de subida)");
        } else if (IsOtherCrossed(path, open, closed)) {
            System.out.println(tabuleo + path.GetPrevLast() + "-" + path.GetLast() + "  (Arco cruzado)");
        }
    }
}
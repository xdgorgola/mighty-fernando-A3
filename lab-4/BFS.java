import java.util.LinkedList;
import java.util.ListIterator;

public class BFS {

    private Grafo graph;

    public void DoNodeBFS(int origin, int trunc, boolean arb, boolean ord, boolean pred) {
        int appo = 0;
        if (trunc == 0) {
            trunc -= 1;
        }
        LinkedList<NodePath> open = new LinkedList<NodePath>();
        LinkedList<NodePath> closed = new LinkedList<NodePath>();
        LLNode initial = graph.GetNode(origin);
        initial.SetImmediatePred(origin);
        initial.SetOrd(0);
        open.addLast(new NodePath(origin));

        initial.SetColor(1);

        System.out.println(initial.GetID() + "-" + initial.GetID());

        while (open.size() > 0 && (open.peekFirst().GetPath().size() - 1) != trunc) {
            System.out.println("Path size: " + open.peekFirst().GetPath().size());

            NodePath actPath = open.peekFirst();
            LLNode act = graph.GetNode(actPath.GetLast());

            // We get the path last node adjacents
            LinkedList<Integer> adjAct = act.GetSucesors();
            for (Integer id : adjAct) {
                appo += 1;
                LLNode expNode = graph.GetNode(id);
                if (pred) {
                    expNode = graph.GetNode(id);
                    if (expNode.GetImmediatePred() == -1) {
                        expNode.SetImmediatePred(act.GetID());
                    }
                }
                if (ord) {
                    if (expNode.GetOrd() == -1) {
                        expNode.SetOrd(appo);
                    }
                }
                LLNode adj = graph.GetNode(id);
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

    }

    public boolean RemovePath(NodePath path, LinkedList<NodePath> open, LinkedList<NodePath> closed, boolean arb) {
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
            System.out.println(tabuleo + path.GetPrevLast() + "-" + path.GetLast() + "  (Es caminito lindo)");
        }
        return false;
    }

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

    public void ProcessSpecialArc(NodePath path, LinkedList<NodePath> open, LinkedList<NodePath> closed) {
        int depth = path.GetPath().size();
        String tabuleo = new String(new char[depth]).replace("\0", " ");
        if (IsGoingUp(path)) {
            System.out.println(tabuleo + path.GetPrevLast() + "-" + path.GetLast() + "  (Este va parriba)");
        } else if (IsOtherCrossed(path, open, closed)) {
            System.out.println(tabuleo + path.GetPrevLast() + "-" + path.GetLast() + "  (Un ligaito ahi)");
        }
    }

    public static void main(String[] args) {
        Grafo g = new Grafo();

        for (int i = 0; i < 8; i++) {
            g.InsertNode(i);
        }

        g.AddEdge(0, 2);
        g.AddEdge(0, 5);
        g.AddEdge(0, 7);
        g.AddEdge(2, 6);
        g.AddEdge(3, 4);
        g.AddEdge(4, 5);
        g.AddEdge(4, 7);
        g.AddEdge(5, 3);
        g.AddEdge(6, 4);
        g.AddEdge(6, 2);
        g.AddEdge(7, 1);

        BFS bfs = new BFS();
        bfs.graph = g;
        bfs.DoNodeBFS(0, 2, true, true, true);

        System.out.println("Nodos no alcanzables uwu");
        for (LLNode v : g.GetGraph()) {
            if (v.GetColor() == 0) {
                System.out.println(v.GetID());
            }
        }
        System.out.println("Orden de ap y pred nodos");
        for (LLNode v : g.GetGraph()) {
            System.out.println("Aparicion de de " + v.GetID());
            System.out.println(v.GetOrd());
            System.out.println("Predecesor de " + v.GetID());
            System.out.println(v.GetImmediatePred());
            System.out.println();
        }
    }
}
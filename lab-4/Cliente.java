import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Cliente {

    static void loadEdge(String line, Grafo grafo) throws IllegalArgumentException {
		String[] vertices = line.split(" ");
		if (vertices.length > 1) {
			for (int i = 1; i < vertices.length; i++) {
				grafo.AddEdge(Integer.parseInt(vertices[0]), Integer.parseInt(vertices[i]));
			}
		}
    }
    
    static Grafo loadGraph(String fileName) throws IOException {
		Grafo salida = new Grafo();
		int vertices;

		BufferedReader Lector = new BufferedReader(new FileReader(fileName));

		String linea = Lector.readLine();
		vertices = Integer.parseInt(linea);

		for (int i = 0; i < vertices; i++) {
			salida.InsertNode(i);
		}

		linea = Lector.readLine();

		linea = Lector.readLine();
		do {
			loadEdge(linea, salida);
			linea = Lector.readLine();
		} while (linea != null);

		Lector.close();

		return salida;
    }
    
    public static void main(String[] args) throws IOException {
        System.out.println(args.length);
        if (args.length < 3 || args.length > 8) {
            System.out.println("Introduzca al menos un comando o menos de 6.");
            System.out.println("Saliendo del programa.");
            return;
        }

        boolean dfs = false;
        boolean bfs = false;
        int origen = 0;
        try {
            origen = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Introduzca un numero");
            return;
        }

        if (args[2].equalsIgnoreCase("dfs")){
            dfs = true;
        }
        else if (args[2].equalsIgnoreCase("bfs")){
            bfs = true;
        }
        else{
            System.out.println("Elige un modo valido");
            return;
        }

        // Chequear que no se repita ninguno
        // Chequear que si hay trun el siguiente sea un int (mayor a 0)
        // Chequear que los comandos sean los que son
        boolean tru = false;
        boolean arb = false;
        boolean ord = false;
        boolean pre = false;

        int len = -1;

        // Lets chequear that ninguno ta repetio
        for (int i = 0; i < args.length - 1; i++) {
            for (int j = i + 1; j < args.length; j++) {
                if (args[i].equalsIgnoreCase(args[j])) {
                    System.out.println("Argumento repetido.");
                    return;
                }
            }
        }

        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("--arb")) {
                if (arb) {
                    return;
                }
                arb = true;
            } else if (args[i].equalsIgnoreCase("--ord")) {
                if (ord) {
                    return;
                }
                ord = true;
            } else if (args[i].equalsIgnoreCase("--pred")) {
                if (pre) {
                    return;
                }
                pre = true;
            } else if (args[i].equalsIgnoreCase("--trun")) {
                if (tru) {
                    return;
                }
                if (i + 1 >= args.length) {
                    return;
                }
                try {
                    if (len != -1){
                        return;
                    }
                    len = Integer.parseInt(args[i+1]);
                    i += 1;
                    tru = true;
                } catch (NumberFormatException e) {
                    System.out.println("Tiene que ser numero despues de trun");
                    return;
                }
            }
        }
        System.out.println("dfs: " + dfs);
        System.out.println("bfs: " + bfs);
        System.out.println("trun: " + tru + " " + len);
        System.out.println("arb: " + arb);
        System.out.println("ord: " + ord);
        System.out.println("pre: " + pre);
        System.out.println("dfs: " + dfs);

        Grafo g = loadGraph(args[0]);
        // Se carga el grafo
        // Has un metodo para que el DFS/BFS reciban el grafo y luego lo llamas, el mio necesita que le pases los parametros.
        // SI NO HAY TRUN, EL MIO RECIBE COMO PARAMETRO TRUN -1
        if (dfs){
            DFS doer = new DFS(g);
            if (tru){
                doer.SetTrunc(len);
            }
            doer.DFSForest(origen);
            doer.DFSConnected(arb, ord, pre);
        }
        else{
            BFS doer = new BFS();
            doer.DoNodeBFS(origen, len, arb, ord, pre);
        }
    }
}
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Cliente {

    private ArrayList<Grafo> grafosND = new ArrayList<Grafo>();
    private ArrayList<Grafo> grafosD = new ArrayList<Grafo>();
    private Scanner scan = new Scanner(System.in);

    /**
     * Ciclo del menu principal de la agrupacion donde se pueden acceder a las siguientes opciones:
     * <ul>
     * <li>Cargar grafo desde archivo</li>
     * <li>Ver grafos guardados</li>
     * <li>Salir del programa</li>
     * </ul>
     */
    public void menuPrincipal() {
        String opcion = "0";
        while (opcion != "-1") {
            System.out.println("1) Cargar grafo desde archivo.");
            System.out.println("2) Ver grafos guardados.");
            System.out.println("-1) Salir del programa.");
        }
    }

    /**
     * Intenta cargar un grafo desde un archivo y lo guarda en la lista de grafos correspondiente.
     */
    public void intentarCargarGrafo() {
        try {
            System.out.println("Introduzca path al archivo que contenedor del grafo: ");
            Scanner fileReader = new Scanner(new File(scan.nextLine()));
            Grafo gND;
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado! Devolviendo al menu.");
            return;
        }
    }

    /**
     * Ciclo del menu donde se visualizan los distintos grafos cargados en el programa y se ofrecen las
     * siguientes opciones:
     * <ul>
     * <li>Editar un grafo</li>
     * <li>Ver propiedades/atributos del grafo</li>
     * <li>Regresar al menu principal</li>
     * </ul>
     */
    public void viendoGrafos() {
        try {
            Integer opcion = 0;
            while (opcion != -1) {
                System.out.println("Grafos no dirigidos: " + grafosND.size());
                for (int i = 0; i < grafosND.size(); i++) {
                    System.out.println(i + ")" + grafosND.get(i).toString());
                }
                System.out.println("--------------------------------------------------------------");
                System.out.println("Grafos dirigidos: " + grafosD.size());
                for (int i = 0; i < grafosD.size(); i++) {
                    System.out.println((i + grafosND.size()) + ")" + grafosD.get(i).toString());
                }
                System.out.println("--------------------------------------------------------------");

                System.out.println("1) Editar un grafo.");
                System.out.println("2) Ver propiedades/atributos del grafo.");
                System.out.println("-1) Regresar al menu principal.");
                System.out.println("Elige una opcion: ");
                opcion = Integer.parseInt(scan.nextLine());

                if (opcion == 1) {
                    selGrafoEditar();
                } else if (opcion == -1) {
                    return;
                }
                // scan.close();
            }
        } catch (NumberFormatException e) {
            // scan.close();
            viendoGrafos();
            return;
        }
    }

    public void selGrafoEditar() {
        try {
            System.out.println("Introduzca un numero de la lista de grafos: ");
            Integer selInt = Integer.parseInt(scan.nextLine());
            if (selInt < 0 || (grafosND.size() + grafosD.size()) <= selInt) {
                System.out.println("Grafo no en la lista! Regresando a lista de grafos.");
                return;
            } else {

            }
            return;
        } catch (NumberFormatException e) {
            System.out.println("Introduce un numero por favor!");
            selGrafoEditar();
            return;
        }
    }

    public void editandoGrafoND(int aEditar) {
        try {
            GrafoNoDirigido g = (GrafoNoDirigido)grafosND.get(aEditar);
            Integer opcion = 0;
            while (opcion != -1) {
                System.out.println("1) Visualizar nodos grafo.");
                System.out.println("2) Visualizar lados grafo.");
                System.out.println("3) Agregar vertice.");
                System.out.println("4) Agregar arista.");
                System.out.println("5) Eliminar vertice.");
                System.out.println("6) Eliminar arista.");
                System.out.println("-1) Regresar a visualizacion de grafos.");
                System.out.println("Elige una opcion: ");
                opcion = Integer.parseInt(scan.nextLine());

                if (opcion == 1) {
                    visualizarNodos(g);
                } else if (opcion == 2) {
                    visualizarLados(g);
                } else if (opcion == 3) {
                    
                } else if (opcion == 4) {

                } else if (opcion == 5) {

                } else if (opcion == 6) {

                } else if (opcion == -1) {
                    return;
                }
                // scan.close();
            }
        } catch (NumberFormatException e) {
            // scan.close();
            viendoGrafos();
            return;
        }
    }

    public void visualizarNodos(Grafo g) {
        LinkedList<Vertice> nodos = g.vertices(g);
        for (Vertice vertice : nodos) {
            System.out.println(Vertice.toString(vertice));
        }
        return;
    }

    public void visualizarLados(Grafo g) {
        ArrayList<Lado> lados = g.lados(g);
        for (Lado lado : lados) {
            System.out.println(lado.toString(lado));
        }
        return;
    }

    public static void main(String[] args) {
        Cliente test = new Cliente();
        test.viendoGrafos();
        test.scan.close();
    }
}
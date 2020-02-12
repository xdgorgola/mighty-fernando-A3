import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import Lados.Lado;
import Vertice.Vertice;

public class Cliente {

    ////////////// DISCULPA ESTE COMENTARIO FEISIMO PERO ES PARA QUE NO LO PASES DE
    ////////////// LARGO. CUANDO HAGAS EL
    ////////////// EL COSO ESTE, SE ME OLVIDO, EL AGREGAR ARCO, ASEGURATE DE QUE EL
    ////////////// TIPO QUE LE PASES 1. PERO
    /////////////// ESO NO ES LO QUE QUERIA DECIRTE (NO ME ACUERDO EN SERIO). AJA,
    ////////////// EN EL EDITAR GRAFO DIRIGIDO
    /////////// CUANDO LE HAGAS GET AL GRAFO DIRIGIDO, COMO LES ESTOY HACIENDO
    ////////////// DISPLAY TIPO 0 1 2 3 4 5 6...
    /////////////// TIENES QUE HACER SI NO ME EQUIVOCO (I - GRAFOSND.SIZE()) SI, LO
    ////////////// ACABO DE ESCRIBIR EN UN
    /////////// PAPEL Y ES ESO. VOY A COPIAR EL METODO Y TE LO VOY A AGREGAR DE UNA
    ////////////// PARA QUE NO MUERAS XD
    ////////////////

    ////////////// PEDRO
    /////////// PON 
    ////////////// LOS
    /////////////// SWITCH
    ////////////// POR
    /////////// FAVOR
    ////////////////////// HAS UN FACTORY PEDRO
    /////////// SE OYE INTERESANTE Y UTIL

    private ArrayList<Grafo> grafosND = new ArrayList<Grafo>();
    private ArrayList<Grafo> grafosD = new ArrayList<Grafo>();
    private Scanner scan = new Scanner(System.in);

    /**
     * Ciclo del menu principal de la agrupacion donde se pueden acceder a las
     * siguientes opciones:
     * <ul>
     * <li><b>Cargar grafo desde archivo: Carga un grafo desde un archivo</b></li>
     * <li><b>Ver grafos guardados: Menu de visualizacion de grafos
     * cargados.</b></li>
     * <li><b>Salir del programa</b></li>
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
     * Intenta cargar un grafo desde un archivo y lo guarda en la lista de grafos
     * correspondiente.
     */
    public void intentarCargarGrafo() {
        Scanner fileReader = null;
        try {
            System.out.println("Introduzca path al archivo que contenedor del grafo: ");
            fileReader = new Scanner(new File(scan.nextLine()));
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado! Devolviendo al menu.");
            fileReader.close();
            return;
        }
    }

    /**
     * Ciclo del menu donde se visualizan los distintos grafos cargados en el
     * programa y se ofrecen las siguientes opciones:
     * <ul>
     * <li><b>Editar un grafo: Menu de edicion de un grafo.</b></li>
     * <li><b>Ver propiedades/atributos del grafo: Menu de propiedades de un
     * grafo.</b></li>
     * <li><b>Clonar un grafo: Menu de clonacion de un grafo.</b></li>
     * <li><b>Regresar al menu principal</b></li>
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
                System.out.println("2) Ver propiedades/atributos de un grafo.");
                System.out.println("3) Clonar un grafo.");
                System.out.println("-1) Regresar al menu principal.");
                System.out.println("Elige una opcion: ");
                opcion = Integer.parseInt(scan.nextLine());

                if (opcion == 1) {
                    selGrafoEditar();
                } else if (opcion == 2) {
                    selGrafoVer();
                } else if (opcion == 3) {

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

    /**
     * Ciclo del menu donde se visualizan las distintas opciones para editar un
     * grafo no dirigido. Estas son:
     * <ul>
     * <li><b><b>Visualizar nodos grafo:</b> Visualiza los nodos del grafo a
     * editar.</li>
     * <li><b><b>Visualizar lados grafo:</b> Visualiza los lados del grafo a
     * editar.</li>
     * <li><b><b>Agregar vertice:</b> Agrega un vertice al grafo a editar.</li>
     * <li><b><b>Agregar arista:</b> Agrega una arista al grafo a editar.</li>
     * <li><b><b>Eliminar vertice:</b> Elimina un vertice al grafo a editar.</li>
     * <li><b><b>Eliminar arista:</b> Elimina una arista al grafo a editar.</li>
     * <li><b><b>Regresar a visualizacion de grafos:</b> Regresa al menu de
     * visualizacion de grafos.</li>
     * </ul>
     * 
     * @param aEditar Grafo a editar.
     */
    public void editandoGrafoND(int aEditar) {
        try {
            GrafoNoDirigido g = (GrafoNoDirigido) grafosND.get(aEditar);
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
                    agregarVertice(g);
                } else if (opcion == 4) {
                    agregarArista(g);
                } else if (opcion == 5) {

                } else if (opcion == 6) {

                } else if (opcion == -1) {
                    return;
                }
                // scan.close();arista
            }
        } catch (NumberFormatException e) {
            // scan.close();
            viendoGrafos();
            return;
        }
    }

    /**
     * Ciclo del menu donde se visualizan las distintas opciones para editar un
     * grafo dirigido. Estas son:
     * <ul>
     * <li><b><b>Visualizar nodos grafo:</b> Visualiza los nodos del grafo a
     * editar.</li>
     * <li><b><b>Visualizar lados grafo:</b> Visualiza los lados del grafo a
     * editar.</li>
     * <li><b><b>Agregar vertice:</b> Agrega un vertice al grafo a editar.</li>
     * <li><b><b>Agregar arco:</b> Agrega una arco al grafo a editar.</li>
     * <li><b><b>Eliminar vertice:</b> Elimina un vertice al grafo a editar.</li>
     * <li><b><b>Eliminar arco:</b> Elimina una arista al grafo a editar.</li>
     * <li><b><b>Regresar a visualizacion de grafos:</b> Regresa al menu de
     * visualizacion de grafos.</li>
     * </ul>
     * 
     * @param aEditar Grafo a editar.
     */
    public void editandoGrafoD(int aEditar) {
        try {
            // GrafoDirigido g = (GrafoDirigido)grafosD.get(aEditar - grafosND.size());
            // esto es lo del comentario gigante
            Grafo g = null; // reemplaza esto por lo de arriba
            Integer opcion = 0;
            while (opcion != -1) {
                System.out.println("1) Visualizar nodos grafo.");
                System.out.println("2) Visualizar lados grafo.");
                System.out.println("3) Agregar vertice.");
                System.out.println("4) Agregar arco.");
                System.out.println("5) Eliminar vertice.");
                System.out.println("6) Eliminar arco.");
                System.out.println("-1) Regresar a visualizacion de grafos.");
                System.out.println("Elige una opcion: ");
                opcion = Integer.parseInt(scan.nextLine());

                if (opcion == 1) {
                    visualizarNodos(g);
                } else if (opcion == 2) {
                    visualizarLados(g);
                } else if (opcion == 3) {
                    agregarVertice(g);
                } else if (opcion == 4) {
                    // agregarArista(g);
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

    public void selGrafoVer() {
        try {
            System.out.println("Introduzca un numero de la lista de grafos: ");
            Integer selInt = Integer.parseInt(scan.nextLine());
            if (selInt < 0 || (grafosND.size() + grafosD.size()) <= selInt) {
                System.out.println("Grafo no en la lista! Regresando a lista de grafos.");
                return;
            } else {
                if (selInt < grafosND.size()){
                    viendoGrafoND(selInt);
                }
                else if (selInt >= grafosND.size()){
                    viendoGrafoD(selInt);
                }
            }
            return;
        } catch (NumberFormatException e) {
            System.out.println("Introduce un numero por favor!");
            selGrafoVer();
            return;
        }
    }
    
    /**
     * Ciclo del menu donde se visualizan los distintos atributos/propiedades de un
     * grafo no dirigido. Estos son:
     * <ul>
     * <li><b><b>Visualizar nodos grafo:</b> Visualiza los nodos del grafo a
     * editar.</li>
     * <li><b><b>Visualizar lados grafo:</b> Visualiza los lados del grafo a
     * editar.</li>
     * <li><b><b>Adyacencias de un vertice:</b> Muestra los vertices adyacentes a un
     * vertice.</li>
     * <li><b><b>Incidencias de un vertice:</b> Muestra los lados que inciden en un
     * vertice.</li>
     * <li><b><b>Grado de un vertice:</b> Calcula el grado de un vertice.</li>
     * <li><b><b>Regresar a visualizacion de grafos:</b> Regresa al menu de
     * visualizacion de grafos.</li>
     * </ul>
     * 
     * @param aVer Grafo a ver.
     */
    public void viendoGrafoND(int aVer) {
        try {
            GrafoNoDirigido g = (GrafoNoDirigido) grafosND.get(aVer);
            Integer opcion = 0;
            while (opcion != -1) {
                System.out.println("1) Visualizar nodos grafo.");
                System.out.println("2) Visualizar lados grafo.");
                System.out.println("3) Adyacencias de un vertice.");
                System.out.println("4) Incidencias de un vertice.");
                System.out.println("5) Grado de un vertice.");
                System.out.println("-1) Regresar a visualizacion de grafos.");
                System.out.println("Elige una opcion: ");
                opcion = Integer.parseInt(scan.nextLine());

                if (opcion == 1) {
                    visualizarNodos(g);
                } else if (opcion == 2) {
                    visualizarLados(g);
                } else if (opcion == 3) {
                    agregarVertice(g);
                } else if (opcion == 4) {
                    agregarArista(g);
                } else if (opcion == 5) {

                } else if (opcion == -1) {
                    return;
                }
                // scan.close();arista
            }
        } catch (NumberFormatException e) {
            // scan.close();
            viendoGrafos();
            return;
        }
    }

    /**
     * Ciclo del menu donde se visualizan los distintos atributos/propiedades de un
     * grafo dirigido Estos son:
     * <ul>
     * <li><b><b>Visualizar nodos grafo:</b> Visualiza los nodos del grafo a
     * editar.</li>
     * <li><b><b>Visualizar lados grafo:</b> Visualiza los lados del grafo a
     * editar.</li>
     * <li><b><b>Sucesores de un vertice:</b> Muestra los vertices sucesores a un
     * vertice.</li>
     * <li><b><b>Predecesores de un vertice:</b> Muestra los vertices predecesores a
     * un vertice.</li>
     * <li><b><b>Grado interno de un vertice:</b> Calcula el grado interno de un
     * vertice.</li>
     * <li><b><b>Grado externo de un vertice:</b> Calcula el grado externo de un
     * vertice.</li>
     * <li><b><b>Regresar a visualizacion de grafos:</b> Regresa al menu de
     * visualizacion de grafos.</li>
     * </ul>
     * 
     * @param aVer Grafo a ver.
     */
    public void viendoGrafoD(int aVer) {
        try {
            GrafoNoDirigido g = (GrafoNoDirigido) grafosND.get(aVer);
            Integer opcion = 0;
            while (opcion != -1) {
                System.out.println("1) Visualizar nodos grafo.");
                System.out.println("2) Visualizar lados grafo.");
                System.out.println("3) Sucesores de un vertice.");
                System.out.println("4) Predecesores de un vertice.");
                System.out.println("5) Grado interno de un vertice.");
                System.out.println("6) Grado externo de un vertice.");
                System.out.println("-1) Regresar a visualizacion de grafos.");
                System.out.println("Elige una opcion: ");
                opcion = Integer.parseInt(scan.nextLine());

                if (opcion == 1) {
                    visualizarNodos(g);
                } else if (opcion == 2) {
                    visualizarLados(g);
                } else if (opcion == 3) {
                    agregarVertice(g);
                } else if (opcion == 4) {
                    agregarArista(g);
                } else if (opcion == 5) {

                } else if (opcion == 6) {

                } else if (opcion == -1) {
                    return;
                }
                // scan.close();arista
            }
        } catch (NumberFormatException e) {
            // scan.close();
            viendoGrafos();
            return;
        }
    }

    public void agregarVertice(Grafo g) {
        try {
            int id = Integer.parseInt(scan.nextLine());
            String nombre = scan.nextLine();
            double xCord = Double.parseDouble(scan.nextLine());
            double yCord = Double.parseDouble(scan.nextLine());
            double w = Double.parseDouble(scan.nextLine());
            if (g.agregarVertice(g, id, nombre, xCord, yCord, w)) {
                System.out.println("Vertice agregado exitosamente!");
            } else {
                System.out.println("Un vertice con la misma ID ya existe :(");
                agregarVertice(g);
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor intenta de nuevo introduciendo numeros cuando se pidan!");
            agregarVertice(g);
            return;
        }
    }

    public void agregarArista(Grafo g) {
        try {
            int id = Integer.parseInt(scan.nextLine());
            String idV1 = scan.nextLine();
            String idV2 = scan.nextLine();
            double w = Double.parseDouble(scan.nextLine());
            if (((GrafoNoDirigido) g).agregarArista(g, idV1, idV2, id, w)) {
                System.out.println("Arista agregada exitosamente!");
            } else {
                System.out.println("Una arista entre los dos vertices ya existe :(");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor intenta de nuevo introduciendo numeros cuando se pidan!");
            agregarArista(g);
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
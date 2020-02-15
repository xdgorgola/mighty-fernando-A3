import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Lados.Arista;
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
     * <li><b>Crear nuevo grafo: Crea un nuevo grafo</b></li>
     * <li><b>Ver grafos guardados: Menu de visualizacion de grafos
     * cargados.</b></li>
     * <li><b>Salir del programa</b></li>
     * </ul>
     */
    public void menuPrincipal() {
        int opcion = 0;
        while (opcion != -1) {
            System.out.println("1) Cargar grafo desde archivo.");

            System.out.println("2) Crear nuevo grafo");
            System.out.println("3) Ver grafos guardados.");
            System.out.println("-1) Salir del programa.");
            opcion = Integer.parseInt(scan.nextLine().trim());

            switch (opcion) {
                case -1:
                    return;
                case 1:
                    intentarCargarGrafo();
                    break;
                case 2:
                    creacionDeGrafo();
                    break;
                case 3:
                    viendoGrafos();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Intenta cargar un grafo desde un archivo y lo guarda en la lista de grafos
     * correspondiente.
     */
    public void intentarCargarGrafo() {
        Scanner fileReader = null;
        try {
            System.out.println("Introduzca path al archivo que contiene el grafo: ");
            fileReader = new Scanner(new File(scan.nextLine().trim()));
            GrafoNoDirigido g = new GrafoNoDirigido();
            g.cargarGrafo(g, scan.nextLine().trim());
            grafosND.add(g);
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado! Devolviendo al menu.");
            return;
        }
    }

    /**
     * Ciclo de creacion de un nuevo grafo. Permite crear un grafo dirigido o no e ir directamente a
     * su menu de edicion.
     */
    public void creacionDeGrafo(){
        try {
            System.out.println("Introduzca ND para un grafo no dirigido y D para uno dirigido: ");
            String op = scan.nextLine().trim().trim();
            switch (op) {
                case "ND":
                    GrafoNoDirigido nd = new GrafoNoDirigido();
                    grafosND.add(nd);
                    editandoGrafoND(grafosND.size() - 1);
                    return;
                case "D":
                    GrafoDirigido d = new GrafoDirigido();
                    grafosD.add(d);
                    editandoGrafoD(grafosD.size() - 1);
                    return;
                default:
                    creacionDeGrafo();
                    return;
            }
        } catch (Exception e) {
            System.out.println("ERROR MAGICO! (NO SE COMO LO CAUSASTE)");
            return;
        }
    }
    /**
     * Ciclo del menu donde se visualizan los distintos grafos cargados en el
     * programa y se ofrecen las siguientes opciones:
     * <ul>
     * <li><b>Editar un grafo:</b> Menu de edicion de un grafo.</li>
     * <li><b>Ver propiedades/atributos del grafo:</b> Menu de propiedades de un
     * grafo.</li>
     * <li><b>Clonar un grafo:</b> Menu de clonacion de un grafo.</li>
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
                opcion = Integer.parseInt(scan.nextLine().trim());

                switch (opcion) {
                    case -1:
                        return;
                    case 1:
                        selGrafoEditar();
                        break;
                    case 2:
                        selGrafoVer();
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }
            }
        } catch (NumberFormatException e) {
            // scan.close();
            viendoGrafos();
            return;
        }
    }

    /**
     * Ciclo de seleccion de un grafo a editar.
     */
    public void selGrafoEditar() {
        try {
            System.out.println("Introduzca un numero de la lista de grafos: ");
            Integer selInt = Integer.parseInt(scan.nextLine().trim());
            if (selInt < 0 || (grafosND.size() + grafosD.size()) <= selInt) {
                System.out.println("Grafo no en la lista! Regresando a lista de grafos.");
                return;
            } else {
                if (selInt < grafosND.size()){
                    editandoGrafoND(selInt);
                }
                else if (selInt >= grafosND.size()){
                    editandoGrafoD(selInt);
                }
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
     * <li><b>Visualizar nodos grafo:</b> Visualiza los nodos del grafo a
     * editar.</li>
     * <li><b>Visualizar lados grafo:</b> Visualiza los lados del grafo a
     * editar.</li>
     * <li><b>Agregar vertice:</b> Agrega un vertice al grafo a editar.</li>
     * <li><b>Agregar arista:</b> Agrega una arista al grafo a editar.</li>
     * <li><b>Eliminar vertice:</b> Elimina un vertice al grafo a editar.</li>
     * <li><b>Eliminar arista:</b> Elimina una arista al grafo a editar.</li>
     * <li><b>Regresar a visualizacion de grafos:</b> Regresa al menu de
     * visualizacion de grafos.</li>
     * </ul>
     * 
     * @param aEditar Grafo a editar.
     */
    public void editandoGrafoND(int aEditar) {
        try {
            GrafoNoDirigido g = (GrafoNoDirigido) grafosND.get(aEditar);
            System.out.println(g == null);
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
                opcion = Integer.parseInt(scan.nextLine().trim());

                switch (opcion) {
                    case -1:
                        return;
                    case 1:
                        visualizarNodos(g);
                        break;
                    case 2:
                        visualizarLados(g);
                        break;
                    case 3:
                        agregarVertice(g);
                        break;
                    case 4:
                        agregarArista(g);
                        break;
                    case 5:
                        eliminarVertice(g);
                        break;
                    case 6:
                        eliminarArista(g);
                        break;
                    default:
                        break;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Introduce un numero por favor!");
            viendoGrafos();
            return;
        }
    }

    /**
     * Ciclo del menu donde se visualizan las distintas opciones para editar un
     * grafo dirigido. Estas son:
     * <ul>
     * <li><b>Visualizar nodos grafo:</b> Visualiza los nodos del grafo a
     * editar.</li>
     * <li><b>Visualizar lados grafo:</b> Visualiza los lados del grafo a
     * editar.</li>
     * <li><b>Agregar vertice:</b> Agrega un vertice al grafo a editar.</li>
     * <li><b>Agregar arco:</b> Agrega una arco al grafo a editar.</li>
     * <li><b>Eliminar vertice:</b> Elimina un vertice al grafo a editar.</li>
     * <li><b>Eliminar arco:</b> Elimina una arista al grafo a editar.</li>
     * <li><b>Regresar a visualizacion de grafos:</b> Regresa al menu de
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
                opcion = Integer.parseInt(scan.nextLine().trim());

                switch (opcion) {
                    case -1:
                        return;
                    case 1:
                        visualizarNodos(g);
                        break;
                    case 2:
                        visualizarLados(g);
                        break;
                    case 3:
                        agregarVertice(g);
                        break;
                    case 4:
                        break;
                    case 5:
                        eliminarVertice(g);
                        break;
                    case 6:
                        break;
                    default:
                        break;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Introduce un numero por favor!");
            editandoGrafoD(aEditar);
            return;
        }
    }

    /**
     * Ciclo de seleccion de grafo a visualizar sus atributos/propiedades.
     */
    public void selGrafoVer() {
        try {
            System.out.println("Introduzca un numero de la lista de grafos: ");
            Integer selInt = Integer.parseInt(scan.nextLine().trim());
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
     * <li><b>Visualizar nodos grafo:</b> Visualiza los nodos del grafo a
     * editar.</li>
     * <li><b>Visualizar lados grafo:</b> Visualiza los lados del grafo a
     * editar.</li>
     * <li><b>Adyacencias de un vertice:</b> Muestra los vertices adyacentes a un
     * vertice.</li>
     * <li><b>Incidencias de un vertice:</b> Muestra los lados que inciden en un
     * vertice.</li>
     * <li><b>Grado de un vertice:</b> Calcula el grado de un vertice.</li>
     * <li><b>Ver info de un vertice:</b> Muestra datos de un vertice.</li>
     * <li><b>Ver info de una arista:</b> Muestra datos de una arista.</li>
     * <li><b>Regresar a visualizacion de grafos:</b> Regresa al menu de
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
                System.out.println("6) Ver info de un vertice.");
                System.out.println("7) Ver info de una arista.");
                System.out.println("-1) Regresar a visualizacion de grafos.");
                System.out.println("Elige una opcion: ");

                opcion = Integer.parseInt(scan.nextLine().trim());

                switch (opcion) {
                    case -1:
                        return;
                    case 1:
                        visualizarNodos(g);
                        break;
                    case 2:
                        visualizarLados(g);
                        break;
                    case 3:
                        adyacenciasVertices(g);
                        break;
                    case 4:
                        incidenciasVertices(g);
                        break;
                    case 5:
                        gradoVertice(g);
                        break;
                    case 6:
                        verInfoVertice(g);
                        break;
                    case 7:
                        verInfoArista(g);
                        break;
                    default:
                        break;
                }
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
     * <li><b>Visualizar nodos grafo:</b> Visualiza los nodos del grafo a
     * editar.</li>
     * <li><b>Visualizar lados grafo:</b> Visualiza los lados del grafo a
     * editar.</li>
     * <li><b>Sucesores de un vertice:</b> Muestra los vertices sucesores a un
     * vertice.</li>
     * <li><b>Predecesores de un vertice:</b> Muestra los vertices predecesores a
     * un vertice.</li>
     * <li><b>Grado interno de un vertice:</b> Calcula el grado interno de un
     * vertice.</li>
     * <li><b>Grado externo de un vertice:</b> Calcula el grado externo de un
     * vertice.</li>
     * <li><b>Ver info de un vertice:</b> Muestra datos de un vertice.</li>
     * <li><b>Ver info de un arco:</b> Muestra datos de un arco.</li>
     * <li><b>Regresar a visualizacion de grafos:</b> Regresa al menu de
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
                System.out.println("7) Ver info de un vertice.");
                System.out.println("8) Ver info de un arco.");
                System.out.println("-1) Regresar a visualizacion de grafos.");
                System.out.println("Elige una opcion: ");
                opcion = Integer.parseInt(scan.nextLine().trim());

                switch (opcion) {
                    case -1:
                        return;
                    case 1:
                        visualizarNodos(g);
                        break;
                    case 2:
                        visualizarLados(g);
                        break;
                    case 3:
                        adyacenciasVertices(g);
                        break;
                    case 4:
                        incidenciasVertices(g);
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                    verInfoVertice(g);
                        break;
                    case 8:
                        break;
                    default:
                        break;
                }
            }
        } catch (NumberFormatException e) {
            viendoGrafos();
            return;
        }
    }

    /**
     * Ciclo de seleccion para agregar un nuevo vertice a un grafo g.
     * 
     * @param g Grafo a agregar vertice
     */
    public void agregarVertice(Grafo g) {
        try {
            System.out.println("Introduzca ID del vertice: ");
            int id = Integer.parseInt(scan.nextLine().trim());
            System.out.println("Introduzca NOMBRE del vertice: ");
            String nombre = scan.nextLine().trim();
            while (nombre.length() < 1){
                System.out.println("Introduzca un NOMBRE mas largo: ");
                nombre = scan.nextLine().trim();
            }
            System.out.println("Introduzca COORDENADA X del vertice (double permitido): ");
            double xCord = Double.parseDouble(scan.nextLine().trim());
            System.out.println("Introduzca COORDENADA Y del vertice (double permitido): ");
            double yCord = Double.parseDouble(scan.nextLine().trim());
            System.out.println("Introduzca PESO del vertice (double permitido): ");
            double w = Double.parseDouble(scan.nextLine().trim());
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

    /**
     * Ciclo de seleccion para eliminar un vertice de un grafo g.
     * 
     * @param g Grafo a eliminar vertice
     */
    public void eliminarVertice(Grafo g){
        try {
            System.out.println("Introduzca ID de un vertice a eliminar: ");
            int toRemove = Integer.parseInt(scan.nextLine().trim());
            g.eliminarVertice(g, toRemove);
            return;
        } catch (NumberFormatException | NoSuchElementException e) {
            if (e instanceof NumberFormatException){
                System.out.println("Por favor introduzca un numero!");
                eliminarVertice(g);
            }
            else{
                System.out.println("El vertice no se encuentra en el grafo!");
                return;
            }
        }
    }

    /**
     * Ciclo de seleccion para agregar una arista a un grafo no dirigido g.
     * 
     * @param g
     */
    public void agregarArista(Grafo g) {
        try {
            System.out.println("Introduzca ID de la arista: ");
            int id = Integer.parseInt(scan.nextLine().trim());
            System.out.println("Introduce NOMBRE del vertice 1: ");
            String idV1 = scan.nextLine().trim().trim();
            System.out.println("Introduce NOMBRE del vertice 2: ");
            String idV2 = scan.nextLine().trim().trim();
            System.out.println("Introduce PESO de la arista (double permitido): ");
            double w = Double.parseDouble(scan.nextLine().trim());
            if (((GrafoNoDirigido) g).agregarArista(g, idV1, idV2, id, w)) {
                System.out.println("Arista agregada exitosamente!");
            } else {
                System.out.println("Una arista entre los dos vertices ya existe :( o no existen los vertices");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor intenta de nuevo introduciendo numeros cuando se pidan!");
            agregarArista(g);
            return;
        }
    }

    /**
     * Ciclo de seleccion para eliminar una arista de un grafo no dirigido g.
     * 
     * @param g Grafo no dirigido a eliminar arista
     */
    public void eliminarArista(Grafo g){
        try {
            System.out.println("Introduzca ID de una arista a eliminar: ");
            int toRemove = Integer.parseInt(scan.nextLine().trim());
            ((GrafoNoDirigido)g).eliminarArista(g, toRemove);
            return;
        } catch (NumberFormatException | NoSuchElementException e) {
            if (e instanceof NumberFormatException){
                System.out.println("Por favor introduzca un numero!");
                eliminarArista(g);
                return;
            }
            else if (e instanceof NoSuchElementException){
                System.out.println("La arista no se encuentra en el grafo!");
                return;
            }
        } 
    }

    /**
     * Visualiza la informacion de todos los nodos de un grafo g.
     * 
     * @param g Grafo a visualizar nodos
     */
    public void visualizarNodos(Grafo g) {
        LinkedList<Vertice> nodos = g.vertices(g);
        if (nodos.size() <= 0){
            System.out.println("No hay nodos!");
        }
        else{
            for (Vertice vertice : nodos) {
                System.out.println(Vertice.toString(vertice));
            }
        }
        return;
    }

    /**
     * Visualiza la informacion de todos los lados de un grafo g.
     * 
     * @param g Grafo a visualizar lados
     */
    public void visualizarLados(Grafo g) {
        ArrayList<Lado> lados = g.lados(g);
        if (lados.size() <= 0){
            System.out.println("No hay lados!");
        }
        for (Lado lado : lados) {
            System.out.println(lado.toString(lado));
        }
        return;
    }

    /**
     * Muestra los ID de los vertices adyacentes a un vertice de un grafo g.
     * 
     * @param g Grafo a buscar adyacencias de un vertice
     */
    public void adyacenciasVertices(Grafo g){
        try {
            System.out.println("Introduzca ID de una vertice: ");
            int id = Integer.parseInt(scan.nextLine().trim());
            String aux = "";
            ListIterator<Vertice> adyIterator = g.adyacentes(g, id).listIterator();
            System.out.println("Adyacencias del vertice " + id + ":\n");
            while (adyIterator.hasNext()){
                Vertice act = adyIterator.next();
                if (!adyIterator.hasNext()){
                    aux += Vertice.obtenerID(act);
                }
                else{
                    aux += Vertice.obtenerID(act) + ", ";
                }
            }
            aux += "\n";
            System.out.println(aux);
            return;
        } catch (NumberFormatException | NoSuchElementException e) {
            if (e instanceof NumberFormatException){
                System.out.println("Por favor introduzca un numero!");
                adyacenciasVertices(g);
                return;
            }
            else if (e instanceof NoSuchElementException){
                System.out.println("El vertice no se encuentra en el grafo!");
                return;
            }
        }        
    }

    /**
     * Muestra los ID de los lados que inciden en un vertice de un grafo g.
     * 
     * @param g Grafo a buscar incidencias
     */
    public void incidenciasVertices(Grafo g){
        try {
            System.out.println("Introduzca ID de una vertice: ");
            int id = Integer.parseInt(scan.nextLine().trim());
            String aux = "";
            ListIterator<Lado> lIterator = g.incidentes(g, id).listIterator();
            System.out.println("Incidentes del vertice " + id + ":\n");
            while (lIterator.hasNext()){
                Lado act = lIterator.next();
                if (!lIterator.hasNext()){
                    aux += Lado.obtenerTipo(act);
                }
                else{
                    aux += Lado.obtenerTipo(act) + ", ";
                }
            }
            aux += "\n";
            System.out.println(aux);
            return;
        } catch (NumberFormatException | NoSuchElementException e) {
            if (e instanceof NumberFormatException){
                System.out.println("Por favor introduzca un numero!");
                incidenciasVertices(g);
                return;
            }
            else if (e instanceof NoSuchElementException){
                System.out.println("El vertice no se encuentra en el grafo!");
                return;
            }
        }        
    }

    /**
     * Ciclo de seleccion para ver el grado de un vertice en un grafo g.
     * 
     * @param g Grafo a ver grado del vertice
     */
    public void gradoVertice(Grafo g){
        try {
            System.out.println("Introduzca ID de una vertice: ");
            int id = Integer.parseInt(scan.nextLine().trim());
            int grad = g.grado(g, id);
            System.out.println("Grado nodo " + id + ": " + grad);
            return;
        } catch (NumberFormatException | NoSuchElementException e) {
            if (e instanceof NumberFormatException){
                System.out.println("Por favor introduzca un numero!");
                gradoVertice(g);
                return;
            }
            else if (e instanceof NoSuchElementException){
                System.out.println("El vertice no se encuentra en el grafo!");
                return;
            }
        }      
    }

    /**
     * Ciclo de seleccion para visualizar la informacion de un vertice en un grafo g.
     * 
     * @param g Grafo a visualizar informacion de un vertice
     */
    public void verInfoVertice(Grafo g){
        try {
            System.out.println("Introduzca ID de una vertice: ");
            int id = Integer.parseInt(scan.nextLine().trim());
            System.out.println(Vertice.toString(g.obtenerVertice(g, id)));
            return;
        } catch (NumberFormatException | NoSuchElementException e) {
            if (e instanceof NumberFormatException){
                System.out.println("Por favor introduzca un numero!");
                verInfoVertice(g);
                return;
            }
            else if (e instanceof NoSuchElementException){
                System.out.println("El vertice no se encuentra en el grafo!");
                return;
            }
        }      
    }

    /**
     * Ciclo de seleccion para visualizar la informacion de una arista en un grafo no dirigido g.
     * 
     * @param g Grafo a visualizar informacion de arista
     */
    public void verInfoArista(Grafo g){
        try {
            System.out.println("Introduzca ID de una vertice: ");
            int id = Integer.parseInt(scan.nextLine().trim());
            Arista a = ((GrafoNoDirigido)g).obtenerArista(g, id);
            System.out.println(a.toString(a));
            return;
        } catch (NumberFormatException | NoSuchElementException e) {
            if (e instanceof NumberFormatException){
                System.out.println("Por favor introduzca un numero!");
                verInfoVertice(g);
                return;
            }
            else if (e instanceof NoSuchElementException){
                System.out.println("El vertice no se encuentra en el grafo!");
                return;
            }
        }      
    }
    public static void main(String[] args) {
        Cliente test = new Cliente();
        test.menuPrincipal();
        test.scan.close();
    }
}
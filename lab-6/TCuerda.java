import java.lang.Math;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.IllegalArgumentException;

/* Autores:
 * 		Pedro Rodriguez 15-11264
 * 		Mariangela Rizzo 17-10538
 */

/**
 * Backtracking para hallar los equipos con la menor diferencia de peso.
 */

public class TCuerda {

    // Valor de la diferencia entre el peso de dos equipos.
    private int weightSol;
    // Equipos que corresponden a isa diferencia.
    private int[] sol;

    public TCuerda() {
        weightSol = 0;
        sol = new int[2];
    }

    /**
     * Carga el numero de profesores que participarán en la competencia y almacena
     * sus pesos en un array.
     * 
     * @param fileName Nombre o ruta del archivo.
     * @return El array con todos los pesos
     * 
     * @throws IOException           Si hay algún error leyendo el archivo.
     * @throws FileNotFoundException Si el archivo o ruta no se encuentra.
     */
    public int[] loadData(String fileName) throws IOException, FileNotFoundException {

        BufferedReader Reader = new BufferedReader(new FileReader(fileName));

        String line = Reader.readLine();
        int professors = Integer.parseInt(line);

        int[] allWeights = new int[professors];

        for (int i = 0; i < professors; i++) {
            line = Reader.readLine();
            allWeights[i] = Integer.parseInt(line);
        }

        Reader.close();

        return allWeights;
    }

    /**
     * Verifica que los equipos no queden con la posibilidad de diferir en mas de un
     * jugador.
     * 
     * @param n    número de profesores.
     * @param team matriz que almacena por fila el peso del equipo y el numero de
     *             jugadores en la columna siguiente.
     * @return booleano que indica falso si hay una diferencia de màs de un jugador
     *         en los equipos.
     * 
     */
    public boolean IsSolution(int n, int[][] team) {
        if (team[0][1] > (int) Math.ceil((float) n / 2) || team[1][1] > (int) Math.ceil((float) n / 2)) {
            return false;
        }

        return true;
    }

    /**
     * Encuentra la solucion que satisface que los equipos no difieran en màs de un
     * jugador y posean la menor diferencia de peso. 
     * Crea un grafo con la recursión en el cual los estados son la diferencia de peso y 
     * no sigue avanzando si los equipos difieren en màs de un jugador. 
     * Va creando el árbol de soluciones con la matriz team que almacena la distribución de los equipos.
     * 
     * @param n      número de profesores.
     * @param weight diferencia entre el peso de los dos equipos.
     * @param pos    si el profesor considerado se insetra en el equipo 0 o en el
     *               equipo 1.
     * @param value  valor del peso del profesor considerado.
     * @param index  índice que permite considerar los distintos pesos de los
     *               profesores.
     * @param values array de enteros que guarda los pesos de todos los profesores.
     * @param team   matriz que almacena por fila el peso del equipo y el numero de
     *               jugadores en la columna siguiente.
     * @return La distribución de jugadores que posee la menor diferencia de pesos.
     * 
     */
    public int[] FindSolution(int n, int weight, int pos, int value, int index, int[] values, int[][] team) {

        if (index == 0) {
            weightSol = 200 * n;
        }

        index++;
        team[pos][0] += value;
        team[pos][1]++;

        if (IsSolution(n, team)) {

            if (pos == 0) {
                weight -= value;
            } else {
                weight += value;
            }

            if (index < n) {
                value = values[index];

                FindSolution(n, weight, 0, value, index, values, team);
                team[0][0] -= value;
                team[0][1]--;

                FindSolution(n, weight, 1, value, index, values, team);
                team[1][0] -= value;
                team[1][1]--;
            } else {
                if (weightSol > Math.abs(weight)) {
                    weightSol = Math.abs(weight);
                    sol[0] = team[0][0];
                    sol[1] = team[1][0];
                }
            }
        }
        return sol;
    }

    public static void main(String[] args) throws IOException, IllegalArgumentException {
        if (args.length <= 0) {
            throw new IllegalArgumentException("Use: java TCuerda <fileName>");
        }

        TCuerda test = new TCuerda();

        int[] w = test.loadData(args[0]);
        int[][] t = { { 0, 0 }, { 0, 0 } };

        int[] ans = test.FindSolution(w.length, 0, 0, w[0], 0, w, t);

        if (ans[0] < ans[1]) {
            System.out.println(ans[0] + " " + ans[1]);
        } else {
            System.out.println(ans[1] + " " + ans[0]);
        }
    }
}
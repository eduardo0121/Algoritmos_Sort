package alg_ordenamiento;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SelectionSort {

    public static void main(String[] args) {
        String[] tamaniosArchivos = {"100", "500", "1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000", "9000",
            "10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000", "100000"
        };
        String rutaCarpeta = "C:\\Users\\Public\\Documents\\@UNAM-III CICLO 2024\\@HONORIO_ANALISIS ALG\\datos";
        ArrayList<Double> tiemposEjecucion = new ArrayList<>();

        for (String tamaño : tamaniosArchivos) {
            String archivoEntrada = rutaCarpeta + "\\File_" + tamaño + ".txt";
            int[] datos = leerDatos(archivoEntrada);

            if (datos != null) {
                long tiempoEjecucion = medirTiempo(datos.clone());
                double tiempoSegundos = tiempoEjecucion / 1_000_000_000.0;
                tiemposEjecucion.add(tiempoSegundos);
                System.out.printf("Tiempo de ejecucion para File_%s: %.6f segundos%n", tamaño, tiempoSegundos);
            }
        }

        escribirTiemposEnArchivo(tiemposEjecucion);
    }

    public static int[] leerDatos(String archivo) {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea.trim());
            }
        } catch (IOException e) {
            return null;
        }
        String[] datosStr = contenido.toString().replaceAll("[\\[\\]]", "").split(",");
        int[] datos = new int[datosStr.length];
        for (int i = 0; i < datosStr.length; i++) {
            datos[i] = Integer.parseInt(datosStr[i].trim());
        }
        return datos;
    }

    public static long medirTiempo(int[] datos) {
        long inicio = System.nanoTime();
        selectionSort(datos);
        long fin = System.nanoTime();
        return fin - inicio;
    }

    public static void selectionSort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            swap(arr, i, minIndex);
        }
    }
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void escribirTiemposEnArchivo(ArrayList<Double> tiempos) {
        String rutaArchivoSalida = "C:\\Users\\Public\\Documents\\@UNAM-III CICLO 2024\\@HONORIO_ANALISIS ALG"
                + "\\java_times\\selection_times.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivoSalida))) {
            bw.write("[");
            for (int i = 0; i < tiempos.size(); i++) {
                bw.write(String.format("%.6f", tiempos.get(i)));
                if (i < tiempos.size() - 1) {
                    bw.write(", ");
                }
            }
            bw.write("]");
        } catch (IOException e) {
        }
    }
}

package alg_ordenamiento;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class HeapSort {

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
        heapSort(datos);
        long fin = System.nanoTime();
        return fin - inicio;
    }

    private static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }
        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    public static void heapSort(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void escribirTiemposEnArchivo(ArrayList<Double> tiempos) {
        String rutaArchivoSalida = "C:\\Users\\Public\\Documents\\@UNAM-III CICLO 2024\\@HONORIO_ANALISIS ALG\\java_times\\heap_times.txt";
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

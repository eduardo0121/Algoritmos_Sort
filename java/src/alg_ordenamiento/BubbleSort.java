package alg_ordenamiento;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BubbleSort {

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
        bubbleSort(datos);
        long fin = System.nanoTime();
        return fin - inicio;
    }

    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean intercambiado;
        for (int i = 0; n - 1 > i; i++) {
            intercambiado = false;
            for (int j = 0; n - i - 1 > j; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    intercambiado = true;
                }
            }
            if (!intercambiado) {
                break;
            }
        }
    }

    public static void escribirTiemposEnArchivo(ArrayList<Double> tiempos) {
        String rutaArchivoSalida = "C:\\Users\\Public\\Documents\\@UNAM-III CICLO 2024\\@HONORIO_ANALISIS ALG\\java_times\\bubble_times.txt";
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

#include <iostream>
#include <fstream>
#include <vector>
#include <sstream>
#include <chrono>
#include <algorithm>

using namespace std;
using namespace std::chrono;

vector<int> leerDatos(const string& archivo) {
    ifstream file(archivo);
    string contenido, dato;
    vector<int> datos;
    if (file.is_open()) {
        getline(file, contenido);
        contenido.erase(0, 1);
        contenido.erase(contenido.size() - 1);
        stringstream ss(contenido);
        while (getline(ss, dato, ',')) {
            datos.push_back(stoi(dato));
        }
        file.close();
    }
    return datos;
}

double medirTiempo(void (*algoritmo)(vector<int>&), vector<int>& datos, const string& nombreAlgoritmo) {
    auto inicio = high_resolution_clock::now();
    algoritmo(datos);
    auto fin = high_resolution_clock::now();
    duration<double> tiempo = fin - inicio;
    cout << "Tiempo para " << nombreAlgoritmo << ": " << tiempo.count() << " segundos" << endl;
    return tiempo.count();
}

void heapify(vector<int>& arr, int n, int i) {
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
        swap(arr[i], arr[largest]);
        heapify(arr, n, largest);
    }
}

void heapSort(vector<int>& arr) {
    int n = arr.size();
    for (int i = n / 2 - 1; i >= 0; i--) {
        heapify(arr, n, i);
    }
    for (int i = n - 1; i > 0; i--) {
        swap(arr[0], arr[i]);
        heapify(arr, i, 0);
    }
}

void escribirTiemposEnArchivo(const vector<double>& tiempos, const string& rutaArchivoSalida) {
    ofstream file(rutaArchivoSalida);
    if (file.is_open()) {
        file << "[";
        for (size_t i = 0; i < tiempos.size(); ++i) {
            file << tiempos[i];
            if (i < tiempos.size() - 1) {
                file << ", ";
            }
        }
        file << "]" << endl;
        file.close();
    }
}

int main() {
    string fileSizes[] = {"100", "500", "1000", "2000", "3000", "4000", "5000", "6000", 
                          "7000", "8000", "9000", "10000", "20000", "30000", 
                          "40000", "50000", "60000", "70000", "80000", "90000", "100000"};
    string rutaCarpeta = "C:\\Users\\Public\\Documents\\@UNAM-III CICLO 2024\\@HONORIO_ANALISIS ALG\\datos";
    string rutaArchivoSalida = "C:\\Users\\Public\\Documents\\@UNAM-III CICLO 2024\\@HONORIO_ANALISIS ALG\\c++_times\\heap_times.txt";
    vector<double> tiemposEjecucion;
    for (const string& size : fileSizes) {
        string archivoEntrada = rutaCarpeta + "\\File_" + size + ".txt";
        vector<int> datos = leerDatos(archivoEntrada);
        if (!datos.empty()) { 
            vector<int> datosCopia = datos;
            double tiempo = medirTiempo(heapSort, datosCopia, "Heap Sort para File_" + size);
            tiemposEjecucion.push_back(tiempo);
        }
    }
    escribirTiemposEnArchivo(tiemposEjecucion, rutaArchivoSalida);
    return 0;
}


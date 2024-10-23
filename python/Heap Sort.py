import time
import os

def leer_datos(archivo):
    with open(archivo, 'r') as f:
        contenido = f.read().strip()
    datos = contenido.strip('[]').split(',')
    return [int(dato.strip()) for dato in datos]

def medir_tiempo(algoritmo, datos, archivo):
    inicio = time.time()
    algoritmo(datos)  
    fin = time.time()
    tiempo = fin - inicio
    print(f"Archivo {archivo} ordenado en {tiempo:.6f} segundos")
    return tiempo

def heapify(arr, n, i):
    largest = i
    left = 2 * i + 1
    right = 2 * i + 2
    if left < n and arr[i] < arr[left]:
        largest = left
    if right < n and arr[largest] < arr[right]:
        largest = right
    if largest != i:
        arr[i], arr[largest] = arr[largest], arr[i]
        heapify(arr, n, largest)

def heap_sort(arr):
    n = len(arr)
    for i in range(n // 2 - 1, -1, -1):
        heapify(arr, n, i)
    for i in range(n-1, 0, -1):
        arr[i], arr[0] = arr[0], arr[i]
        heapify(arr, i, 0)
    return arr

def escribir_tiempos_en_archivo(tiempos, ruta_archivo_salida):
    with open(ruta_archivo_salida, 'w') as f:
        f.write("[")
        for i, tiempo in enumerate(tiempos):
            f.write(f"{tiempo:.6f}")
            if i < len(tiempos) - 1:
                f.write(", ")
        f.write("]\n")

file_sizes = ["100", "500", "1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000", "9000", "10000","20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000", "100000"]
ruta_carpeta = r'C:\Users\Public\Documents\@UNAM-III CICLO 2024\@HONORIO_ANALISIS ALG\datos'
ruta_archivo_salida = r'C:\Users\Public\Documents\@UNAM-III CICLO 2024\@HONORIO_ANALISIS ALG\python_times\heap_times.txt'
tiempos_ejecucion = []
for size in file_sizes:
    archivo_entrada = os.path.join(ruta_carpeta, f"file_{size}.txt")
    datos = leer_datos(archivo_entrada)
    tiempo = medir_tiempo(heap_sort, datos.copy(), archivo_entrada)
    tiempos_ejecucion.append(tiempo)
escribir_tiempos_en_archivo(tiempos_ejecucion, ruta_archivo_salida)

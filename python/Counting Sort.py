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

def counting_sort(arr):
    max_val = max(arr)
    count = [0] * (max_val + 1)
    output = [0] * len(arr)

    for num in arr:
        count[num] += 1

    for i in range(1, len(count)):
        count[i] += count[i-1]

    for num in reversed(arr):
        output[count[num] - 1] = num
        count[num] -= 1
    return output

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
ruta_archivo_salida = r'C:\Users\Public\Documents\@UNAM-III CICLO 2024\@HONORIO_ANALISIS ALG\python_times\counting_times.txt'
tiempos_ejecucion = []
for size in file_sizes:
    archivo_entrada = os.path.join(ruta_carpeta, f"file_{size}.txt")
    datos = leer_datos(archivo_entrada)
    tiempo = medir_tiempo(counting_sort, datos.copy(), archivo_entrada)
    tiempos_ejecucion.append(tiempo)
escribir_tiempos_en_archivo(tiempos_ejecucion, ruta_archivo_salida)

import os
import pandas as pd
import matplotlib.pyplot as plt

# Definir las carpetas de origen
carpetas = [
    r"C:\Users\Public\Documents\@UNAM-III CICLO 2024\@HONORIO_ANALISIS ALG\c++_times",
    r"C:\Users\Public\Documents\@UNAM-III CICLO 2024\@HONORIO_ANALISIS ALG\java_times",
    r"C:\Users\Public\Documents\@UNAM-III CICLO 2024\@HONORIO_ANALISIS ALG\python_times",
]

# Nombres de los archivos de tiempo
nombres_archivos = [
    "bubble_times.txt",
    "counting_times.txt",
    "heap_times.txt",
    "insertion_times.txt",
    "merge_times.txt",
    "quick_times.txt",
    "selection_times.txt"
]

# Crear un diccionario para almacenar los resultados
resultados = {nombre: {} for nombre in nombres_archivos}

# Leer los datos de los archivos
for carpeta in carpetas:
    for nombre_archivo in nombres_archivos:
        ruta_archivo = os.path.join(carpeta, nombre_archivo)
        if os.path.exists(ruta_archivo):
            with open(ruta_archivo, 'r') as file:
                datos = file.read().strip().strip('[]').split(', ')
                datos = [float(d.replace('\n', '').strip()) for d in datos if d]  
                lenguaje = os.path.basename(carpeta).replace('_times', '')  
                resultados[nombre_archivo][lenguaje] = datos

# Crear la carpeta 'diagramas' si no existe
carpeta_diagramas = r"C:\Users\Public\Documents\@UNAM-III CICLO 2024\@HONORIO_ANALISIS ALG\diagramas"
os.makedirs(carpeta_diagramas, exist_ok=True)

# Graficar y guardar los resultados
for nombre_archivo, datos in resultados.items():
    if datos: 
        plt.figure(figsize=(10, 6))
    
        etiquetas = list(datos.keys())
        for etiqueta in etiquetas:
            plt.plot(datos[etiqueta], marker='o', label=etiqueta)

        plt.title(f'Tiempos de Ejecución - {nombre_archivo.split("_")[0].capitalize()} Sort')
        plt.xlabel('Tamaño de Entrada (elementos)')
        plt.ylabel('Tiempo (segundos)')
        plt.xticks(ticks=range(len(datos[etiqueta])), labels=["100", "500", "1000", "2000", "3000", "4000", 
                                                             "5000", "6000", "7000", "8000", "9000", 
                                                             "10000", "20000", "30000", "40000", 
                                                             "50000", "60000", "70000", "80000", 
                                                             "90000", "100000"], rotation=45)
        plt.legend()
        plt.grid()
        plt.tight_layout()
        
        # Guardar el gráfico en la carpeta 'diagramas'
        plt.savefig(os.path.join(carpeta_diagramas, f'{nombre_archivo.split("_")[0]}_comparasion.png'))  
        plt.show()  

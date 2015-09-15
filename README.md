# TFG-EtiquetadoSemantico
Librería implementado para el Trabajo Final de Grado. 
Contiene dos algoritmos, que emplean varias funciones para el cálculo de la distancia entre imágenes. 
La librería recibe una configuración de entrada, un banco de imágenes y una imagen inicial y devuelve una serie de imágenes cercanas, que atienden a la configuración inicial.

INPUT:
 - aleatorio: indica si las instancias a emplear en la clasificación se generan de forma aleatoria o no.
 - nombreFich: nombre del fichero del que se extrae la información de las imáganes a usar en la clasificación
 - metrica: tipo de función de distancia a utilizar
 - algoritmo: tipo de modelo algorítmico a utilizar
 - d1: distancia máxima existente entre las imágenes del primer grupo
 - d2: distancia máxima existente entre las imágenes del segundo grupo
 - gap: distancia de separación entre ambos grupos
 - indObjOrigen: imagen origen
 
 OUTPUT:
  - Dos grupos de imágenes que atienden a la configuración inicial de entrada.
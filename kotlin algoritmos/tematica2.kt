//TEMÁTICA 2: FUNCIONES CON ARREGLOS
//Se propone diseñar una solución que incluya una función encargada de crear y cargar un
//arreglo con datos proporcionados por el usuario. Posteriormente, otra función deberá
//recibir dicho arreglo como parámetro y retornar la suma total de sus elementos. Como
//complemento, el estudiante deberá implementar funciones que permitan calcular el
//promedio y determinar el valor máximo del arreglo.

fun cargarArreglo(): IntArray {
    println("¿Cuántos números desea ingresar?:")
    val cantidad = readLine()!!.toInt()

    val arreglo = IntArray(cantidad)

    for (i in 0 until cantidad) {
        println("Ingrese el numero ${i + 1}:")
        arreglo[i] = readLine()?.toIntOrNull() ?: 0
    }
    return arreglo
}

fun calcularSuma(arreglo: IntArray): Int {
    var suma = 0
    for (num in arreglo) {
        suma += num
    }
    return suma
}

fun calcularPromedio(arreglo: IntArray): Double {
    val suma = calcularSuma(arreglo)
    return if (arreglo.isNotEmpty()) suma.toDouble() / arreglo.size else 0.0
}

fun calcularMaximo(arreglo: IntArray): Int {
    var maximo = arreglo[0]
    for (num in arreglo) {
        if (num > maximo) {
            maximo = num
        }
    }
    return maximo
}

fun main() {
    val numeros = cargarArreglo()
    
    val sumaTotal = calcularSuma(numeros)
    val promedio = calcularPromedio(numeros)
    val valorMaximo = calcularMaximo(numeros)

    println("\n ------------ Resultados -------------")
    println("Suma total: $sumaTotal")
    println("Promedio: $promedio")
    println("Valor máximo: $valorMaximo")
}
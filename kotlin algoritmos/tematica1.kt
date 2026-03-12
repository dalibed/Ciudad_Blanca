// TEMÁTICA 1: MANEJO DE ARREGLOS
//Se solicita desarrollar un programa que permita ingresar un conjunto de valores numéricos
//y almacenarlos en un arreglo. Una vez cargados los datos, el programa deberá calcular la
//suma total, el promedio, el valor mayor y el valor menor. Además, el estudiante deberá
//implementar una funcionalidad adicional que permita contar cuántos números son pares y
//cuántos son impares dentro del arreglo.

fun main() {
    println("¡Cuantos números desea ingresar?")
    val cantidad = readLine()!!.toInt()
    
    val numeros = IntArray(cantidad)
    
    for (i in numeros.indices) {
        println("Ingrese el numero ${i + 1}")
        numeros[i] = readLine()?.toIntOrNull() ?: 0
    }

    var suma = 0
    var mayor  = numeros[0]
    var menor  = numeros[0]
    var pares = 0
    var impares = 0
    
    for (num in numeros){
        suma += num
        
        if (num > mayor) {
            mayor = num
        }
        
        if (num < menor) {
            menor = num
        }
        
        if (num % 2 == 0){
            pares++
        }else {
            impares++
        }
    }
    
    val promedio = suma.toDouble() / cantidad
    
    println("\n ------------ Resultados -------------")
    println("Suma total: $suma")
    println("Promedio: $promedio")
    println("Número mayor: $mayor")
    println("Número menor: $menor")
    println("Cantidad de números pares: $pares")
    println("Cantidad de números impares: $impares")
    
}
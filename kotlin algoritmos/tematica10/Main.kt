fun main(){
    val aleatorio = Utilidades.generarNumeroAleatorio(1, 10)
    println("Número aleatorio entre 1 y 10: $aleatorio")

    val cuadrado = Utilidades.cuadrado(5.0)
    println("El cuadrado de 5 es: $cuadrado")

    val raiz = Utilidades.raizCuadrada(25.0)
    println("La raíz cuadrada de 25 es: $raiz")

    val promedio = Utilidades.promedio(listOf(10.0, 20.0, 30.0))
    println("El promedio de 10, 20 y 30 es: $promedio")
}
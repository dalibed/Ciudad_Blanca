fun main() {
    val cal = Calculadora()

    val a = 10.0
    val b = 5.0

    println("Suma: ${cal.calcular(a, b, Operacion.SUMA)}")
    println("Resta: ${cal.calcular(a, b, Operacion.RESTA)}")
    println("Multiplicación: ${cal.calcular(a, b, Operacion.MULTIPLICACION)}")
    println("División: ${cal.calcular(a, b, Operacion.DIVISION)}")
    println("División por cero: ${cal.calcular(a, 0.0, Operacion.DIVISION)}")   

}
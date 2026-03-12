class Calculadora {
    fun calcular(a: Double, b: Double, operacion: Operacion): Double? {
        return when (operacion) {
            Operacion.SUMA -> a + b
            Operacion.RESTA -> a - b
            Operacion.MULTIPLICACION -> a * b
            Operacion.DIVISION -> {
                if (b != 0.0) a / b else {
                    println("Error: División por cero.")
                    null
                }
            }
        }
    }
}
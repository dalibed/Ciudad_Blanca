class Rectangulo(private val base: Double, private val altura: Double) {
    fun calcularArea(): Double {
        return base * altura
    }

    fun calcularPerimetro(): Double {
        return 2 * (base + altura)
    }

    fun mostrarInformacion() {
        println("Base: $base")
        println("Altura: $altura")
        println("Área: ${calcularArea()}")
        println("Perímetro: ${calcularPerimetro()}")
    }
}
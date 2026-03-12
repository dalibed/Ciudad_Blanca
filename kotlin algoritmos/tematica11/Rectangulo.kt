class Rectangulo(private val base: Double, private val altura: Double) : Figura() {
    override fun calcularArea(): Double {
        return base * altura
    }
}
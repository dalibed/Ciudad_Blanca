class Circulo(private val radio: Double) : Figura() {
    override fun calcularArea(): Double {
        return Math.PI * Math.pow(radio, 2.0)
    }
}
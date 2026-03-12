fun main(){
    val rectangulo = Rectangulo(5.0, 3.0)
    rectangulo.mostrarMensaje()
    println("Área del rectángulo: ${rectangulo.calcularArea()}")

    val circulo = Circulo(4.0)
    circulo.mostrarMensaje()
    println("Área del círculo: ${circulo.calcularArea()}")
}
// TEMÁTICA 8: DATA CLASS
// Se propone crear una data class que represente una entidad como un producto o estudiante,
// permitiendo almacenar información de forma eficiente y comparar objetos.

data class Producto(
    val nombre : String,
    val precio : Double,
    val cantidad : Int
)

fun main(){
    
    val producto1 = Producto("Manzanas", 1500.0, 10)
    val producto2 = Producto("Naranjas", 800.0, 20)
    val producto3 = Producto("Peras", 1200.0, 10)

    println("Producto 1: $producto1")
    println("Producto 2: $producto2")
    println("Producto 3: $producto3")

    println("producto1 == producto3? ${producto1 == producto3}") // que sean el mismo producto nombre, manzanas diferente a peras FALSE
    println("producto1 === producto1? ${producto1 === producto1}") // que sean el mismo objeto TRUE

    val producto4 = producto2.copy(cantidad = 5)
    println(producto4)
}
fun main(){
    val vehiculos : List<Vehiculo> = listOf(
        Auto(),
        Moto()
    )

    for (vehiculo in vehiculos) {
        println("Tipo de vehículo: ${vehiculo.obtenerTipo()}")
        vehiculo.arrancar()
        vehiculo.detener()
        println()
    }
}
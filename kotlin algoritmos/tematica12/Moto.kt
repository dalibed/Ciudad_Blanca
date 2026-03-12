class Moto : Vehiculo {
    override fun arrancar() {
        println("La moto ha arrancado.")
    }

    override fun detener() {
        println("La moto se ha detenido.")
    }

    override fun obtenerTipo(): String {
        return "Moto"
    }
}
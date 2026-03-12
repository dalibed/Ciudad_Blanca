class Auto: Vehiculo {
    override fun arrancar() {
        println("El auto está arrancando.")
    }

    override fun detener() {
        println("El auto se ha detenido.")
    }

    override fun obtenerTipo(): String {
        return "Auto"
    }
}

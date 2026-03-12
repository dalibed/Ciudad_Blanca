class Persona (val nombre: String, val edad: Int) {
    fun mostrarInformacion() {
        println("Nombre: $nombre")
        println("Edad: $edad")
    }

    fun esMayorDeEdad(): Boolean {
        return edad >= 18
    }
}
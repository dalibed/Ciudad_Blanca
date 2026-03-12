class Estudiante(private val nombre: String, private val nota: Double) {
    fun mostrarInformacion() {
        println("Nombre: $nombre, Nota: $nota")
    }

    fun estaAprobado(): Boolean {
        return nota >= 3.0
    }
}
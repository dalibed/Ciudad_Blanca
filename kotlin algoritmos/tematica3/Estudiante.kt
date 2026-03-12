class Estudiante(val nombre: String, val edad: Int, val notaFinal: Double){
    fun mostrarInformacion() {
        println("Nombre: $nombre")
        println("Edad: $edad")
        println("Nota Final: $notaFinal")
    }

    fun aprobado(): Boolean {
        return notaFinal >= 3.0
    }
}
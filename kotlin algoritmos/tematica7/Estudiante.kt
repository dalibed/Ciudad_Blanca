class Estudiante(private var _nombre: String, private var _nota: Double){
    var nombre: String
        get() = _nombre
        set(value) {
            if (value.isNotBlank()) {
                _nombre = value
            } else {
                println("El nombre no puede estar vacío.")
            }
        }

    var nota: Double
        get() = _nota
        set(value) {
            if (value in 0.0..5.0) {
                _nota = value
            } else {
                println("La nota debe estar entre 0 y 5.")
            }
        }

    fun mostrarInformacion() {
        println("Nombre: $nombre, Nota: $nota")
    }
}
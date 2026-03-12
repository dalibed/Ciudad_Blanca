class Libro(val titulo: String, val autor: String, val paginas: Int) {
    fun mostrarInformacion() {
        println("Título: $titulo")
        println("Autor: $autor")
        println("Número de páginas: $paginas")
    }
}


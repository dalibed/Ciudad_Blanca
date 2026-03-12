fun main(){
    val estudiantes : List<Estudiante> = listOf(
        Estudiante("Juan", 4.5),
        Estudiante("María", 2.8),
        Estudiante("Carlos", 3.2)
    )

    for (estudiante in estudiantes) {
        estudiante.mostrarInformacion()
        if (estudiante.estaAprobado()) {
            println("El estudiante está aprobado.")
        } else {
            println("El estudiante no está aprobado.")
        }
        println()
    }
}
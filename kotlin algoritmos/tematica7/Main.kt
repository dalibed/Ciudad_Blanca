// TEMÁTICA 7: PROPIEDADES GET Y SET
// Se solicita implementar una clase que utilice métodos get y set para validar los datos
// asignados a sus propiedades, evitando el ingreso de valores inválidos.

fun main(){
    val estudiante = Estudiante("Juan", 4.5)
    estudiante.mostrarInformacion()

    estudiante.nombre = "María"
    estudiante.nota = 3.8
    estudiante.mostrarInformacion()

    estudiante.nombre = ""
    estudiante.nota = 6.0
    estudiante.mostrarInformacion()
}
// TEMÁTICA 3: CLASES Y OBJETOS
// Se solicita diseñar una clase que represente una entidad real, como una Persona, incluyendo
// propiedades como nombre y edad, y métodos que permitan mostrar la información y
// determinar si la persona es mayor de edad. Posteriormente, se deberán crear varios objetos
// de esta clase y demostrar su funcionamiento. Adicionalmente, el estudiante deberá crear
// una clase Estudiante que permita evaluar si un alumno aprueba según su nota final.

fun main() {
    val persona1 = Persona("Luna", 20)
    val persona2 = Persona("Luis", 15)

    println("Información de la persona 1:")
    persona1.mostrarInformacion()
    if (persona1.esMayorDeEdad()) {
        println("${persona1.nombre} es mayor de edad.")
    } else {
        println("${persona1.nombre} no es mayor de edad.")
    }
    
    println("\nInformación de la persona 2:")
    persona2.mostrarInformacion()
    if (persona2.esMayorDeEdad()) {
        println("${persona2.nombre} es mayor de edad.")
    } else {
        println("${persona2.nombre} no es mayor de edad.")
    }

    val estudiante1 = Estudiante("Ana", 22, 3.5)
    val estudiante2 = Estudiante("Carlos", 19, 2.8)

    println("\nInformación del estudiante 1:")
    estudiante1.mostrarInformacion()
    if (estudiante1.aprobado()) {
        println("${estudiante1.nombre} ha aprobado.")
    } else {
        println("${estudiante1.nombre} no ha aprobado.")
    }

    println("\nInformación del estudiante 2:")
    estudiante2.mostrarInformacion()
    if (estudiante2.aprobado()) {
        println("${estudiante2.nombre} ha aprobado.")
    } else {
        println("${estudiante2.nombre} no ha aprobado.")
    }
}
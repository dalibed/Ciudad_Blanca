// TEMÁTICA 4: CONSTRUCTORES
// Se propone implementar una clase que utilice un constructor para inicializar sus
// propiedades. Como ejercicio práctico, el estudiante deberá crear una clase Rectangulo que
// permita calcular su área y perímetro, y otra clase Libro que permita inicializar y mostrar su
// información mediante el uso de un constructor.

fun main(){
    val rectangulo = Rectangulo(5.0, 3.0)
    println("--- RECTÁNGULO ---")
    rectangulo.mostrarInformacion()

    val libro = Libro("Cien años de soledad", "Gabriel García Márquez", 400)
    println("--- LIBRO ---")
    libro.mostrarInformacion()
}


// TEMÁTICA 5: COLABORACIÓN ENTRE CLASES
// Se solicita implementar un sistema donde dos clases colaboren entre sí. Por ejemplo, una
// clase Banco y una clase Cliente, donde el banco gestione los depósitos de sus clientes. El
// objetivo es demostrar cómo una clase puede utilizar objetos de otra clase para cumplir sus
// funciones.

fun main(){
    val banco = Banco("Banco Central")
    
    val cliente1 = Cliente("Juan Perez", 1000.0)
    val cliente2 = Cliente("Maria Gomez", 2000.0)

    banco.agregarCliente(cliente1)
    banco.agregarCliente(cliente2)

    banco.mostrarClientes()

    banco.depositar("Juan Perez", 500.0)
    banco.depositar("Maria Gomez", 1000.0)
    banco.depositar("Carlos Sanchez", 300.0) // Cliente no existente  

    banco.mostrarClientes()
}
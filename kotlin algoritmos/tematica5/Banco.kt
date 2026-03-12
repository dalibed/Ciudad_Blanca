class Banco(val nombreBanco: String){
    private val clientes = mutableListOf<Cliente>()

    fun agregarCliente(cliente: Cliente) {
        clientes.add(cliente)
        println("Cliente ${cliente.nombre} agregado al banco $nombreBanco.")
    }

    fun depositar(nombreCliente: String, monto: Double) {
        val cliente = clientes.find { it.nombre == nombreCliente }
        if (cliente != null) {
            cliente.saldo += monto
            println("Depósito de $monto realizado para ${cliente.nombre}. Nuevo saldo: ${cliente.saldo}")
        } else {
            println("Cliente $nombreCliente no encontrado en el banco $nombreBanco.")
        }
    }

    fun mostrarClientes() {
        println("Clientes del banco $nombreBanco:")
        for (cliente in clientes) {
            cliente.mostrarInformacion()
        }
    }
}
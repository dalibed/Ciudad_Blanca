class CuentaBancaria(private var saldo: Double = 0.0) {
    fun depositar(monto: Double) {
        if (monto > 0) {
            saldo += monto
            println("Depósito de $monto realizado. Nuevo saldo: $saldo")
        } else {
            println("Monto de depósito inválido.")
        }
    }

    fun retirar(monto: Double) {
        if (monto > 0 && monto <= saldo) {
            saldo -= monto
            println("Retiro de $monto realizado. Nuevo saldo: $saldo")
        } else {
            println("Monto de retiro inválido o saldo insuficiente.")
        }
    }

    fun consultarSaldo(): Double {
        return saldo
    }
}
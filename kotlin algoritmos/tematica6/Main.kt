// TEMÁTICA 6: MODIFICADORES DE ACCESO
// Se propone crear una clase que contenga propiedades privadas y métodos públicos, de
// manera que los datos solo puedan ser modificados mediante métodos definidos en la clase.
// Esto permitirá comprender el principio de encapsulamiento.

fun main(){
    val cuenta  = CuentaBancaria(1000.0)

    println("Saldo inicial: ${cuenta.consultarSaldo()}")
    cuenta.depositar(500.0)
    cuenta.retirar(200.0)
    println("Saldo final: ${cuenta.consultarSaldo()}")
}
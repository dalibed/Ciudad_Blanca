class Cliente(val nombre: String,  var saldo: Double) {
    fun mostrarInformacion() {
        println("Cliente: $nombre")
        println("Saldo: $saldo")
    }

}
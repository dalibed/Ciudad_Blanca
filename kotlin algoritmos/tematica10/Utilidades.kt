import kotlin.random.Random
import kotlin.math.pow
import kotlin.math.sqrt

object Utilidades {
    fun generarNumeroAleatorio(min: Int, max: Int): Int {
        return Random.nextInt(min, max + 1)
    }

    fun cuadrado(numero: Double): Double {
        return numero.pow(2)
    }

    fun raizCuadrada(numero: Double): Double {
        return sqrt(numero)
    }

    fun promedio(numeros: List<Double>): Double {
        return if (numeros.isNotEmpty()) {
            numeros.sum() / numeros.size
        } else {
            0.0
        }
    }
}
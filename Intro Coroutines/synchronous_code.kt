import kotlin.system.*
import kotlinx.coroutines.*

fun synchronous_code() {
    val time = measureTimeMillis {
        runBlocking {
            println("Weather forecast")
            val forecast: Deferred<String> = async {
                printForecast()
            }
            val temperature: Deferred<String> = async {
                printTemperature()
            }
        }
    }
    println("${forecast.await()} ${temperature.await()}")
    println("Execution time: ${time / 1000.0} seconds")
}
fun asynchronous_code() {
    val time = measureTimeMillis {
        runBlocking {
            println("Weather forecast")
            launch {
                printForecast()
            }
            launch {
                printTemperature()
            }
            println("This line is printed right after Weather forecast line")
            getWeatherReport()
        }
    }
    println("Execution time: ${time / 1000.0} seconds")

}
suspend fun getWeatherReport() = coroutineScope {
    val forecast = async { getForecast() }
    val temperature = async { getTemperature() }
    "${forecast.await()} ${temperature.await()}"
}
fun main() {
    synchronous_code()
    asynchronous_code()
}
suspend fun printForecast() : String {
    delay(1000)
    return "Sunny"
}

suspend fun printTemperature() {
    delay(1000)
    return "30\u00b0C"
} 
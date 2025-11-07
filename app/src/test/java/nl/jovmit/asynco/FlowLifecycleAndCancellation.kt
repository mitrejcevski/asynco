package nl.jovmit.asynco

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

class FlowLifecycleAndCancellation {

  val tickingFlow = flow {
    try {
      repeat(10) {
        emit(it)
        delay(1.seconds)
      }
    } catch (exception: Exception) {
      //either check if the exception is a CancellationException
      //and if it has to be re-thrown
      //OR
      //check for the specific exceptions that you need to handle
      //based on the problem (i.e. IOException, YouDomainSpecificException, etc...)
      if (exception is CancellationException) {
        throw exception
      }
      println("Caught: $exception")
    } finally {
      println("Collection completed or cancelled")
    }
  }

  @Test
  fun check() = runBlocking {
    val job = launch {
      tickingFlow
        .onCompletion { println("Flow collection completed") }
        .collect { println(it) }
    }
    delay(1.5.seconds)
    job.cancel()
  }
}
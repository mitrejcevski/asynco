package nl.jovmit.asynco

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FlowTransformations {

  val flow = flowOf(1, 2, 3, 4, 5)
    .onStart { println("Flow is starting") }
    .onEach { item ->
      delay(1000)
      println("Processing $item")
    }
    .filter { it != 4 } //move this above onEach. What's the result then?
    .flowOn(Dispatchers.Default)

  @Test
  fun check() = runTest {
    flow.collect { println("Collected: $it") }
  }
}
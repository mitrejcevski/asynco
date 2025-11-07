package nl.jovmit.asynco

import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CollectingFlows {

  @Test
  fun check() = runTest {
    flowOf(1, 2, 3, 4, 5)
      .onEach { item -> println(item) }
      .filter { item -> item % 2 == 0 }
      .collect { item -> println(item) }

    val integers = flowOf(1, 2, 3, 4, 5)
    println("Last item: ${integers.last()}")

    val list = mutableListOf<Int>()
    integers.toCollection(list)
    println("Collected List: $list")
  }
}

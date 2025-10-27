package nl.jovmit.asynco

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ColdFlowsExplained {

  @Test
  fun run() = runBlocking {
    val myFlow = flow {
      println("Flow Started...")
      emit(1)
      emit(2)
      emit(3)
    }

    println("Before collecting...")
    myFlow.collect { item ->
      println("Collector #1: $item")
    }
    myFlow.collect { item ->
      println("Collector #2: $item")
    }
  }
}
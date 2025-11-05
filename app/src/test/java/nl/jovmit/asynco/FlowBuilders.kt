package nl.jovmit.asynco

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Test

class FlowBuilders {

  @Test
  fun check() {
    val flowOfInts = flow {
      emit(1)
      emit(2)
    }

    val flowOfStrings = flowOf("one", "two", "three")

    val emptyFlow: Flow<Boolean> = emptyFlow<Boolean>()

    val flowOfNames = listOf("Jov", "Alice").asFlow()
  }
}
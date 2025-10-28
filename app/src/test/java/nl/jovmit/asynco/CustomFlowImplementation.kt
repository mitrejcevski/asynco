package nl.jovmit.asynco

import kotlinx.coroutines.runBlocking
import org.junit.Test

class CustomFlowImplementation {

  @Test
  fun check() = runBlocking {
    val stream1 = streamOf(1, 2, 3)
      .filter { it > 1 }
      .map { it*it }

    val stream2 = streamOf("one", "two", "three", "four")
      .map { it.uppercase() }
      .filter { it.length > 3 }

    stream1.collect { value -> println("[S1] -> $value") }
    stream2.collect { value -> println("[S2] -> $value") }
  }
}

fun <T, R> Stream<T>.map(block: suspend (T) -> R) = transform { value ->
  emit(block(value))
}

fun <T> Stream<T>.filter(predicate: suspend (T) -> Boolean) = transform { value ->
  if (predicate(value)) emit(value)
}

fun <T, R> Stream<T>.transform(transform: suspend Collector<R>.(value: T) -> Unit) = stream {
  collect { value -> transform(value) }
}

suspend fun <T> Stream<T>.collect(collector: suspend (value: T) -> Unit) {
  collect(object : Collector<T> {
    override suspend fun emit(value: T) {
      collector.invoke(value)
    }
  })
}

fun <T> streamOf(vararg values: T): Stream<T> {
  return stream { values.forEach { emit(it) } }
}

fun <T> stream(block: suspend Collector<T>.() -> Unit): Stream<T> {
  return object : Stream<T> {
    override suspend fun collect(collector: Collector<T>) {
      collector.block()
    }
  }
}

interface Stream<T> {
  suspend fun collect(collector: Collector<T>)
}

interface Collector<T> {
  suspend fun emit(value: T)
}
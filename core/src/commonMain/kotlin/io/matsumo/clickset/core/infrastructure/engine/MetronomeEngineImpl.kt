package io.matsumo.clickset.core.infrastructure.engine

import io.matsumo.clickset.core.domain.engine.MetronomeEngine
import io.matsumo.clickset.core.domain.model.TickEvent
import io.matsumo.clickset.core.infrastructure.util.monotonicTimeNanos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

private const val NANOS_PER_MINUTE = 60_000_000_000L
private const val NANOS_PER_MILLI = 1_000_000L

class MetronomeEngineImpl : MetronomeEngine {
    override fun tick(
        bpm: Int,
        beatsPerMeasure: Int,
    ): Flow<TickEvent> =
        flow {
            val intervalNanos = NANOS_PER_MINUTE / bpm
            var beat = 1
            var nextTickNanos = monotonicTimeNanos()

            while (true) {
                emit(TickEvent(nextTickNanos, beat, beatsPerMeasure, bpm))

                beat = if (beat >= beatsPerMeasure) 1 else beat + 1
                nextTickNanos += intervalNanos

                val delayNanos = nextTickNanos - monotonicTimeNanos()
                if (delayNanos > 0) delay(delayNanos / NANOS_PER_MILLI)
            }
        }.flowOn(Dispatchers.Default)
}

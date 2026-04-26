package io.matsumo.clickset.core.domain.engine

import io.matsumo.clickset.core.domain.model.TickEvent
import kotlinx.coroutines.flow.Flow

interface MetronomeEngine {
    fun tick(
        bpm: Int,
        beatsPerMeasure: Int,
    ): Flow<TickEvent>
}

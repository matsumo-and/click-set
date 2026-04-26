package io.matsumo.clickset.core.domain.model

data class TickEvent(
    val scheduledTimeNanos: Long,
    val beatNumber: Int,
    val beatsPerMeasure: Int,
    val bpm: Int,
) {
    val isDownbeat: Boolean get() = beatNumber == 1
}

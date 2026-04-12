package io.matsumo.clickset.core.domain.model

data class Song(
    val id: Long,
    val title: String,
    val bpm: Int,
    val beatsPerMeasure: Int,
    val createdAt: Long,
    val updatedAt: Long,
)

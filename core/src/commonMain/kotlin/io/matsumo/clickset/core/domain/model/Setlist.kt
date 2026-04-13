package io.matsumo.clickset.core.domain.model

data class Setlist(
    val id: Long,
    val title: String,
    val songs: List<Song>,
    val createdAt: Long,
    val updatedAt: Long,
)

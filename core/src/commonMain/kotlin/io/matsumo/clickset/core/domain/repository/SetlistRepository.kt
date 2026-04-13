package io.matsumo.clickset.core.domain.repository

import io.matsumo.clickset.core.domain.model.Setlist

interface SetlistRepository {
    suspend fun getAllSetlists(): List<Setlist>

    suspend fun getSetlistById(id: Long): Setlist?

    suspend fun createSetlist(title: String): Setlist

    suspend fun updateSetlist(setlist: Setlist): Setlist

    suspend fun deleteSetlist(id: Long)

    suspend fun addSongToSetlist(
        setlistId: Long,
        songId: Long,
    )

    suspend fun removeSongFromSetlist(
        setlistId: Long,
        songId: Long,
    )

    suspend fun reorderSongs(
        setlistId: Long,
        songIds: List<Long>,
    )
}

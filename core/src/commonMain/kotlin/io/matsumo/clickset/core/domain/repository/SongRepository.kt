package io.matsumo.clickset.core.domain.repository

import io.matsumo.clickset.core.domain.model.Song

interface SongRepository {
    suspend fun getAllSongs(): List<Song>

    suspend fun getSongById(id: Long): Song?

    suspend fun createSong(
        title: String,
        bpm: Int,
        beatsPerMeasure: Int,
    ): Song

    suspend fun updateSong(song: Song): Song

    suspend fun deleteSong(id: Long)
}

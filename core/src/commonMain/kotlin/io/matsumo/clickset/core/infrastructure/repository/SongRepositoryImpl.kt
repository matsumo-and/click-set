package io.matsumo.clickset.core.infrastructure.repository

import io.matsumo.clickset.core.ClickSetDatabase
import io.matsumo.clickset.core.SongEntity
import io.matsumo.clickset.core.domain.model.Song
import io.matsumo.clickset.core.domain.repository.SongRepository
import io.matsumo.clickset.core.infrastructure.database.DatabaseDriverFactory
import io.matsumo.clickset.core.infrastructure.util.currentTimeMillis
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SongRepositoryImpl(driverFactory: DatabaseDriverFactory) : SongRepository {
    private val database = ClickSetDatabase(driverFactory.createDriver())
    private val queries = database.songQueries

    override suspend fun getAllSongs(): List<Song> =
        withContext(Dispatchers.Default) {
            queries.getAllSongs().executeAsList().map(SongEntity::toDomain)
        }

    override suspend fun getSongById(id: Long): Song? =
        withContext(Dispatchers.Default) {
            queries.getSongById(id).executeAsOneOrNull()?.toDomain()
        }

    override suspend fun createSong(
        title: String,
        bpm: Int,
        beatsPerMeasure: Int,
    ): Song =
        withContext(Dispatchers.Default) {
            val now = currentTimeMillis()
            database.transactionWithResult {
                queries.insertSong(
                    title = title,
                    bpm = bpm.toLong(),
                    beats_per_measure = beatsPerMeasure.toLong(),
                    created_at = now,
                    updated_at = now,
                )
                val id = queries.lastInsertRowId().executeAsOne()
                queries.getSongById(id).executeAsOne().toDomain()
            }
        }

    override suspend fun updateSong(song: Song): Song =
        withContext(Dispatchers.Default) {
            val now = currentTimeMillis()
            database.transactionWithResult {
                queries.updateSong(
                    title = song.title,
                    bpm = song.bpm.toLong(),
                    beats_per_measure = song.beatsPerMeasure.toLong(),
                    updated_at = now,
                    id = song.id,
                )
                queries.getSongById(song.id).executeAsOne().toDomain()
            }
        }

    override suspend fun deleteSong(id: Long): Unit =
        withContext(Dispatchers.Default) {
            queries.deleteSong(id)
        }
}

private fun SongEntity.toDomain() =
    Song(
        id = id,
        title = title,
        bpm = bpm.toInt(),
        beatsPerMeasure = beats_per_measure.toInt(),
        createdAt = created_at,
        updatedAt = updated_at,
    )

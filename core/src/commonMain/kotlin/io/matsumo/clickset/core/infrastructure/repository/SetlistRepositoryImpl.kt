package io.matsumo.clickset.core.infrastructure.repository

import io.matsumo.clickset.core.ClickSetDatabase
import io.matsumo.clickset.core.GetSongsForSetlist
import io.matsumo.clickset.core.SetlistEntity
import io.matsumo.clickset.core.domain.model.Setlist
import io.matsumo.clickset.core.domain.model.Song
import io.matsumo.clickset.core.domain.repository.SetlistRepository
import io.matsumo.clickset.core.infrastructure.database.DatabaseDriverFactory
import io.matsumo.clickset.core.infrastructure.util.currentTimeMillis
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SetlistRepositoryImpl(driverFactory: DatabaseDriverFactory) : SetlistRepository {
    private val database = ClickSetDatabase(driverFactory.createDriver())
    private val setlistQueries = database.setlistQueries
    private val songQueries = database.songQueries

    override suspend fun getAllSetlists(): List<Setlist> =
        withContext(Dispatchers.Default) {
            setlistQueries.getAllSetlists().executeAsList().map { entity ->
                val songs = setlistQueries.getSongsForSetlist(entity.id).executeAsList().map(GetSongsForSetlist::toDomain)
                entity.toDomain(songs)
            }
        }

    override suspend fun getSetlistById(id: Long): Setlist? =
        withContext(Dispatchers.Default) {
            val entity = setlistQueries.getSetlistById(id).executeAsOneOrNull() ?: return@withContext null
            val songs = setlistQueries.getSongsForSetlist(id).executeAsList().map(GetSongsForSetlist::toDomain)
            entity.toDomain(songs)
        }

    override suspend fun createSetlist(title: String): Setlist =
        withContext(Dispatchers.Default) {
            val now = currentTimeMillis()
            database.transactionWithResult {
                setlistQueries.insertSetlist(
                    title = title,
                    created_at = now,
                    updated_at = now,
                )
                val id = setlistQueries.lastInsertRowId().executeAsOne()
                setlistQueries.getSetlistById(id).executeAsOne().toDomain(emptyList())
            }
        }

    override suspend fun updateSetlist(setlist: Setlist): Setlist =
        withContext(Dispatchers.Default) {
            val now = currentTimeMillis()
            database.transactionWithResult {
                setlistQueries.updateSetlist(
                    title = setlist.title,
                    updated_at = now,
                    id = setlist.id,
                )
                val songs = setlistQueries.getSongsForSetlist(setlist.id).executeAsList().map(GetSongsForSetlist::toDomain)
                setlistQueries.getSetlistById(setlist.id).executeAsOne().toDomain(songs)
            }
        }

    override suspend fun deleteSetlist(id: Long): Unit =
        withContext(Dispatchers.Default) {
            setlistQueries.deleteSetlist(id)
        }

    override suspend fun addSongToSetlist(
        setlistId: Long,
        songId: Long,
    ): Unit =
        withContext(Dispatchers.Default) {
            val maxPosition = setlistQueries.getMaxPositionForSetlist(setlistId).executeAsOne().MAX ?: -1L
            setlistQueries.insertSetlistSong(
                setlist_id = setlistId,
                song_id = songId,
                position = maxPosition + 1,
            )
        }

    override suspend fun removeSongFromSetlist(
        setlistId: Long,
        songId: Long,
    ): Unit =
        withContext(Dispatchers.Default) {
            setlistQueries.deleteSetlistSong(setlist_id = setlistId, song_id = songId)
        }

    override suspend fun reorderSongs(
        setlistId: Long,
        songIds: List<Long>,
    ): Unit =
        withContext(Dispatchers.Default) {
            database.transaction {
                setlistQueries.deleteAllSongsFromSetlist(setlistId)
                songIds.forEachIndexed { index, songId ->
                    setlistQueries.insertSetlistSong(
                        setlist_id = setlistId,
                        song_id = songId,
                        position = index.toLong(),
                    )
                }
            }
        }
}

private fun SetlistEntity.toDomain(songs: List<Song>) =
    Setlist(
        id = id,
        title = title,
        songs = songs,
        createdAt = created_at,
        updatedAt = updated_at,
    )

private fun GetSongsForSetlist.toDomain() =
    Song(
        id = id,
        title = title,
        bpm = bpm.toInt(),
        beatsPerMeasure = beats_per_measure.toInt(),
        createdAt = created_at,
        updatedAt = updated_at,
    )

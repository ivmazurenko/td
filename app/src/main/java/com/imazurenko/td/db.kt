package com.imazurenko.td

import androidx.room.*

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "text") val text: String
)

fun createEmpty() = NoteEntity(0, "")

@Dao
interface NoteEntityDao {
    @Query("SELECT * FROM NoteEntity")
    suspend fun getAll(): List<NoteEntity>

    @Query("SELECT * FROM NoteEntity WHERE uid LIKE :uid LIMIT 1")
    suspend fun loadById(uid: Long): NoteEntity

    @Insert
    suspend fun insert(noteEntity: NoteEntity): Long

    @Update
    suspend fun update(vararg noteEntities: NoteEntity)

    @Query("DELETE FROM NoteEntity where :uid == uid")
    suspend fun delete(uid: Long)
}

@Database(entities = arrayOf(NoteEntity::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteEntityDao(): NoteEntityDao
}



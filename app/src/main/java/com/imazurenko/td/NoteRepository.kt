package com.imazurenko.td

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(appDatabase: AppDatabase) {
    private var noteEntityDao: NoteEntityDao = appDatabase.noteEntityDao()

    val updatedEvent = Event<NoteRepositoryUpdatedEventArgs>()

    var currentId: Long? = null
        set(value) {
            field = value
        }

    suspend fun getByUid(uid: Long): NoteEntity {
        return noteEntityDao.loadById(uid)
    }

    suspend fun getAll(): List<NoteEntity> {
        return noteEntityDao.getAll().toList()
    }

    suspend fun update(noteEntity: NoteEntity) {
        noteEntityDao.update(noteEntity)
        updatedEvent.invoke(NoteRepositoryUpdatedEventArgs.Update)
    }

    suspend fun insert(noteEntity: NoteEntity): Long {
        val insert = noteEntityDao.insert(noteEntity)
        updatedEvent.invoke(NoteRepositoryUpdatedEventArgs.Insert)

        return insert
    }

    suspend fun delete(uid: Long) {
        noteEntityDao.delete(uid)
        updatedEvent.invoke(NoteRepositoryUpdatedEventArgs.Delete)
    }
}

enum class NoteRepositoryUpdatedEventArgs {
    Insert,
    Update,
    Delete,
}

package com.imazurenko.td

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(appDatabase: AppDatabase) {
    private var noteEntityDao: NoteEntityDao = appDatabase.noteEntityDao()

    val updatedEvent = Event()

    var currentId: Long? = null
        set(value) {
            field = value
            updatedEvent.invoke()
        }

    suspend fun getByUid(uid: Long): NoteEntity {
        return noteEntityDao.loadById(uid)
    }

    suspend fun getAll(): List<NoteEntity> {
        return noteEntityDao.getAll().toList()
    }

    suspend fun update(noteEntity: NoteEntity) {
        noteEntityDao.update(noteEntity)
        updatedEvent.invoke()
    }

    suspend fun insert(noteEntity: NoteEntity): Long {
        val insert = noteEntityDao.insert(noteEntity)
        updatedEvent.invoke()

        return insert
    }

    suspend fun delete(uid: Long) {
        noteEntityDao.delete(uid)
        updatedEvent.invoke()
    }
}
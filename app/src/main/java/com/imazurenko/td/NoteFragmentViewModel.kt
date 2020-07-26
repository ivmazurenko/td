package com.imazurenko.td

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteFragmentViewModel @Inject constructor(
    private val repository: NoteRepository
) :
    ViewModel() {

    val text = MutableLiveData<String>()
    private var _uid: Long = 0

    fun init(uid: Long) {
        _uid = uid
        repository.currentId = uid

        if (uid == 0L)
            init(createEmpty())
        else
            viewModelScope.launch {
                init(repository.getByUid(uid))
            }
    }

    private fun init(noteEntity: NoteEntity) {
        text.value = noteEntity.text
    }

    fun saveAndGetNavigationTarget(): NavigationTarget {
        viewModelScope.launch {
            val note = NoteEntity(_uid, text.value!!)
            if (_uid != 0L) {
                repository.update(note)
            } else {
                repository.insert(note)
            }
        }
        return Back()
    }
}
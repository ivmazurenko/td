package com.imazurenko.td

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteListFragmentViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel(), IEventListener {

    var itemViewModels: MutableLiveData<List<NoteListItemViewModel>> = MutableLiveData(emptyList())

    init {
        repository.updatedEvent.add(this)
        update()
    }

    override fun onEvent() {
        update()
    }

    private fun update() {
        viewModelScope.launch {
            itemViewModels.value =
                repository.getAll()
                    .map { NoteListItemViewModel(it.uid, it.text) }
        }
    }

    fun getNavigationTarget() = ItemTarget(0)

    public override fun onCleared() {
        repository.updatedEvent.remove(this)
        super.onCleared()
    }
}
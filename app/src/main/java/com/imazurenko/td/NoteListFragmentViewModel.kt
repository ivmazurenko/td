package com.imazurenko.td

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteListFragmentViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel(), IEventListener<NoteRepositoryUpdatedEventArgs> {

    var itemViewModels: MutableLiveData<List<NoteListItemViewModel>> = MutableLiveData(emptyList())

    private fun self(): NoteListFragmentViewModel = this

    init {
        noteRepository.updatedEvent.add(this)
        viewModelScope.launch {
            itemViewModels.value =
                noteRepository.getAll()
                    .map { NoteListItemViewModel(self(), it.uid, it.text) }
        }
    }

    override fun onEvent(args: NoteRepositoryUpdatedEventArgs) {
        viewModelScope.launch {
            if (itemViewModels.value != null) {
                val allRepositoryItems = noteRepository.getAll()

                when (args) {
                    NoteRepositoryUpdatedEventArgs.Delete -> {
                        self().itemViewModels.value =
                            itemViewModels.value!!.filter { viewModel -> !allRepositoryItems.all { noteEntity -> noteEntity.uid != viewModel.uid } }
                    }
                    NoteRepositoryUpdatedEventArgs.Update -> {
                        allRepositoryItems.forEach { noteEntity ->
                            val noteListItemViewModel =
                                self().itemViewModels.value!!.firstOrNull { viewModel -> viewModel.uid == noteEntity.uid }
                            noteListItemViewModel?.update(noteEntity)
                        }
                    }
                    NoteRepositoryUpdatedEventArgs.Insert -> {
                        val newItems = allRepositoryItems.filter { noteEntity ->
                            self().itemViewModels.value!!.all { viewModel -> viewModel.uid != noteEntity.uid }
                        }.map { NoteListItemViewModel(self(), it.uid, it.text) }

                        self().itemViewModels.value = itemViewModels.value!!.plus(newItems)
                    }
                }
            }
        }
    }

    fun delete(uid: Long) {
        viewModelScope.launch {
            noteRepository.delete(uid)
        }
    }

    fun getNavigationTarget() = ItemTarget(0)

    public override fun onCleared() {
        noteRepository.updatedEvent.remove(this)
        super.onCleared()
    }

    fun resetSelection(uid: Long) {
        itemViewModels.value?.forEach { viewModel ->
            if (viewModel.uid != uid)
                viewModel.isSelected.value = false
        }
    }
}
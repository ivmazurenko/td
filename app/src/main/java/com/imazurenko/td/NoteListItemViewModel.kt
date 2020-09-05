package com.imazurenko.td

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NoteListItemViewModel(
    private val noteListFragmentViewModel: NoteListFragmentViewModel,
    val uid: Long,
    initialText: String
) : ViewModel() {

    val isSelected = MutableLiveData<Boolean>(false)
    val text = MutableLiveData<String>(initialText)

    fun getNavigationTarget() = ItemTarget(uid)

    fun getTextForCopy() = text.value

    fun select(): Boolean {
        noteListFragmentViewModel.resetSelection(uid)
        this.isSelected.value = true

        return true
    }

    fun deselect() {
        this.isSelected.value = false
    }

    fun delete(): Boolean {
        noteListFragmentViewModel.delete(uid)
        return false
    }

    fun update(it: NoteEntity) {
        this.text.value = it.text
    }
}
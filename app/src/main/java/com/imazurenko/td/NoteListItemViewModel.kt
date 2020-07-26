package com.imazurenko.td

import androidx.lifecycle.ViewModel

class NoteListItemViewModel(
    val uid: Long,
    val text: String
) : ViewModel() {
    fun getNavigationTarget() = ItemTarget(uid)
}
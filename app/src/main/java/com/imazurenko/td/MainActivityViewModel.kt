package com.imazurenko.td

import android.view.MenuItem
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {
    fun menuItemSelected(item: MenuItem): NavigationTarget {
        return when (item.itemId) {
            R.id.MainActivityToolbarDeleteItem -> {
                viewModelScope.launch {
                    val currentId = noteRepository.currentId
                    if (currentId != null) {
                        noteRepository.delete(currentId)
                    }
                }

                Back()
            }

            R.id.MainActivityToolbarCreateItem -> {
                ItemTarget(0)
            }

            else -> throw IllegalArgumentException()
        }
    }
}
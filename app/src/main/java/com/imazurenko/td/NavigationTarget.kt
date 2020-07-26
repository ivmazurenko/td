package com.imazurenko.td

import androidx.navigation.NavDirections

abstract class NavigationTarget

class ItemTarget(val uid: Long) : NavigationTarget()

class Back : NavigationTarget()

fun NavigationTarget.toNavDirections(): NavDirections = when (this) {
    is ItemTarget -> NoteListFragmentDirections.actionNoteListFragmentToNoteFragment(this.uid)
    else -> throw IllegalArgumentException()
}
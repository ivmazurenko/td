package com.imazurenko.td

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("android:submitNoteListItemViewModelList")
fun submitNoteListItemViewModelList(view: RecyclerView, list: List<NoteListItemViewModel>) {
    val listAdapter = view.adapter as? ListAdapter<NoteListItemViewModel, *>
    listAdapter?.submitList(list)
}

@BindingAdapter("android:navigate")
fun navigate(view: View, targetGetter: () -> NavigationTarget) {
    view.setOnClickListener {
        val navController = view.findNavController()
        navController.makeActionAndNavigate(targetGetter)
    }
}

public fun NavController.makeActionAndNavigate(targetGetter: () -> NavigationTarget) {
    val target = targetGetter()

    if (target is Back) {
        this.popBackStack()

    } else {
        val directions = target.toNavDirections()
        this.navigate(directions)
    }
}


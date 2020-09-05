package com.imazurenko.td

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

@BindingAdapter("android:copyToClipboardOnClick")
fun copyToClipboardOnClick(view: View, textGetter: () -> String) {
    view.setOnClickListener {
        val clipboardManager = view.context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Note", textGetter())

        clipboardManager.setPrimaryClip(clipData)

        val copied = view.context.resources.getString(R.string.copied);
        Toast.makeText(view.context, copied, Toast.LENGTH_SHORT).show()
    }
}

@BindingAdapter("android:setOnClickListenerWithConfirmation")
fun setOnClickListenerWithConfirmation(view: View, action: () -> Boolean) {
    view.setOnClickListener {
        val alertDialog: AlertDialog? = view.context.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.yes) { _, _ -> action() }
                setNegativeButton(R.string.no) { _, _ -> }
                setTitle(R.string.are_you_sure_you_want_to_delete_this_note)
            }
            builder.create()
        }

        alertDialog?.show()
    }
}

@BindingAdapter("android:visibleOrGone")
fun visibleOrGone(view: View, isVisible: Boolean) {
    view.visibility = when {
        isVisible -> View.VISIBLE
        else -> View.GONE
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


package com.imazurenko.td

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.imazurenko.td.databinding.NoteListItemBinding

class NoteListRecyclerViewAdapter :
    ListAdapter<NoteListItemViewModel, NoteListRecyclerViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListRecyclerViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = NoteListItemBinding.inflate(layoutInflater, parent, false)
        return NoteListRecyclerViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NoteListRecyclerViewHolder, position: Int) {
        val itemViewModel = getItem(position)
        holder.bind(itemViewModel)
    }
}

private class DiffCallback : DiffUtil.ItemCallback<NoteListItemViewModel>() {

    override fun areItemsTheSame(
        oldItem: NoteListItemViewModel,
        newItem: NoteListItemViewModel
    ): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(
        oldItem: NoteListItemViewModel,
        newItem: NoteListItemViewModel
    ): Boolean {
        return oldItem.text == newItem.text
    }
}
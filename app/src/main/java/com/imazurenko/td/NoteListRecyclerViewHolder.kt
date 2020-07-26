package com.imazurenko.td

import androidx.recyclerview.widget.RecyclerView
import com.imazurenko.td.databinding.NoteListItemBinding

class NoteListRecyclerViewHolder(private val binding: NoteListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(viewModel: NoteListItemViewModel) {
        binding.viewModel = viewModel
    }
}

package com.example.androidlab.ui.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.androidlab.databinding.ItemNoteBinding

class NoteAdapter(
    private val inflater: LayoutInflater,
    private val onNoteListener: OnNoteListener
) : ListAdapter<NoteEntity, NoteViewHolder>(NoteDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(inflater, parent, false)
        return NoteViewHolder(binding, onNoteListener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    private object NoteDiffer : DiffUtil.ItemCallback<NoteEntity>() {
        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
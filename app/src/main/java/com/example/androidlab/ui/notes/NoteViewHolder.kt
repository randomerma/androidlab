package com.example.androidlab.ui.notes

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlab.databinding.ItemNoteBinding

class NoteViewHolder(
    private val binding: ItemNoteBinding,
    private val onNoteListener: OnNoteListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    fun bindTo(noteEntity: NoteEntity) {
        binding.textView.text = noteEntity.info

        binding.root.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        onNoteListener.onNoteClick(adapterPosition)
    }
}
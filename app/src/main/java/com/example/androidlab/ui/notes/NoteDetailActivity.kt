package com.example.androidlab.ui.notes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlab.R
import com.example.androidlab.databinding.ActivityNoteDetailsBinding

class NoteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarNote)

        val curId: Long
        val isEdit: Boolean
        val extras = intent.extras
        if (extras != null) {
            isEdit = extras.getBoolean(NotesFragment.NOTE_EDIT)
            if (isEdit) {
                val curInfo = extras.getString(NotesFragment.NOTE_INFO)
                curId = extras.getLong(NotesFragment.NOTE_ID, -1L)
                if (curId > 0L && !curInfo.isNullOrEmpty()) {
                    binding.tvNoteInfo.setText(curInfo)
                }
                binding.btnSave.setOnClickListener {
                    val newInfo = binding.tvNoteInfo.text.toString()
                    if (newInfo.isNotEmpty()) {
                        val returnIntent = Intent()
                        returnIntent.putExtra(NotesFragment.NOTE_ID, curId)
                        returnIntent.putExtra(NotesFragment.NOTE_ID, newInfo)
                        setResult(NotesFragment.RESULT_EDIT, intent)
                        finish()
                    }
                }
                binding.btnDelete.setOnClickListener {
                    val returnIntent = Intent()
                    returnIntent.putExtra(NotesFragment.NOTE_ID, curId)
                    setResult(NotesFragment.RESULT_DELETE, intent)
                    finish()
                }
                title = getString(R.string.title_edit_note)
            } else {
                binding.btnSave.setOnClickListener {
                    val newInfo = binding.tvNoteInfo.text.toString()
                    if (newInfo.isNotEmpty()) {
                        val returnIntent = Intent()
                        returnIntent.putExtra(NotesFragment.NOTE_INFO, newInfo)
                        setResult(RESULT_OK, returnIntent)
                        finish()
                    }
                }
                binding.btnDelete.setOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }
                title = getString(R.string.title_add_note)
            }
        }

    }
}
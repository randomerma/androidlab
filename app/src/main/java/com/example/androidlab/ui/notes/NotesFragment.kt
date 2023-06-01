package com.example.androidlab.ui.notes

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidlab.LabApplication
import com.example.androidlab.databinding.FragmentNotesBinding

class NotesFragment : Fragment(), OnNoteListener {

    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((requireActivity().application as LabApplication).noteRepository)
    }

    private lateinit var binding: FragmentNotesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val notesAdapter = NoteAdapter(layoutInflater, this)
        binding.rvNotes.layoutManager = LinearLayoutManager(context)
        binding.rvNotes.adapter = notesAdapter

        binding.btnAddNote.setOnClickListener { startNoteDetailActivity() }

        noteViewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            notes?.let { notesAdapter.submitList(it) }
        }

        return root
    }

    override fun onStart() {
        super.onStart()
         requireActivity().actionBar?.title = "Быстрые заметки"
    }

    private fun startNoteDetailActivity(noteId: Long = 0, noteInfo: String = "", isEdit: Boolean = false) {
        val intent = Intent(context, NoteDetailActivity::class.java)
        if (isEdit) {
            intent.putExtra(NOTE_ID, noteId)
            intent.putExtra(NOTE_INFO, noteInfo)
        }
        intent.putExtra(NOTE_EDIT, isEdit)
        if (isEdit) {
            startActivityForResult(intent, EDIT_NOTE_ACTIVITY_REQUEST_CODE)
        } else {
            startActivityForResult(intent, ADD_NOTE_ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ADD_NOTE_ACTIVITY_REQUEST_CODE -> {
                if (resultCode == RESULT_OK && data != null) {
                    val info = data.getStringExtra(NOTE_INFO)
                    info?.let {
                        val note = NoteEntity(it)
                        noteViewModel.insert(note)
                    }
                }
            }
            EDIT_NOTE_ACTIVITY_REQUEST_CODE -> {
                when (resultCode) {
                    RESULT_EDIT -> {
                        if (data != null) {
                            val curId = data.getLongExtra(NOTE_ID, -1L)
                            val newInfo = data.getStringExtra(NOTE_INFO)
                            if (curId > 0L && !newInfo.isNullOrEmpty()) {
                                val newNote = NoteEntity(newInfo)
                                newNote.id = curId
                                noteViewModel.update(newNote)
                            }
                        }
                    }
                    RESULT_DELETE -> {
                        if (data != null) {
                            val curId = data.getLongExtra(NOTE_ID, -1L)
                            if (curId > 0L) {
                                noteViewModel.delete(curId)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onNoteClick(position: Int) {
        val clickedNote = noteViewModel.allNotes.value?.get(position)
        if (clickedNote != null) {
            startNoteDetailActivity(clickedNote.id, clickedNote.info, isEdit = true)
        }
    }

    companion object {
        const val ADD_NOTE_ACTIVITY_REQUEST_CODE = 1
        const val EDIT_NOTE_ACTIVITY_REQUEST_CODE = 2

        const val NOTE_ID = "noteId"
        const val NOTE_INFO = "noteInfo"
        const val NOTE_EDIT = "noteEdit"

        const val RESULT_DELETE = 10
        const val RESULT_EDIT = 11
    }
}
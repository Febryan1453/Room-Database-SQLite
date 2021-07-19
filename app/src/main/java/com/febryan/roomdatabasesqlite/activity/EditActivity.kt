package com.febryan.roomdatabasesqlite.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.febryan.roomdatabasesqlite.databinding.ActivityEditBinding
import com.febryan.roomdatabasesqlite.room.Constant
import com.febryan.roomdatabasesqlite.room.Note
import com.febryan.roomdatabasesqlite.room.NoteDB
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditBinding
    private val db by lazy { NoteDB(this) }
    var noteId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpListener()

        setupView()
    }

    private fun setupView() {

       supportActionBar!!.setDisplayShowHomeEnabled(true)

        val intentType = intent.getIntExtra("type",0)
        when(intentType){
            Constant.TYPE_CREATE -> {
                binding.buttonUpdate.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                binding.buttonSave.visibility = View.GONE
                binding.buttonUpdate.visibility = View.GONE
                getNote()
            }
            Constant.TYPE_UPDATE -> {
                binding.buttonSave.visibility = View.GONE
                getNote()
            }
        }
    }

    private fun setUpListener() {

        binding.buttonSave.setOnClickListener {

            val title = binding.editTitle.text.toString()
            val note = binding.editNote.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                db.noteDao().addNote(
                    Note(0, title, note)
                )
                finish()
            }
        }

        binding.buttonUpdate.setOnClickListener {

            val title = binding.editTitle.text.toString()
            val note = binding.editNote.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                db.noteDao().updateNote(
                    Note(noteId, title, note)
                )
                finish()
            }
        }
    }

    fun getNote(){
        noteId = intent.getIntExtra("ID",0)
//        Toast.makeText(applicationContext, noteId.toString(), Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.noteDao().getNote(noteId).get(0)
            edit_title.setText(notes.title)
            edit_note.setText(notes.note)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
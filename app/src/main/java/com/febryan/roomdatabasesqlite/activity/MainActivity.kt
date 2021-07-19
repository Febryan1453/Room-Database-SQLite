package com.febryan.roomdatabasesqlite.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.febryan.roomdatabasesqlite.R
import com.febryan.roomdatabasesqlite.databinding.ActivityMainBinding
import com.febryan.roomdatabasesqlite.room.Constant
import com.febryan.roomdatabasesqlite.room.Note
import com.febryan.roomdatabasesqlite.room.NoteDB
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val db by lazy { NoteDB(this) }
    lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListener()
        setupRecyclerView()
//        intentEdit()

    }

    private fun intentEdit(noteId: Int, intentType: Int) {
        val i = Intent(applicationContext, EditActivity::class.java)
        i.putExtra("ID", noteId)
        i.putExtra("type", intentType)
        startActivity(i)
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter(arrayListOf(), object : NoteAdapter.onAdapterListener{

            override fun onClick(note: Note) {
//                Toast.makeText(applicationContext, note.title, Toast.LENGTH_SHORT).show()
//                val i = Intent(applicationContext, EditActivity::class.java)
//                i.putExtra("ID", note.id)
//                startActivity(i)
                intentEdit(note.id, Constant.TYPE_READ)
            }

            override fun onUpdate(note: Note) {
                intentEdit(note.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(note: Note) {
                    deleteAlert(note)
            }

        })
        list_note.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = noteAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        loadNote()
    }

    fun loadNote(){
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.noteDao().getNotes()
            Log.d("MainActivity","Response: $notes")
            withContext(Dispatchers.Main){
                noteAdapter.setData(notes)
            }
        }
    }

    private fun setupListener() {
        binding.buttonCreate.setOnClickListener {
//            startActivity(Intent(this, EditActivity::class.java))
            intentEdit(0, Constant.TYPE_CREATE)
        }

    }

    private fun deleteAlert(note: Note){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin hapus ${note.title} ?")
            setNegativeButton("Batal") { dialogInterface,i->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface,i->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.noteDao().deleteNote(note)
                    loadNote()
                }
            }
        }
        alertDialog.show()
    }
}
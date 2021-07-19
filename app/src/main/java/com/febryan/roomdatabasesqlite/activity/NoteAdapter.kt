package com.febryan.roomdatabasesqlite.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.febryan.roomdatabasesqlite.R
import com.febryan.roomdatabasesqlite.room.Note
import kotlinx.android.synthetic.main.adapter_note.view.*

class NoteAdapter (var notes: ArrayList<Note>, private val listener: onAdapterListener) :  RecyclerView.Adapter<NoteAdapter.MyViewHolder>(){

    class MyViewHolder (val view: View) : RecyclerView.ViewHolder(view){
//        val title = view.findViewById<TextView>(R.id.text_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_note,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = notes[position]
        holder.view.text_title.text = note.title

        holder.view.text_title.setOnClickListener {
            listener.onClick(note)
        }

        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(note)
        }

        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(note)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun setData(list: List<Note>){
        notes.clear()
        notes.addAll(list)
        notifyDataSetChanged()
    }

    interface onAdapterListener{
        fun onClick(note: Note)
        fun onUpdate(note: Note)
        fun onDelete(note: Note)
    }

}
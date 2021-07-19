package com.febryan.roomdatabasesqlite.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.febryan.roomdatabasesqlite.R
import com.febryan.roomdatabasesqlite.databinding.ActivityLoadImageBinding
import com.squareup.picasso.Picasso

class LoadImageActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoadImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val urlImg = binding.imageView3
        binding.loadImages.setOnClickListener {
            Picasso.get()
                .load("https://s.id/CRMJ8")
                .resize(600,200)
                .into(urlImg)
        }
    }
}
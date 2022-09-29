package com.example.potterguide.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.potterguide.databinding.ActivityCasasBinding

class CasasActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCasasBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
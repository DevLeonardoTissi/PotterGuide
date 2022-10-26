package com.example.potterguide.ui.activity

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.potterguide.databinding.ActivitySplashScreenBinding
import com.example.potterguide.extensions.vaiPara

class SplashScreenActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

         Handler().postDelayed({
                vaiPara(MainActivity::class.java)
                finish();
        }, 3000);
    }

}
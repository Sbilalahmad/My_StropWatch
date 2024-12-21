package com.syed.mystropwatch

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.syed.mystropwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
//    Here binding varieable is declared
    private lateinit var binding: ActivityMainBinding

    private var isRunning =false

    private var timeSecond = 0

    private var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.startbtn.setOnClickListener{
            Starttimer()
        }
        binding.resetbtn.setOnClickListener {
            Resettimer()
        }
        binding.stopbtn.setOnClickListener {
            Stoptiimer()
        }
    }

    private var runnable =object : Runnable{
        @SuppressLint("DefaultLocale")
        override fun  run(){
            timeSecond++
            val hours = timeSecond/3600
            val minutes = (timeSecond%3600)/60
            val seconds = timeSecond%60

            val time = String.format("%02d:%02d:%02d",hours,minutes,seconds)

            binding.textView.text=time

            handler.postDelayed(this,1000)
        }
    }

    private fun Starttimer(){
        if(!isRunning){
            handler.postDelayed(runnable,1000)
            isRunning = true

            binding.startbtn.isEnabled =false
            binding.resetbtn.isEnabled =true
            binding.stopbtn.isEnabled =true
        }
    }
    @SuppressLint("SetTextI18n")
    private fun  Stoptiimer(){
        if (isRunning){
            handler.removeCallbacks(runnable)
            isRunning =false

            binding.startbtn.isEnabled=true
            binding.startbtn.text="resume"
            binding.resetbtn.isEnabled=true
            binding.stopbtn.isEnabled=false
        }
    }
    @SuppressLint("SetTextI18n")
    private fun Resettimer(){
        Stoptiimer()

        timeSecond = 0
        binding.textView.text="00:00:00"

        binding.startbtn.isEnabled=true
        binding.startbtn.text="Start"
        binding.resetbtn.isEnabled=false
    }
}
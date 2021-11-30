package com.example.simondice

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import com.example.simondice.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.thread

class MainActivity : Activity() {

    var _road: IntArray = intArrayOf()
    var _puntaje: Int = 0
    var _round: Int = 0
    lateinit var textView: TextView
    lateinit var btnStart: Button
    lateinit var btnAzul: Button
    lateinit var btnRojo: Button
    lateinit var btnAmarillo: Button
    lateinit var btnVerde: Button
    var _position: Int = 0


    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        textView = findViewById(R.id.txtScore)
        btnStart = findViewById(R.id.btnStart)
        btnAzul = findViewById(R.id.btnAzul)
        btnRojo = findViewById(R.id.btnRojo)
        btnAmarillo = findViewById(R.id.btnAmarillo)
        btnVerde = findViewById(R.id.btnVerde)


        btnStart.setOnClickListener {
            textView.setText("Score: $_puntaje").toString()
            start()
        }
        btnAzul.setOnClickListener { playColor(1) }
        btnRojo.setOnClickListener { playColor(2) }
        btnAmarillo.setOnClickListener { playColor(3) }
        btnVerde.setOnClickListener { playColor(4) }


    }

    fun start(): Unit {
        _road += getRandom()
        var _aux: Int = 0
        _position = 0

        println()
        val timer = object : CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (_aux < _road.size) {
                    var step = _road[_aux];
                    println(step)
                    activateButton(step)
                    _aux++
                }
            }

            override fun onFinish() {

            }
        }.start()
    }

    fun activateButton(step: Int): Unit {
        val handler = Handler()
        when (step) {
            1 -> {
                handler.postDelayed({
                    btnAzul.setBackgroundColor(Color.parseColor("#4D03A9F4"))
                }, 500)
                btnAzul.setBackgroundColor(Color.BLUE)
            }
            2 -> {
                handler.postDelayed({
                    btnRojo.setBackgroundColor(Color.parseColor("#4DA6170C"))
                }, 500)
                btnRojo.setBackgroundColor(Color.RED)
            }
            3 -> {
                handler.postDelayed({
                    btnAmarillo.setBackgroundColor(Color.parseColor("#4DAC9B0B"))
                }, 500)
                btnAmarillo.setBackgroundColor(Color.YELLOW)
            }
            4 -> {
                handler.postDelayed({
                    btnVerde.setBackgroundColor(Color.parseColor("#4D4CAF50"))
                }, 500)
                btnVerde.setBackgroundColor(Color.GREEN)
            }
        }
    }

    fun getRandom(): Int {
        return (1..4).random()
    }

    fun reset(): Unit {
        _position = 0
        _puntaje = 0
        _road = intArrayOf()
    }

    fun playColor(index: Int): Unit {
        if (_position <= _road.size) {
            activateButton(index)
            if (index == _road[_position]) {
                println("si")
                _position++
                if (_position == _road.size) {
                    _puntaje++
                    textView.setText("Score: $_puntaje").toString()
                    Handler().postDelayed({
                        start()
                    }, 2000)
                }
            } else {
                println("no")

                textView.setText("Perdiste!!!!").toString()
                reset()
            }
        }

    }
}

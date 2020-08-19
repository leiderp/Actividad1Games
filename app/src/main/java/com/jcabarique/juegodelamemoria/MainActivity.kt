package com.jcabarique.juegodelamemoria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Timer
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    var puntos: Int = 0
    var intentos: Int = 1
    var anterior: String = ""
    var anteriorView: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun onClickMostrar(view: View) {
        val puntaje: TextView = findViewById(R.id.puntaje)
        val tagView = view.getTag().toString().split("-")

        val tag = tagView[0]
        if(tag == anteriorView) {
            intentos = 1;
        }
        
        if (intentos % 2 == 0){
            if(tagView[1] == anterior) {
                mostrar(tag)
                puntos += 100
            }else {
                mostrar(tag)
                Handler().postDelayed({
                    ocultar(tag)
                    ocultar(anteriorView)
                }, 500)

            }
        }else {
            mostrar(tag)
            anterior = tagView[1]
            anteriorView = tagView[0]
        }

        puntaje.text = "Puntos: " + puntos.toString()
        intentos += 1
    }

    fun mostrar (tag: String) {
        when (tag){
            "1_1" -> {iv_11.setImageResource(R.drawable.i_1);}
            "1_2" -> {iv_12.setImageResource(R.drawable.i_3);}
            "1_3" -> {iv_13.setImageResource(R.drawable.i_4);}
            "1_4" -> {iv_14.setImageResource(R.drawable.i_2);}
            "2_1" -> {iv_21.setImageResource(R.drawable.i_6);}
            "2_2" -> {iv_22.setImageResource(R.drawable.i_5);}
            "2_3" -> {iv_23.setImageResource(R.drawable.i_6);}
            "2_4" -> {iv_24.setImageResource(R.drawable.i_3);}
            "3_1" -> {iv_31.setImageResource(R.drawable.i_2);}
            "3_2" -> {iv_32.setImageResource(R.drawable.i_1);}
            "3_3" -> {iv_33.setImageResource(R.drawable.i_4);}
            "3_4" -> {iv_34.setImageResource(R.drawable.i_5);}
        }
    }

    fun ocultar (tag: String) {
        when (tag){
            "1_1" -> {iv_11.setImageResource(R.drawable.signo1);}
            "1_2" -> {iv_12.setImageResource(R.drawable.signo1);}
            "1_3" -> {iv_13.setImageResource(R.drawable.signo1);}
            "1_4" -> {iv_14.setImageResource(R.drawable.signo1);}
            "2_1" -> {iv_21.setImageResource(R.drawable.signo1);}
            "2_2" -> {iv_22.setImageResource(R.drawable.signo1);}
            "2_3" -> {iv_23.setImageResource(R.drawable.signo1);}
            "2_4" -> {iv_24.setImageResource(R.drawable.signo1);}
            "3_1" -> {iv_31.setImageResource(R.drawable.signo1);}
            "3_2" -> {iv_32.setImageResource(R.drawable.signo1);}
            "3_3" -> {iv_33.setImageResource(R.drawable.signo1);}
            "3_4" -> {iv_34.setImageResource(R.drawable.signo1);}
        }
    }

    fun onClickReiniciar(view: View) {
        puntos = 0
        val puntaje: TextView = findViewById(R.id.puntaje)
        puntaje.text = "Puntos: " + puntos.toString()
        intentos = 1
        iv_11.setImageResource(R.drawable.signo1)
        iv_12.setImageResource(R.drawable.signo1)
        iv_13.setImageResource(R.drawable.signo1)
        iv_14.setImageResource(R.drawable.signo1)
        iv_21.setImageResource(R.drawable.signo1)
        iv_22.setImageResource(R.drawable.signo1)
        iv_23.setImageResource(R.drawable.signo1)
        iv_24.setImageResource(R.drawable.signo1)
        iv_31.setImageResource(R.drawable.signo1)
        iv_32.setImageResource(R.drawable.signo1)
        iv_33.setImageResource(R.drawable.signo1)
        iv_34.setImageResource(R.drawable.signo1)
    }
}
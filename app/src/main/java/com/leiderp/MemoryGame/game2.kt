package com.leiderp.MemoryGame

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game2.*
import kotlinx.android.synthetic.main.activity_main.*
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size



class game2 : AppCompatActivity(), View.OnClickListener {
    //Timer variables
    var START_MILLI_SECONDS = 60000L
    lateinit var countdown_timer: CountDownTimer
    var isRunning: Boolean = false;
    var time_in_milli_seconds = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game2)
        initLevel(false)
    }

    var score = 0
    var intentos = 0
    var time = 500L
    var CLevel = 1
    var numSuccess = 0
    var succes = mutableListOf<Int>()

    fun showSuccess(v: View){
        if (start.text.toString() =="START") {
            val btn: Button = findViewById(R.id.start)
            btn.setBackgroundColor(getResources().getColor(R.color.colorGrey))
            //btn.isEnabled = false
            clickable(true)
            generarCuadrosAleatorios()

            for (i in (0..succes.size - 1)) {
                val id = getResources().getIdentifier("i" + succes.get(i), "id", getPackageName())
                val iV = findViewById(id) as ImageView
                iV.setImageResource(R.drawable.i_1)
            }
            Handler().postDelayed({
                ocultAllSucces()
            }, time)

            val time = 1
            time_in_milli_seconds = time.toLong() * 60000L
            startTimer(time_in_milli_seconds)
        }else if (start.text == "PAUSE"){
            pauseTimer()
        }else if (start.text == "REANUDAR"){
            startTimer(time_in_milli_seconds)
        }
    }

    fun  generarCuadrosAleatorios(){
        var aleatorio = 0
        for (i in (0..CLevel)){
            if (i==0) {
                aleatorio = (1..25).random()
                succes.add(i,aleatorio)
            }else {
                var n=0
                var sw = true
                while (sw) {
                    if (succes.get(n)!=aleatorio) {
                        n+=1
                    }else{
                        aleatorio = (1..25).random()
                        n=0
                    }
                    if(n==i){
                        sw = false
                        succes.add(i,aleatorio)
                    }
                }
            }
        }

    }

    fun ocultAllSucces(){
        for (i in (0..succes.size-1)){
            val id = getResources().getIdentifier("i"+succes.get(i), "id", getPackageName())
            val iV = findViewById(id) as ImageView
            iV.setImageResource(R.drawable.signo1)
        }
    }

    fun initLevel(click: Boolean){
        start.text = "START"
        resetTimer()
        for (i in (1..25)){
            val id = getResources().getIdentifier("i"+i, "id", getPackageName())
            val iV = findViewById(id) as ImageView
            iV.setImageResource(R.drawable.signo1)
            iV.isClickable = click
            val btn:Button = findViewById(R.id.start)
            btn.isEnabled = true
            btn.setBackgroundColor( getResources().getColor(R.color.colorGreen) )
            btn.setTextColor(getResources().getColor(R.color.colorBlack))
        }

    }

    fun onClickInit(v: View){
        initLevel(false)
        countdown_timer.cancel()
        resetTimer()
        start.text = "START"
        score = 0
        CLevel = 1
        numSuccess = 0
        succes.clear()
        var scor: TextView = findViewById(R.id.score)
        scor.text = "Score: " + score.toString()
        val lev: TextView = findViewById(R.id.level)
        lev.text = "Level: " + CLevel.toString()
    }

    fun clickable(click: Boolean){
        for (i in (1..25)){
            val id = getResources().getIdentifier("i"+i, "id", getPackageName())
            val iV = findViewById(id) as ImageView
            iV.isClickable = click
        }
    }

    fun winner(all: Boolean){
        for (i in (1..25)){
            val id = getResources().getIdentifier("i"+i, "id", getPackageName())
            val iV = findViewById(id) as ImageView
            iV.setImageResource(R.drawable.youwin)
            iV.isClickable = all
        }
        loadConfeti()
    }

    fun lose(all: Boolean){
        Toast.makeText(this,"Oooh!!!, PERDISTE!!, VUELVE A INTENTARLO",Toast.LENGTH_LONG).show()
        for (i in (1..25)){
            val id = getResources().getIdentifier("i"+i, "id", getPackageName())
            val iV = findViewById(id) as ImageView
            iV.setImageResource(R.drawable.lose1)
            iV.isClickable = all
        }
    }

    override fun onClick(v: View) {
        var tagView = v.getTag().toString()
        var iv : ImageView = v.findViewWithTag(tagView) as ImageView
        var sw = true
        for (i in (0..CLevel)){
            if(tagView.toInt()==succes.get(i)){
                iv.setImageResource(R.drawable.i_1)
                numSuccess += 1
                if (CLevel==1){
                    score+=(50-intentos)
                }
                sw = false
                var scor: TextView = findViewById(R.id.score)
                scor.text = "Score: " + score.toString()
            }
        }
        if(sw==true){
            iv.setImageResource(R.drawable.no)
            Handler().postDelayed({
                iv.setImageResource(R.drawable.signo1)
                ocultAllSucces()
                numSuccess = 0
                intentos += 1
                if (CLevel>1){
                    score = score-intentos
                }else{
                    score = 0
                }
                var scor: TextView = findViewById(R.id.score)
                scor.text = "Score: " + score.toString()
            }, time-250L)

        }else{
            sw = true
        }

        if (numSuccess==succes.size){
            numSuccess = 0
            intentos = 0
            if (CLevel>1){
                score+=100
                var scor: TextView = findViewById(R.id.score)
                scor.text = "Score: " + score.toString()
            }
            CLevel += 1
            succes.clear()
            Handler().postDelayed({
                winner(false)
            },500)
            Toast.makeText(this,"Felicitaciones!! Nivel ${CLevel-1} superado, Pasas al Nivel: $CLevel",Toast.LENGTH_LONG).show()
            Handler().postDelayed({
                if (CLevel<24) {
                    countdown_timer.cancel()
                    initLevel(false)
                    val lev: TextView = findViewById(R.id.level)
                    lev.text = "Level: " + CLevel.toString()
                }else{
                    Toast.makeText(this,"Felicitaciones!!!, Has Superado Todos Los Niveles",Toast.LENGTH_LONG).show()
                    val lev: TextView = findViewById(R.id.level)
                    lev.text = "FELICITACIONES!!"
                    winner(false)
                }
            }, 2000)

        }

    }

    //Timer Methods
    private fun pauseTimer() {

        start.text = "REANUDAR"
        countdown_timer.cancel()
        isRunning = false
    }

    private fun startTimer(time_in_seconds: Long) {
        countdown_timer = object : CountDownTimer(time_in_seconds, 1000) {
            override fun onFinish() {
               // loadConfeti() here other code
                lose(false)
            }

            override fun onTick(p0: Long) {
                time_in_milli_seconds = p0
                updateTextUI()
            }
        }
        countdown_timer.start()

        isRunning = true
        start.setText("PAUSE")

    }

    private fun resetTimer() {
        if (start.text!="START"){
            countdown_timer.cancel()
        }
        time_in_milli_seconds = 1 * START_MILLI_SECONDS
        updateTextUI()
    }

    private fun updateTextUI() {
        val minute = (time_in_milli_seconds / 1000) / 60
        val seconds = (time_in_milli_seconds / 1000) % 60

        timer.text = "$minute:$seconds"
    }


    private fun loadConfeti() {
       viewKonfetti.build()
            .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(1000L)
            .addShapes(Shape.RECT, Shape.CIRCLE)
            .addSizes(Size(12))
            .setPosition(-50f, viewKonfetti.width + 50f, -50f, -50f)
            .streamFor(300, 2000L)
    }

}
package com.example.mytrainingsession

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Exercise : AppCompatActivity() {
    var exercises = mutableListOf<ThisExercise>()

    private lateinit var titleTV: TextView
    private lateinit var exercisesTV: TextView
    private lateinit var descriptionTV: TextView
    private lateinit var startButtonBTN: Button
    private lateinit var completedButtonBTN: Button
    private lateinit var timerTV: TextView
    private lateinit var imageViewIV: ImageView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private var exerciseIndex  = 0
    private lateinit var currentExercise : ThisExercise
    private lateinit var timer: CountDownTimer

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_exercise)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = ""

        titleTV = findViewById(R.id.titleTV)
        exercisesTV = findViewById(R.id.exerciseTV)
        descriptionTV = findViewById(R.id.descriptionTV)
        startButtonBTN = findViewById(R.id.startButtonBTN)
        completedButtonBTN = findViewById(R.id.completedButtonBTN)
        timerTV = findViewById(R.id.timerTV)
        imageViewIV = findViewById(R.id.imageViewIV)

        val id = intent.getIntExtra("id",1)

        exercises = when(id){
            0 -> ExerciseDataBase.exerciseStrength
            1 -> ExerciseDataBase.exerciseAgility
            2 -> ExerciseDataBase.exerciseRapidity
            3 -> ExerciseDataBase.exerciseEndurance
            else -> ExerciseDataBase.exerciseStrength
        }

        startButtonBTN.setOnClickListener{
            startWorkout()
        }

        completedButtonBTN.setOnClickListener{
            completedExercise()
        }
    }

    private fun completedExercise() {
        timer.cancel()
        completedButtonBTN.isEnabled = false
        startNextExercise()
    }

    private fun startWorkout() {
        exerciseIndex = 0
        titleTV.text = "Начало тренировки"
        startButtonBTN.isEnabled = false
        startButtonBTN.text = "Процесс тренеровки"
        startNextExercise()
    }

    private fun startNextExercise() {
        if (exerciseIndex < exercises.size){
            currentExercise = exercises[exerciseIndex]
            exercisesTV.text = currentExercise.name
            descriptionTV.text = currentExercise.description
            imageViewIV.setImageResource(currentExercise.gifImage)
            timerTV.text = formatTime(currentExercise.durationInSection)
            imageViewIV.visibility = View.VISIBLE
            timer = object : CountDownTimer(currentExercise.durationInSection * 1000L,1000){

                override fun onTick(millisUntilFinished: Long) {
                    timerTV.text = formatTime((millisUntilFinished / 1000).toInt())
                }

                override fun onFinish() {
                    timerTV.text = "Упражнение завершено"
                    imageViewIV.visibility = View.GONE
                    completedButtonBTN.isEnabled = true
                }
            }.start()
            exerciseIndex++
        } else {
            exercisesTV.text = "Тренеровка запвершена"
            descriptionTV.text = ""
            titleTV.text = ""
            completedButtonBTN.isEnabled = false
            startButtonBTN.isEnabled = true
            startButtonBTN.text = "Начать снова"
        }
    }

    private fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.context_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.exitMenuMain ->{
                finishAffinity()
                Toast.makeText(this,"Приложение завершено", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
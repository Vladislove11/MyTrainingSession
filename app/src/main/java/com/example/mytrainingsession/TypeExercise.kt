package com.example.mytrainingsession

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TypeExercise : AppCompatActivity() {

    private lateinit var listTypeExerciseLV: ListView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_type_exercise)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = ""

        val list = listOf("Сила", "Ловкость", "Выносливость", "Быстрота")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

        listTypeExerciseLV = findViewById(R.id.listTypeExerciseLV)
        listTypeExerciseLV.adapter = adapter

        listTypeExerciseLV.onItemClickListener =
            AdapterView.OnItemClickListener { parent, v, position, id ->
                val intent = Intent(this, Exercise::class.java)
                intent.putExtra("id", id.toInt())
                startActivity(intent)
            }


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
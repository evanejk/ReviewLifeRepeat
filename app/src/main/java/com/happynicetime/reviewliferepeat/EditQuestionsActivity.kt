package com.happynicetime.reviewliferepeat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class EditQuestionsActivity : AppCompatActivity() {
    private lateinit var buttonSave: Button
    private lateinit var editText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_questions)
        buttonSave = findViewById(R.id.buttonSave)
        editText = findViewById(R.id.editTextTextMultiLine)
        editText.setText(readFileOnInternalStorage(applicationContext,"questions.txt"))
        buttonSave.setOnClickListener{
            //println("time to save questions")
            val filePathMadeAsString = writeFileOnInternalStorage(applicationContext,"questions.txt",editText.getText().toString())
            val toast = Toast.makeText(this, filePathMadeAsString, Toast.LENGTH_SHORT)
            toast.show()
            finish()
        }
    }
}
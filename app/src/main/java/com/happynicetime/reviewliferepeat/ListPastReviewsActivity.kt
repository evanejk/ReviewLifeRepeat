package com.happynicetime.reviewliferepeat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.io.File
import java.util.Arrays

class ListPastReviewsActivity : AppCompatActivity() {

    private lateinit var textViewFileName: TextView
    private lateinit var editTextFileText: EditText
    private lateinit var buttonPreviousFile: Button
    private lateinit var buttonNextFile: Button
    private lateinit var buttonSaveFile: Button
    private lateinit var buttonDeleteFile: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_past_reviews)

        var dir: File = File(applicationContext.getFilesDir(), "ReviewLifeRepeat/reviews/")
        if(!dir.exists()){
            val toast = Toast.makeText(this, "No Reviews To Show", Toast.LENGTH_LONG)
            toast.show()
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        var files = dir.listFiles()
        if (files.isEmpty()) {
            val toast = Toast.makeText(this, "No Reviews To Show", Toast.LENGTH_LONG)
            toast.show()
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        Arrays.sort(files)
        files = files.reversedArray()
        //for(item in files){
        //    println(item.toString())//works
        //}
        textViewFileName = findViewById(R.id.textViewFileName)
        var file = files[0]
        textViewFileName.setText(file.toString())
        var fileString = readFileOnInternalStorage(file)
        editTextFileText = findViewById(R.id.editTextFileText)
        editTextFileText.setText(fileString)
        buttonPreviousFile = findViewById(R.id.buttonPreviousFile)
        var currentFileIndex = 0;
        buttonDeleteFile = findViewById(R.id.buttonDeleteFile)
        buttonPreviousFile.setOnClickListener {
            currentFileIndex++
            if (currentFileIndex < files.size) {
                file = files[currentFileIndex]
                fileString = readFileOnInternalStorage(file)
                editTextFileText.setText(fileString)
                textViewFileName.setText(file.toString())
                buttonDeleteFile.setText("Delete")
            } else {
                currentFileIndex--;
            }
        }
        buttonNextFile = findViewById(R.id.buttonNextFile)
        buttonNextFile.setOnClickListener {
            currentFileIndex--;
            if (currentFileIndex >= 0) {
                file = files[currentFileIndex]
                fileString = readFileOnInternalStorage(file)
                editTextFileText.setText(fileString)
                textViewFileName.setText(file.toString())
                buttonDeleteFile.setText("Delete")
            } else {
                currentFileIndex++;
            }
        }
        buttonSaveFile = findViewById(R.id.buttonSaveFile)
        buttonSaveFile.setOnClickListener {
            file = files[currentFileIndex]
            var filePathMadeAsString =
                writeFileOnInternalStorage(file, editTextFileText.getText().toString())
            val toast = Toast.makeText(this, filePathMadeAsString, Toast.LENGTH_SHORT)
            toast.show()
        }
        buttonDeleteFile.setOnClickListener {
            if (buttonDeleteFile.getText().toString() == "Delete") {
                buttonDeleteFile.setText("Delete Confirm")
            } else {
                file = files[currentFileIndex]
                file.delete()

                dir = File(applicationContext.getFilesDir(), "ReviewLifeRepeat/reviews/")
                files = dir.listFiles()
                Arrays.sort(files)
                files = files.reversedArray()

                if (currentFileIndex >= files.size) {
                    currentFileIndex--;
                }

                if (files.size > 0) {
                    file = files[currentFileIndex]
                    fileString = readFileOnInternalStorage(file)
                    editTextFileText.setText(fileString)
                    textViewFileName.setText(file.toString())
                } else {
                    val toast =
                        Toast.makeText(this, "No Reviews To Show", Toast.LENGTH_LONG)
                    toast.show()
                    val intent: Intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }

                buttonDeleteFile.setText("Delete")
            }
        }
    }
}

package com.happynicetime.reviewliferepeat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date

class NewReviewActivity : AppCompatActivity() {
    private lateinit var textViewQuestion: TextView
    private lateinit var buttonNext: Button
    private lateinit var editTextResponse: EditText
    private lateinit var buttonPrevious: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_review)
        //load questions
        var questionsString: String = readFileOnInternalStorage(applicationContext,"questions.txt")
        textViewQuestion = findViewById(R.id.textViewQuestion)
        val questionsArray = questionsString.split("\n")
        if(questionsArray.isNotEmpty()){
            textViewQuestion.setText(questionsArray[0])
            buttonNext = findViewById(R.id.buttonNext)
            editTextResponse = findViewById(R.id.editTextResponse)
            var responsesArray: Array<String?> = arrayOfNulls(questionsArray.size)
            var currentQuestionIndex: Int = 0;
            buttonPrevious = findViewById(R.id.buttonPrevious)
            buttonNext.setOnClickListener{
                if(currentQuestionIndex == questionsArray.size - 1){//time to save
                    responsesArray[currentQuestionIndex] = editTextResponse.getText().toString()
                    //println("Stuff to save:")
                    var saveStringBuilder: StringBuilder = StringBuilder()
                    for(i in 0..responsesArray.size - 1){
                        saveStringBuilder.append(questionsArray[i])
                        saveStringBuilder.append("\n")
                        saveStringBuilder.append(responsesArray[i])
                        saveStringBuilder.append("\n\n")
                    }
                    //println(saveStringBuilder.toString())
                    val fileSuffix: String = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
                    //println(fileSuffix)
                    val filePathMadeAsString = writeFileOnInternalStorage(applicationContext,"reviews/" + fileSuffix + ".txt",saveStringBuilder.toString())
                    val toast = Toast.makeText(this, filePathMadeAsString, Toast.LENGTH_SHORT)
                    toast.show()

                    currentQuestionIndex = 0
                    //var intent: Intent = Intent(this, MainActivity::class.java)
                    //startActivity(intent)
                    finish()
                }else{
                    //save the response in a variable (array)
                    responsesArray[currentQuestionIndex] = editTextResponse.getText().toString()
                    //go to next question
                    currentQuestionIndex++
                    if (currentQuestionIndex < questionsArray.size - 1) {//2nd to last one and before
                        //switch question
                        textViewQuestion.setText(questionsArray[currentQuestionIndex])
                        if (responsesArray[currentQuestionIndex] != null) {
                            editTextResponse.setText(responsesArray[currentQuestionIndex])
                        } else {
                            editTextResponse.setText("")
                        }
                    } else if (currentQuestionIndex == questionsArray.size - 1) {//going to last one
                        textViewQuestion.setText(questionsArray[currentQuestionIndex])
                        if (responsesArray[currentQuestionIndex] != null) {
                            editTextResponse.setText(responsesArray[currentQuestionIndex])
                        } else {
                            editTextResponse.setText("")
                        }
                        //change button text to save
                        buttonNext.setText("Save")
                    }
                }
            }
            buttonPrevious.setOnClickListener{
                if(currentQuestionIndex > 0){
                    //save response in variable
                    responsesArray[currentQuestionIndex] = editTextResponse.getText().toString()
                    //switch to response before
                    currentQuestionIndex--
                    textViewQuestion.setText(questionsArray[currentQuestionIndex])
                    editTextResponse.setText(responsesArray[currentQuestionIndex])
                }
            }
        }
    }
}
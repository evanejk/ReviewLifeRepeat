package com.happynicetime.reviewliferepeat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.OnBackPressedCallback

class MainActivity : AppCompatActivity() {
    private lateinit var buttonEditQuestions: Button
    private lateinit var buttonNewReview: Button
    private lateinit var buttonListPastReviews: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonEditQuestions = findViewById(R.id.buttonEditQuestions)
        buttonEditQuestions.setOnClickListener{
            //println(editText.getText())
            val intent: Intent = Intent(this, EditQuestionsActivity::class.java)
            startActivity(intent)
        }
        buttonNewReview = findViewById(R.id.buttonNewReview)
        buttonNewReview.setOnClickListener{
            val intent: Intent = Intent(this,NewReviewActivity::class.java)
            startActivity(intent)
        }
        buttonListPastReviews = findViewById(R.id.buttonPastReviews)
        buttonListPastReviews.setOnClickListener{
            val intent: Intent = Intent(this,ListPastReviewsActivity::class.java)
            startActivity(intent)
        }
        //val back = this.onBackPressedDispatcher
        //back.addCallback(this, object : OnBackPressedCallback(true){
        //    override fun handleOnBackPressed() {
                //println("back pressed")
                //do nothing instead of glitching into a previous activity
        //    }
        //})

    }
}
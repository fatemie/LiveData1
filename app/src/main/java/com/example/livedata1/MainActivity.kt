package com.example.livedata1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    val vmodel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    @SuppressLint("ResourceAsColor")
    private fun initViews() {
        var textView = findViewById<TextView>(R.id.tvNumber)
        var buttonNext = findViewById<Button>(R.id.button1)
        var buttonBack = findViewById<Button>(R.id.button)
        var buttonAddQuestion= findViewById<Button>(R.id.buttonAddQuestion)
        var progressBar = findViewById<ProgressBar>(R.id.progressBar)
        var questionText = findViewById<TextView>(R.id.tvQuestion)
        var editText = findViewById<TextView>(R.id.editText)
        var scoreTV = findViewById<TextView>(R.id.scoreTV)
        var countTV = findViewById<TextView>(R.id.textViewQuestionCount)
       // scoreTV.setTextColor(R.color.red)
       // scoreTV.setBackgroundColor(R.color.teal_200)
        // scoreTV.setHintTextColor(R.color.teal_200 )


        //progressBar.max = vmodel.questionCountLiveData.value!!

        buttonNext.setOnClickListener {
            vmodel.updateScore(editText.text.toString())
            vmodel.nextClicked()
        }

        buttonBack.setOnClickListener {
            vmodel.updateScore(editText.text.toString())
            vmodel.backClicked()
        }

        buttonAddQuestion.setOnClickListener {
            QuestionRepository.insertQuestion(QuestionRepository.newRandomQuestion())
        }

        val numberObserver = Observer<Int> { number ->
                textView.text = number.toString()
                progressBar.progress = number
        }

        val scoreObserver = Observer<Int> { score ->
            scoreTV.text = score.toString()
        }
        val scoreColorObserver = Observer<Int> { score ->
            when(score){
                R.color.red -> scoreTV.setTextColor(resources.getColor(R.color.red))
                R.color.yellow -> scoreTV.setTextColor(resources.getColor(R.color.yellow))
                else -> scoreTV.setTextColor(resources.getColor(R.color.green))
            }
        }

        val buttonEnabledObserver = Observer<Boolean>{  enabled ->
            buttonNext.isEnabled = enabled
        }

        val buttonBackEnabledObserver = Observer<Boolean>{  enabled ->
            buttonBack.isEnabled = enabled
        }

        val questionObserver = Observer<String>{ question ->
            questionText.text = question
        }

        val questionCountObserver = Observer<Int>{ questionCount ->
            progressBar.max = questionCount
            countTV.text = questionCount.toString()

        }

        vmodel.questionLiveData.observe(this , questionObserver)
        vmodel.nextEnabledLiveData.observe(this , buttonEnabledObserver)
        vmodel.backEnabledLiveData.observe(this , buttonBackEnabledObserver)
        vmodel.numberLiveData.observe(this , numberObserver)
        vmodel.scoreLiveData.observe(this , scoreObserver)
        vmodel.scoreColorLiveData.observe(this , scoreColorObserver)
        vmodel.questionCountLiveData.observe(this , questionCountObserver)

    }
}
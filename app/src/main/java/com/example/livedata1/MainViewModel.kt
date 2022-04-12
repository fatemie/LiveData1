package com.example.livedata1

import android.app.Application
import androidx.lifecycle.*

class MainViewModel(app: Application)  : AndroidViewModel(app){

    val numberLiveData = MutableLiveData<Int>(1)
    val scoreLiveData = MutableLiveData<Int>(0)
    val scoreColorLiveData : LiveData<Int> = Transformations.map(scoreLiveData){
        when(it){
            in 0 .. 1 -> R.color.red
            in 2 .. 3 -> R.color.yellow
            else -> R.color.green
        }
    }

    val questionLiveData = MutableLiveData<String>(
        QuestionRepository.getQuestion(1)?.desc)

    //val questionCount = QuestionRepository.questionList.size
    lateinit var questionCountLiveData : LiveData<Int>

    var nextEnabledLiveData = MutableLiveData<Boolean>(true)
    var backEnabledLiveData = MutableLiveData<Boolean>(false)

    init{
        QuestionRepository.initDB(app.applicationContext)
        QuestionRepository.insertQuestion(QuestionRepository.newRandomQuestion())
        questionCountLiveData = QuestionRepository.getQuestionCount()!!
    }

    fun nextClicked() {
        backEnabledLiveData.value = true
        numberLiveData.value = numberLiveData.value?.plus(1)
        numberLiveData.value?.let { number ->
            questionLiveData.value = QuestionRepository.getQuestion(number)?.desc
        }
        if(numberLiveData.value == questionCountLiveData.value) {
            nextEnabledLiveData.value = false
        }
    }

    fun backClicked() {
        nextEnabledLiveData.value = true
        numberLiveData.value = numberLiveData.value?.minus(1)
        numberLiveData.value?.let { number ->
            questionLiveData.value = QuestionRepository.getQuestion(number)?.desc
        }
        if(numberLiveData.value == 1) {
            backEnabledLiveData.value = false
        }
    }

    fun updateScore(answer : String) {
         if(answer.isNotEmpty()) {
             var intAnswer = Integer.parseInt(answer)
             if (intAnswer == numberLiveData.value?.let { QuestionRepository.getQuestion(it)?.answer }) {
                 scoreLiveData.value = scoreLiveData.value?.plus(2)
             } else {
                 scoreLiveData.value = scoreLiveData.value?.minus(1)
             }
         }
//        scoreColor()
    }

//    fun scoreColor() {
//        if(scoreLiveData.value!! > questionCount/2){
//            scoreColorLiveData.value = R.color.green
//        }
//        if(scoreLiveData.value!! < questionCount/2){
//            scoreColorLiveData.value = R.color.red
//        }
//       scoreColorLiveData.value = R.color.yellow
//    }

}

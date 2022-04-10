package com.example.livedata1

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MainViewModel  : ViewModel(){

    val numberLiveData = MutableLiveData<Int>(1)
    val scoreLiveData = MutableLiveData<Int>(0)
    val scoreColorLiveData = MutableLiveData<Int>()

    val questionLiveData = MutableLiveData<String>(
        QuestionRepository.questionList[0]
    )

    val questionCount = QuestionRepository.questionList.size

    var nextEnabledLiveData = MutableLiveData<Boolean>(true)
    var backEnabledLiveData = MutableLiveData<Boolean>(false)

    fun nextClicked() {
        backEnabledLiveData.value = true
        numberLiveData.value = numberLiveData.value?.plus(1)
        numberLiveData.value?.let { number ->
            questionLiveData.value = QuestionRepository.questionList[number - 1]
        }
        if(numberLiveData.value == questionCount) {
            nextEnabledLiveData.value = false
        }
    }

    fun backClicked() {
        nextEnabledLiveData.value = true
        numberLiveData.value = numberLiveData.value?.minus(1)
        numberLiveData.value?.let { number ->
            questionLiveData.value = QuestionRepository.questionList[number - 1]
        }
        if(numberLiveData.value == 1) {
            backEnabledLiveData.value = false
        }
    }

    fun updateScore(answer : String) {
         if(answer.isNotEmpty()) {
             var intAnswer = Integer.parseInt(answer)
             if (intAnswer == QuestionRepository.answerList[numberLiveData.value!!.minus(1)]) {
                 scoreLiveData.value = scoreLiveData.value?.plus(1)
             } else {
                 scoreLiveData.value = scoreLiveData.value?.minus(1)
             }
         }
        scoreColor()
    }

    fun scoreColor() {
        if(scoreLiveData.value!! > questionCount/2){
            scoreColorLiveData.value = R.color.green
        }
        if(scoreLiveData.value!! < questionCount/2){
            scoreColorLiveData.value = R.color.red
        }
       scoreColorLiveData.value = R.color.yellow
    }

}

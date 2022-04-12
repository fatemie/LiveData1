package com.example.livedata1

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.livedata1.database.AppDatabase
import com.example.livedata1.database.QuestionDao
import com.example.livedata1.model.Question
import kotlin.random.Random

object QuestionRepository {
    val questionList = arrayListOf(
        " result of 2 + 2 " ,
        " result of  5 - 1 " ,
        " result of 100 * 21"
    )
    val answerList = arrayListOf(
        4,
        4,
        2100
    )


    var db: AppDatabase?=null
    var dao: QuestionDao?=null

    fun initDB(context: Context){
        db= AppDatabase.getAppDatabase(context)
        dao=db?.questionDao()
    }

    fun insertQuestion( question: Question){
        dao?.insertAll(question)
    }
    fun getQuestion(id:Int): Question?{
        return dao?.getQuestion(id)
    }

    fun getQuestionCount(): LiveData<Int>? {
        return dao?.getQuestionCount()
    }

    fun newRandomQuestion(): Question{
        var num1 = Random.nextInt(20)
        var num2 = Random.nextInt(20)
        var ans = num1 - num2
        var desc = "$num1 - $num2"
        var question = Question(0, desc, ans)
        return question
    }

}
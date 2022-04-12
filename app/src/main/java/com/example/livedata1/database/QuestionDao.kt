package com.example.livedata1.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.livedata1.model.Question

@Dao
interface QuestionDao {
    @Query("SELECT * FROM Question")
    fun getAllQuestions():List<Question>

    @Query("SELECT * FROM Question WHERE id IN (:questionId)")
    fun getQuestion(questionId:Int): Question

    @Insert
    fun insertAll(vararg question: Question)

    @Query("DELETE FROM Question")
    fun deleteAll()

    @Query("SELECT COUNT (*) FROM Question")
    fun getQuestionCount(): LiveData<Int>

}
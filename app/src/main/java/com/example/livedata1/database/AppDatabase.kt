package com.example.livedata1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.livedata1.model.Question

@Database(entities = [Question::class], version = 1 )
abstract class AppDatabase : RoomDatabase(){
    abstract fun questionDao(): QuestionDao

    companion object{
        var instance : AppDatabase?=null
        fun getAppDatabase(context: Context):AppDatabase?{
            if (instance==null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "QuestionDB"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return instance
        }
    }
}
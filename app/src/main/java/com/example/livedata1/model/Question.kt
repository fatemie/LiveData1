package com.example.livedata1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question(@PrimaryKey(autoGenerate=true) val id : Int, val desc : String, val answer : Int)

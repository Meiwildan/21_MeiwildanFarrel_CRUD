package com.example.a21_meiwildanfarrel.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Movie (
    @PrimaryKey(autoGenerate = true)

    val id: Int,
    val title: String,
    val desc: String,
)
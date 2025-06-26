package com.example.recipe

import androidx.room.Dao
import androidx.room.Query
import androidx.room.R

@Dao
interface Dao {
    @Query("SELECT * FROM recipe")
    fun getAll():List<Recipe?>?
}
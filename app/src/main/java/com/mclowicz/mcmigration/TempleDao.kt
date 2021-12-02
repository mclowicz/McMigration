package com.mclowicz.mcmigration

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TempleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTemple(temple: Temple): Long

    @Query("SELECT * FROM temple")
    fun getTemples(): List<Temple>
}
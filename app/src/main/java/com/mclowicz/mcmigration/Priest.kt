package com.mclowicz.mcmigration

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "priest")
data class Priest(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String = "",
    val rank: String = ""
)

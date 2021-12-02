package com.mclowicz.mcmigration

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "temple")
data class Temple(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String = "",
    val city: String = "",
    val street: String = ""
)
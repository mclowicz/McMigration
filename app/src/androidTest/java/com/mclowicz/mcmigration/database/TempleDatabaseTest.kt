package com.mclowicz.mcmigration.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mclowicz.mcmigration.Temple
import com.mclowicz.mcmigration.TempleDatabase
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TempleDatabaseTest {

    private lateinit var database: TempleDatabase

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(
            context,
            TempleDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun tearDown() {
        database.clearAllTables()
        database.close()
    }

    @Test
    fun insertReadStudent() {
        val temple = Temple(
            name = "Saint Matthews",
            city = "London",
            street = "Brixton Hill"
        )
        val newId = database.templeDao().insertTemple(temple)
        val expectedTemple = temple.copy(id = newId)
        val templeList = database.templeDao().getTemples()
        assertEquals(1, templeList.size)
        assertEquals(expectedTemple, templeList.first())
    }
}
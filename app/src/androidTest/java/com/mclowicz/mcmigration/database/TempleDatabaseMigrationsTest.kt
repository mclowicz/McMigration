package com.mclowicz.mcmigration.database

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mclowicz.mcmigration.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.fail
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TempleDatabaseMigrationsTest {
    private lateinit var database: SupportSQLiteDatabase

    @JvmField
    @Rule
    val migrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        TempleDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migrate_1_2() {
        database = migrationTestHelper.createDatabase(TEST_DB, 1)
            .apply {
                execSQL("INSERT INTO temple (id, name, city, street) VALUES (1, 'Saint Matthews', 'London', 'Brixton Hill')")
                close()
            }
        database = migrationTestHelper.runMigrationsAndValidate(
            TEST_DB,
            2,
            true,
            MIGRATION_1_2
        )

        val cursor = database.query("SELECT * FROM temple")
        MatcherAssert.assertThat(cursor, Is(notNullValue()))

        while (cursor.moveToNext()) {
            var index: Int
            index = cursor.getColumnIndexOrThrow("name")
            val name = cursor.getString(index)
            index = cursor.getColumnIndexOrThrow("city")
            val city = cursor.getString(index)
            index = cursor.getColumnIndexOrThrow("street")
            val street = cursor.getString(index)
            index = cursor.getColumnIndexOrThrow("income")
            val income = cursor.getDouble(index)

            assertEquals("Saint Matthews", name)
            assertEquals("London", city)
            assertEquals("Brixton Hill", street)
            assertEquals(0.0, income)
        }
    }

    @Test
    fun migrate_2_3() {
        database = migrationTestHelper.createDatabase(TEST_DB, 2)
            .apply {
                execSQL("""
                    INSERT INTO temple (
                    id, name, city, street, income
                    ) VALUES (
                    1, 'Saint Matthews', 'London', 'Brixton Hill', 0
                    )
                """.trimIndent()
                )
                close()
            }
        database = migrationTestHelper.runMigrationsAndValidate(
            TEST_DB,
            3,
            true,
            MIGRATION_2_3
        )

        val cursor = database.query("SELECT * FROM temple")
        MatcherAssert.assertThat(cursor, Is(notNullValue()))

        while (cursor.moveToNext()) {
            var index: Int
            index = cursor.getColumnIndexOrThrow("name")
            val name = cursor.getString(index)
            index = cursor.getColumnIndexOrThrow("city")
            val city = cursor.getString(index)
            index = cursor.getColumnIndexOrThrow("street")
            val street = cursor.getString(index)

            assertEquals("Saint Matthews", name)
            assertEquals("London", city)
            assertEquals("Brixton Hill", street)
        }
    }

    @Test
    fun migrate_3_4() {
        database = migrationTestHelper.createDatabase(TEST_DB, 3)
        database = migrationTestHelper.runMigrationsAndValidate(
            TEST_DB,
            4,
            true,
            MIGRATION_3_4
        )

        database.execSQL("INSERT INTO  priest VALUES (1, 'Pan Kleks', 'Major')")
        val cursor = database.query("SELECT * FROM priest")
        MatcherAssert.assertThat(cursor, Is(notNullValue()))

        while (cursor.moveToNext()) {
            var index: Int
            index = cursor.getColumnIndexOrThrow("name")
            val name = cursor.getString(index)
            index = cursor.getColumnIndexOrThrow("rank")
            val rank = cursor.getString(index)

            assertEquals("Pan Kleks", name)
            assertEquals("Major", rank)
        }
    }

    @Test
    fun migrate_4_5() {
        database = migrationTestHelper.createDatabase(TEST_DB, 4).apply {
            execSQL("INSERT INTO  priest VALUES (1, 'Pan Kleks', 'Major')")
        }
        database = migrationTestHelper.runMigrationsAndValidate(
            TEST_DB,
            5,
            true,
            MIGRATION_4_5
        )

        try {
            database.query("SELECT * FROM priest")
            fail("DB query to failed.")
        } catch (e: Exception) { }
    }

    companion object {
        private const val TEST_DB = "TEST_TEMPLE_DATABASE"
    }
}
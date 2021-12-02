package com.mclowicz.mcmigration

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Temple::class], version = 5)
abstract class TempleDatabase : RoomDatabase() {
    abstract fun templeDao(): TempleDao

    companion object {
        const val DATABASE_NAME = "db"
        fun createDatabase(applicationContext: Context): TempleDatabase {
            return Room.databaseBuilder(
                applicationContext,
                TempleDatabase::class.java,
                DATABASE_NAME
            )
                .allowMainThreadQueries()
                .addMigrations(
                    MIGRATION_1_2,
                    MIGRATION_2_3,
                    MIGRATION_3_4,
                    MIGRATION_4_5
                )
                .build()
        }
    }
}

package com.mclowicz.mcmigration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * DB_v_2 -> adds the income field
 */
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE temple ADD COLUMN income DOUBLE NOT NULL DEFAULT 0"
        )
    }
}

/**
 * DB_v_3 -> set income field as nullable
 */
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        listOf(
            "CREATE TABLE Temples_temp (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT NOT NULL, city TEXT NOT NULL, street TEXT NOT NULL)",
            "INSERT INTO Temples_temp (id, name, city, street) SELECT id, name, city, street FROM temple",
            "DROP TABLE temple",
            "ALTER TABLE Temples_temp RENAME TO temple"
        ).forEach { statement -> database.execSQL(statement) }
    }
}

/**
 * DB_v_3 -> add new Priest entity
 */
val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        listOf(
            "CREATE TABLE priest (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT NOT NULL, rank TEXT NOT NULL)"
        ).forEach { statement -> database.execSQL(statement) }
    }
}

/**
 * DB_v_3 -> removed Priest entity
 */
val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE priest")
    }
}
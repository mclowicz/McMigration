package com.mclowicz.mcmigration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TempleDatabase.createDatabase(this.applicationContext).apply {
            templeDao().insertTemple(
                Temple(
                    name = "Saint Matthews",
                    city = "London",
                    street = "Brixton Hill",
//                    income = 0.0
                )
            )
        }
    }
}
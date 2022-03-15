package com.example.infinidnd.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.preference.PreferenceManager
import com.example.infinidnd.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("main", "oncreate")
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val theme: String? = pref.getString("mode", "two")
        val layout: Int
        if (theme == "two") layout = R.layout.activity_main else layout = R.layout.activity_main_1

        Log.d("Theme: ", theme.toString())
        Log.d("layout: ", layout.toString())

        super.onCreate(savedInstanceState)
        setContentView(layout)

        val damageBtn: Button = findViewById(R.id.button_damage)
        damageBtn.setOnClickListener {
            startActivity(Intent(this, DamageActivity::class.java))
        }

        val schoolsBtn: Button = findViewById(R.id.button_schools)
        schoolsBtn.setOnClickListener {
            startActivity(Intent(this, SchoolsActivity::class.java))
        }
        val monstersBtn: Button = findViewById(R.id.button_monster)
        monstersBtn.setOnClickListener {
            startActivity(Intent(this, MonstersActivity::class.java))
        }
        val equipmentBtn: Button = findViewById(R.id.button_equipment)
        equipmentBtn.setOnClickListener {
            startActivity(Intent(this, EquipmentActivity::class.java))
        }
        val spellsBtn: Button = findViewById(R.id.button_spells)
        spellsBtn.setOnClickListener {
            startActivity(Intent(this, SpellsActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
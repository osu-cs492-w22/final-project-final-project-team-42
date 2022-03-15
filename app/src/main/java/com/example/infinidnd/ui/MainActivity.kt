package com.example.infinidnd.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.infinidnd.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
}
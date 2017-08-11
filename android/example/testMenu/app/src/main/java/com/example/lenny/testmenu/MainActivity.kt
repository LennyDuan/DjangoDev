package com.example.lenny.testmenu

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_dashboard -> {
                val intent = Intent(this,Main2Activity::class.java)
                startActivity(intent)
            }
            R.id.navigation_notifications -> {
                val intent = Intent(this,Main3Activity::class.java)
                startActivity(intent)
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.isEnabled = false
        button.setOnClickListener {
            Toast.makeText(this, "hello world", Toast.LENGTH_SHORT).show()
        }
        navigation.menu.getItem(2).isEnabled = false
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}

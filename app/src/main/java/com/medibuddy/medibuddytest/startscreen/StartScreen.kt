package com.medibuddy.medibuddytest.startscreen

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.medibuddy.medibuddytest.R
import com.medibuddy.medibuddytest.editprofile.view.EditProfileActivity

class StartScreen : AppCompatActivity(){

private lateinit var tvClick :TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        supportActionBar!!.hide()

        initview()
        eventListners()

    }

    private fun eventListners() {

        tvClick.setOnClickListener {

            val intent = Intent(this@StartScreen, EditProfileActivity::class.java)
            startActivity(intent)
        }


    }

    private fun initview() {

        tvClick = findViewById(R.id.tvClick)
    }

}
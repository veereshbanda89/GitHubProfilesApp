package com.example.githubprofiles.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.githubprofiles.R
import com.example.githubprofiles.constants.Constants

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({

            startActivity(Intent(this, HomeActivity::class.java))

            finish()
        },Constants.SPLASH_TIME_OUT)
    }
}

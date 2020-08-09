package com.openclassrooms.realestatemanager.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.view.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 10000 //3 second

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        Handler().postDelayed({
            //This method will be executed once the timer is over
            startActivity(Intent(this, LoginActivity::class.java))
            //close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }
}
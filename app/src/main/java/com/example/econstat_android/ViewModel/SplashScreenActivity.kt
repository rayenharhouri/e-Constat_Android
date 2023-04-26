package com.example.econstat_android.ViewModel

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat.setBackground
import com.example.econstat_android.MainActivity
import com.example.econstat_android.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen_layout)
        val myView = findViewById<View>(R.id.backgroundView)
        val sdk:Int = android.os.Build.VERSION.SDK_INT;
        val drawable = R.drawable.group_33623
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            myView.setBackgroundDrawable(ContextCompat.getDrawable(this, drawable))
        } else {
            myView.background = ContextCompat.getDrawable(this, drawable)
        }
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
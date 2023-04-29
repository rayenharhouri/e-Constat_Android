package com.example.econstat_android.ViewModel

import android.R
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton


class carA_DamageActivity : AppCompatActivity() {
    private lateinit var topLeft : Button
    private lateinit var topRight : Button
    private lateinit var midLeft : Button
    private lateinit var midRight : Button
    private lateinit var bottomLeft : Button
    private lateinit var bottomRight : Button
    private lateinit var confirm : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.econstat_android.R.layout.activity_car_adamage)
        supportActionBar?.hide()
        setFullScreen(this@carA_DamageActivity)
        //VAR
        var TL = true
        var ML = true
        var BL = true
        var TR = true
        var MR = true
        var BR = true
        //INIT
        topLeft = findViewById<MaterialButton>(com.example.econstat_android.R.id.topLeft)
        topRight = findViewById<MaterialButton>(com.example.econstat_android.R.id.topRight)
        midLeft = findViewById<MaterialButton>(com.example.econstat_android.R.id.midLeft)
        midRight = findViewById<MaterialButton>(com.example.econstat_android.R.id.midRight)
        bottomLeft = findViewById<MaterialButton>(com.example.econstat_android.R.id.bottomLeft)
        bottomRight = findViewById<MaterialButton>(com.example.econstat_android.R.id.bottomRight)
        confirm = findViewById<Button>(com.example.econstat_android.R.id.confirm)
        val popupLayout = findViewById<FrameLayout>(com.example.econstat_android.R.id.popup_layout)
        val closeButton = findViewById<ImageButton>(com.example.econstat_android.R.id.close_button)
        popupLayout.visibility = View.VISIBLE
        confirm.visibility = View.GONE
        //change icon
        topLeft.setOnClickListener{
            changeIcon(TL, topLeft as MaterialButton?)
            TL = !TL
        }
        midLeft.setOnClickListener{
            changeIcon(ML, midLeft as MaterialButton?)
            ML = !ML
        }
        bottomLeft.setOnClickListener{
            changeIcon(BL, bottomLeft as MaterialButton?)
            BL = !BL
        }
        topRight.setOnClickListener{
            changeIcon(TR, topRight as MaterialButton?)
            TR = !TR
        }
        midRight.setOnClickListener{
            changeIcon(MR, midRight as MaterialButton?)
            MR = !MR
        }
        bottomRight.setOnClickListener{
            changeIcon(BR, bottomRight as MaterialButton?)
            BR = !BR
        }
        confirm.setOnClickListener{
            val intent = Intent(this@carA_DamageActivity,carB_DamageActivity::class.java)
            startActivity(intent)
        }




// Hide popup dialog when close button is clicked

// Hide popup dialog when close button is clicked
        closeButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                popupLayout.visibility = View.GONE
                confirm.visibility = View.VISIBLE
            }
        })

    }
    fun setFullScreen(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            activity.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }
    fun changeIcon(stateIcon:Boolean, button: MaterialButton?){
        if (stateIcon) {
            (button as MaterialButton?)?.setIconResource(com.example.econstat_android.R.drawable.damage)
        } else {
            (button as MaterialButton?)?.setIconResource(com.example.econstat_android.R.drawable.shield)
        }
    }
}
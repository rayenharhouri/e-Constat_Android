package com.example.econstat_android.ViewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.example.econstat_android.MainActivity
import com.example.econstat_android.R
import com.example.econstat_android.Services.ApiService
import com.example.econstat_android.Services.CarService
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class carB_DamageActivity : AppCompatActivity() {
    private lateinit var carb : ImageView
    private lateinit var topLeft : Button
    private lateinit var topRight : Button
    private lateinit var midLeft : Button
    private lateinit var midRight : Button
    private lateinit var bottomLeft : Button
    private lateinit var bottomRight : Button
    private lateinit var confirm : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_bdamage)
        supportActionBar?.hide()
        setFullScreen(this@carB_DamageActivity)
        //INIT
        carb = findViewById(R.id.imageView2)
        carb.visibility = View.GONE
        var TL = true
        var ML = true
        var BL = true
        var TR = true
        var MR = true
        var BR = true
        //INIT
        topLeft = findViewById<MaterialButton>(R.id.topLeft)
        topRight = findViewById<MaterialButton>(R.id.topRight)
        midLeft = findViewById<MaterialButton>(R.id.midLeft)
        midRight = findViewById<MaterialButton>(R.id.midRight)
        bottomLeft = findViewById<MaterialButton>(R.id.bottomLeft)
        bottomRight = findViewById<MaterialButton>(R.id.bottomRight)
        confirm = findViewById<Button>(R.id.confirm)
        val popupLayout = findViewById<FrameLayout>(R.id.popup_layout)
        val closeButton = findViewById<ImageButton>(R.id.close_button)
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
            ApiService.carService.addCarDamageA(
                CarService.CarDamageAbody(
                    TL,ML,BL,TR,MR,BR,"643dbe9c5d831b98f40d95ec"
                )
            ).enqueue(
                object : Callback<CarService.CarDamageResponse> {
                    override fun onResponse(
                        call: Call<CarService.CarDamageResponse>,
                        response: Response<CarService.CarDamageResponse>
                    ) {
                        if (response.code() == 200) {
                            println("status code is " + response.code())
                            showDialog(this@carB_DamageActivity, "","Created ✅")
                        } else if (response.code() == 409) {
                            showDialog(this@carB_DamageActivity, "error ","Caution ⚠️")
                        }
                        else if (response.code() == 400) {
                            showDialog(this@carB_DamageActivity, "error","Caution ⚠️")
                        }
                        else {
                            println("status code is " + response.code())
                        }
                    }

                    override fun onFailure(call: Call<CarService.CarDamageResponse>, t: Throwable) {
                        println("HTTP ERROR")
                        t.printStackTrace()
                    }
                }
            )

        }
        closeButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                popupLayout.visibility = View.GONE
                confirm.visibility = View.VISIBLE
                carb.visibility = View.VISIBLE
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
    private fun changeIcon(stateIcon:Boolean, button: MaterialButton?){
        if (stateIcon) {
            (button)?.setIconResource(R.drawable.damage)
        } else {
            (button)?.setIconResource(R.drawable.shield)
        }
    }
    fun showDialog(activityName: Context, message:String,title: String){
        val builder = AlertDialog.Builder(activityName)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK", null)
        builder.setOnDismissListener{
            val intent = Intent(this@carB_DamageActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        val dialog = builder.create()
        dialog.show()
    }}
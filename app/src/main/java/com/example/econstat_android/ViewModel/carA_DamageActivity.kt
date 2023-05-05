package com.example.econstat_android.ViewModel

import android.R
import android.app.Activity
import android.content.Context
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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.econstat_android.MainActivity
import com.example.econstat_android.Model.Car
import com.example.econstat_android.Model.CarDamage
import com.example.econstat_android.Model.User
import com.example.econstat_android.Services.ApiService
import com.example.econstat_android.Services.CarService
import com.example.econstat_android.Services.ConstatService
import com.example.econstat_android.utils.Constant
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class carA_DamageActivity : AppCompatActivity() {
    private lateinit var carId: String
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
        //getting the car from recyclerView
        carId = intent.getStringExtra("car").toString()

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
            ApiService.carService.addCarDamageA(
                CarService.CarDamageAbody(
                    TL,ML,BL,TR,MR,BR,carId
                )
            ).enqueue(
                object : Callback<CarService.CarDamageResponse> {
                    override fun onResponse(
                        call: Call<CarService.CarDamageResponse>,
                        response: Response<CarService.CarDamageResponse>
                    ) {
                        if (response.code() == 200) {
                            println("status code is " + response.code())
                            val json = Gson().toJson(response.body()!!.carDamage)
                            val carDamage = Gson().fromJson(json, CarDamage::class.java)
                            val sharedPreferences = getSharedPreferences(Constant.SHARED_PREF_SESSION, MODE_PRIVATE)
                            val insuranceData = sharedPreferences.getString("insurance", "")
                            constatA(insuranceData.toString(),carDamage._id)
                            showDialog(this@carA_DamageActivity, "Please read instruction on the next page","Created ✅")

                        } else if (response.code() == 409) {
                            showDialog(this@carA_DamageActivity, "error ","Caution ⚠️")
                        }
                        else if (response.code() == 400) {
                            showDialog(this@carA_DamageActivity, "error","Caution ⚠️")
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
    fun showDialog(activityName: Context, message:String,title: String){
        val builder = AlertDialog.Builder(activityName)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK", null)
        builder.setOnDismissListener{
            val intent = Intent(this@carA_DamageActivity,carB_DamageActivity::class.java)
            startActivity(intent)
            finish()
        }
        val dialog = builder.create()
        dialog.show()
    }
    fun constatA(insurance: String,carDamage: String) {
        ApiService.constatService.NewConstat(
            ConstatService.constatBody(insurance,carDamage)
        ).enqueue(
            object : Callback<ConstatService.ConstatResponse> {
                override fun onResponse(
                    call: Call<ConstatService.ConstatResponse>,
                    response: Response<ConstatService.ConstatResponse>
                ) {
                    if (response.code() == 200) {
                        showDialog(this@carA_DamageActivity, "The first part of the report is done","Created ✅")
                    } else if (response.code() == 409) {
                        showDialog(this@carA_DamageActivity, "error ","Caution ⚠️")
                    }
                    else if (response.code() == 400) {
                        showDialog(this@carA_DamageActivity, "error","Caution ⚠️")
                    }
                    else {
                        println("status code is " + response.code())
                    }
                }

                override fun onFailure(call: Call<ConstatService.ConstatResponse>, t: Throwable) {
                    println("HTTP ERROR")
                    t.printStackTrace()
                }
            }
        )
    }
}
package com.example.econstat_android.ViewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.econstat_android.MainActivity
import com.example.econstat_android.R
import com.example.econstat_android.Services.ApiService
import com.example.econstat_android.Services.UserService
import com.example.econstat_android.utils.Constant
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class changePwdActivity : AppCompatActivity(){
    private lateinit var changePwd_et: TextInputEditText
    private lateinit var changePwd_lyt: TextInputLayout
    private lateinit var ConfirmChangePwd_et: TextInputEditText
    private lateinit var ConfirmChangePwd_lyt: TextInputLayout
    private lateinit var nextbtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_passwod_layout)
        //VAR
        val context = this@changePwdActivity
        supportActionBar?.hide()
        setFullScreen(context)
        val email = intent.getStringExtra("email")
        //INIT components
        changePwd_et = findViewById(R.id.et_cPwd)
        changePwd_lyt = findViewById(R.id.lyt_cPwd)
        ConfirmChangePwd_et = findViewById(R.id.et_CcPwd)
        ConfirmChangePwd_lyt = findViewById(R.id.lyt_CcPwd)
        nextbtn = findViewById(R.id.btnNext)
        nextbtn.setOnClickListener{
            if(comparePasswords(changePwd_et.text.toString(), ConfirmChangePwd_et.text.toString())){
                ApiService.userService.changePwd(UserService.changePwdBody(
                    email.toString(),changePwd_et.text.toString(),
                    ConfirmChangePwd_et.text.toString())).enqueue( object :
                    Callback<UserService.UserResponse> {
                    override fun onResponse(
                        call: Call<UserService.UserResponse>,
                        response: Response<UserService.UserResponse>
                    ) {
                        if (response.code() == 200) {
                            Toast.makeText(this@changePwdActivity, "Password Changed ✅", Toast.LENGTH_SHORT).show()
                            val intent =
                                Intent(this@changePwdActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else if(response.code() == 400) {
                            showDialog(context,"Error")
                        }
                        else {
                            println("status code is " + response.code())
                        }
                    }

                    override fun onFailure(call: Call<UserService.UserResponse>, t: Throwable) {

                        println("HTTP ERROR")
                        t.printStackTrace()}

                })
            } else {
                showDialog(context,"Passwords do not match. Please try again. ❌")
            }
        }
    }
    fun comparePasswords(pwd:String , cPwd: String):Boolean{
        if(pwd.compareTo(cPwd) == 0 ){
            return true
        }
        return false
    }
    private fun showDialog(activityName: Context,message:String){
        val builder = AlertDialog.Builder(this@changePwdActivity)
        builder.setTitle("Caution ⚠️")
        builder.setMessage(message)
        builder.setPositiveButton("OK", null)
        val dialog = builder.create()
        dialog.show()
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

}
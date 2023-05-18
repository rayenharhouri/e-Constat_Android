package com.example.econstat_android.ViewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.econstat_android.MainActivity
import com.example.econstat_android.Model.User
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

class changePwd : AppCompatActivity() {
    private lateinit var oldPwd_et: TextInputEditText
    private lateinit var oldPwd_lyt: TextInputLayout
    private lateinit var changePwd_et: TextInputEditText
    private lateinit var changePwd_lyt: TextInputLayout
    private lateinit var ConfirmChangePwd_et: TextInputEditText
    private lateinit var ConfirmChangePwd_lyt: TextInputLayout
    private lateinit var nextbtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pwd)
        setFullScreen(this@changePwd)
        supportActionBar?.hide()
        //INIT
        oldPwd_et = findViewById(R.id.et_oldPwd)
        oldPwd_lyt = findViewById(R.id.lyt_oldPwd)
        changePwd_et = findViewById(R.id.et_cPwd)
        changePwd_lyt = findViewById(R.id.lyt_cPwd)
        ConfirmChangePwd_et = findViewById(R.id.et_CcPwd)
        ConfirmChangePwd_lyt = findViewById(R.id.lyt_CcPwd)
        nextbtn = findViewById(R.id.btnNext)
        //VAR
        val context = this@changePwd
        var user = getUser(this@changePwd)
        nextbtn.setOnClickListener{
        if (verifyPwd(changePwd_et.text.toString(),ConfirmChangePwd_et.text.toString())) {
            ApiService.userService.changePwd(
                UserService.changePwdBody(
                user.email,changePwd_et.text.toString(),
                ConfirmChangePwd_et.text.toString())).enqueue( object :
                Callback<UserService.UserResponse> {
                override fun onResponse(
                    call: Call<UserService.UserResponse>,
                    response: Response<UserService.UserResponse>
                ) {
                    if (response.code() == 200) {
                        Toast.makeText(this@changePwd, "Password Updated ✅", Toast.LENGTH_SHORT).show()
                        val intent =
                            Intent(this@changePwd, MainActivity::class.java)
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
            showDialog(this@changePwd,"Password don't match")
        }
        }

    }
    private fun getUser(context: Context) : User{
        val sharedPreferences =
            context.getSharedPreferences(Constant.SHARED_PREF_SESSION, MODE_PRIVATE)
        val userData = sharedPreferences.getString("USER_DATA", "")
        val user = Gson().fromJson(userData, User::class.java)
        return user
    }
    private fun verifyPwd(pwd:String,pwdC:String) : Boolean{
        if(changePwd_et.text.toString().compareTo(ConfirmChangePwd_et.text.toString())==0){
            return true
        }
        return false
    }
     fun showDialog(activityName:Context,message:String){
        val builder = AlertDialog.Builder(activityName)
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
package com.example.econstat_android.ViewModel

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.chaos.view.PinView
import com.example.econstat_android.MainActivity
import com.example.econstat_android.R
import com.example.econstat_android.Services.ApiService
import com.example.econstat_android.Services.UserService
import com.example.econstat_android.utils.Constant
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class forgetPwdOtpActivity : AppCompatActivity() {

    private lateinit var pinView: PinView
    private lateinit var nextBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //hide Toolbar
        supportActionBar?.hide()

        setContentView(R.layout.forgetpwd_otp_layout)
        // init components
        pinView = findViewById(R.id.pinView)
        nextBtn = findViewById(R.id.confirmOtp)
        var finalOtp = ""
        pinView.requestFocus()
        val inputMethod : InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethod.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY)
        pinView.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().length == 4){
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(pinView.windowToken, 0)
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun afterTextChanged(p0: Editable?) {
                finalOtp = pinView.text.toString()
            }
        })

        nextBtn.setOnClickListener{
            ApiService.userService.confirmOtp(
                UserService.otp(finalOtp)
            ).enqueue(
                object : Callback<UserService.UserResponse> {
                    override fun onResponse(
                        call: Call<UserService.UserResponse>,
                        response: Response<UserService.UserResponse>
                    ) {
                        if (response.code() == 200) {
                            Toast.makeText(this@forgetPwdOtpActivity, "OTP Accepted ✅", Toast.LENGTH_SHORT).show()
                            val intent =
                                Intent(this@forgetPwdOtpActivity, changePwdActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else if(response.code() == 400) {
                            val builder = AlertDialog.Builder(this@forgetPwdOtpActivity)
                            builder.setTitle("Caution ⚠️")
                            builder.setMessage("Wrong OTP ❌")
                            builder.setPositiveButton("OK", null)
                            val dialog = builder.create()
                            dialog.show()
                        }
                        else {
                            println("status code is " + response.code())
                        }
                    }

                    override fun onFailure(call: Call<UserService.UserResponse>, t: Throwable) {

                        println("HTTP ERROR")
                        t.printStackTrace()}

                }
            )
        }

    }
}
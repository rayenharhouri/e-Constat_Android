package com.example.econstat_android.ViewModel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.econstat_android.R
import com.example.econstat_android.Services.ApiService
import com.example.econstat_android.Services.UserService
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgetPwdActivity : AppCompatActivity() {
    private lateinit var emailEt : TextInputEditText
    private lateinit var emailLyt : TextInputLayout
    private lateinit var nextBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Var
        val context = this@ForgetPwdActivity
        supportActionBar?.hide()
        setContentView(R.layout.forgetpwd_layout)
        emailEt = findViewById(R.id.et_email)
        emailLyt = findViewById(R.id.lyt_email)
        nextBtn = findViewById(R.id.btnNext)

        nextBtn.setOnClickListener{
            ApiService.userService.sendOtp(
                UserService.emailBody(emailEt.text.toString())
            ).enqueue(
                object : Callback<UserService.UserResponse> {
                    override fun onResponse(
                        call: Call<UserService.UserResponse>,
                        response: Response<UserService.UserResponse>
                    ) {
                        if (response.code() == 200) {
                            val intent = Intent(this@ForgetPwdActivity,forgetPwdOtpActivity::class.java).apply {
                                putExtra("email",  emailEt.text.toString())
                            }
                            startActivity(intent)
                            finish()
                        }
                        else if(response.code() == 404) {
                            showDialog(context,"Use an existing mail ❌")
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
    private fun showDialog(activityName: Context, message:String){
        val builder = AlertDialog.Builder(activityName)
        builder.setTitle("Caution ⚠️")
        builder.setMessage(message)
        builder.setPositiveButton("OK", null)
        val dialog = builder.create()
        dialog.show()
    }

}
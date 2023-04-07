package com.example.econstat_android.ViewModel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
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

class SignUpActivity : AppCompatActivity(){
    private lateinit var emailEt : TextInputEditText
    private lateinit var emailLyt : TextInputLayout
    private lateinit var passwordEt : TextInputEditText
    private lateinit var passwordLyt : TextInputLayout
    private lateinit var firstNameEt : TextInputEditText
    private lateinit var firstNameLyt : TextInputLayout
    private lateinit var lastNameEt : TextInputEditText
    private lateinit var lastNameLyt : TextInputLayout
    private lateinit var addressEt : TextInputEditText
    private lateinit var addressLyt : TextInputLayout
    private lateinit var driverLicenseEt : TextInputEditText
    private lateinit var driverLicenseLyt : TextInputLayout
    private lateinit var signUpBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_layout)
        //Var
        val context = this@SignUpActivity
        supportActionBar?.hide()

        emailEt = findViewById(R.id.et_email)
        emailLyt = findViewById(R.id.lyt_email)
        passwordEt = findViewById(R.id.et_password)
        passwordLyt = findViewById(R.id.lyt_password)
        firstNameEt = findViewById(R.id.et_firstName)
        firstNameLyt = findViewById(R.id.lyt_firstName)
        lastNameEt = findViewById(R.id.et_lastName)
        lastNameLyt = findViewById(R.id.lyt_lastName)
        addressEt = findViewById(R.id.et_address)
        addressLyt = findViewById(R.id.lyt_address)
        driverLicenseEt = findViewById(R.id.et_driverLicense)
        driverLicenseLyt = findViewById(R.id.lyt_driverLicense)
        signUpBtn = findViewById(R.id.btnSignUp)

        signUpBtn!!.setOnClickListener {
            ApiService.userService.signUp(
                UserService.UserBody(
                    firstNameEt.text.toString(),
                    lastNameEt.text.toString(),
                    emailEt.text.toString(),
                    passwordEt.text.toString(),
                    53344511,
                    addressEt.text.toString(),
                    driverLicenseEt.text.toString()
                )
            ).enqueue(
                object : Callback<UserService.UserResponse> {
                    override fun onResponse(
                        call: Call<UserService.UserResponse>,
                        response: Response<UserService.UserResponse>
                    ) {
                        if (response.code() == 201) {
                            ApiService.userService.sendVerification(
                                UserService.emailBody(emailEt.text.toString())
                            ).enqueue(
                                object : Callback<UserService.UserResponse> {
                                    override fun onResponse(
                                        call: Call<UserService.UserResponse>,
                                        response: Response<UserService.UserResponse>
                                    ) {
                                        val intent =
                                            Intent(this@SignUpActivity, LoginActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                        println("status code is " + response.code())
                                    }
                                    override fun onFailure(call: Call<UserService.UserResponse>, t: Throwable) {
                                        println("HTTP ERROR")
                                        t.printStackTrace()
                                    }
                                }
                            )

                        } else if (response.code() == 403) {
                            showDialog(context)
                        } else {
                            println("status code is " + response.code())
                        }
                    }

                    override fun onFailure(call: Call<UserService.UserResponse>, t: Throwable) {

                        println("HTTP ERROR")
                        t.printStackTrace()
                    }

                }
            )

        }
    }

    private fun showDialog(activityName: Context){
        val builder = AlertDialog.Builder(this@SignUpActivity)
        builder.setTitle("Caution ⚠️")
        builder.setMessage("User already exist")
        builder.setPositiveButton("OK", null)
        val dialog = builder.create()
        dialog.show()
    }
}

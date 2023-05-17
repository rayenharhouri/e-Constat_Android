package com.example.econstat_android.ViewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.Toolbar
import com.example.econstat_android.MainActivity
import com.example.econstat_android.Model.User
import com.example.econstat_android.R
import com.example.econstat_android.Services.ApiService
import com.example.econstat_android.Services.UserService
import com.example.econstat_android.utils.Constant
import com.example.econstat_android.utils.SessionManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var emailEt: TextInputEditText
    private lateinit var passwordEt: TextInputEditText
    private lateinit var emailLyt: TextInputLayout
    private lateinit var passwordLyt: TextInputLayout
    private lateinit var rememberMe: CheckBox
    private lateinit var signInBtn: Button
    private lateinit var signUpBtn: LinearLayoutCompat
    private lateinit var forgetPwd: LinearLayoutCompat
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        //Var
        sessionManager = SessionManager(this)
        val context = this@LoginActivity
        //INIT UI ELEMENTS
        emailEt = findViewById(R.id.et_email)
        emailLyt = findViewById(R.id.lyt_email)
        passwordEt = findViewById(R.id.et_password)
        passwordLyt = findViewById(R.id.lyt_password)
        rememberMe = findViewById(R.id.cb_rm)
        signInBtn = findViewById(R.id.buttonSignIn)
        signUpBtn = findViewById(R.id.signup)
        forgetPwd = findViewById(R.id.forgotPwd)
        supportActionBar?.hide()
        setFullScreen(context)
        if (sessionManager.isLoggedIn) {
            keepSession()
        }
        signUpBtn.setOnClickListener {
            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        forgetPwd.setOnClickListener {
            var intent = Intent(this, ForgetPwdActivity::class.java)
            startActivity(intent)
        }
        signInBtn.setOnClickListener {
            signIn(this@LoginActivity)
        }
    }

    private fun validateInput(): Boolean {
        //Vérifier si les champs des 2 EditText ne sont pas vides
        if (setError(passwordEt, getString(R.string.must_not_be_empty)) || setError(
                emailEt,
                getString(R.string.must_not_be_empty)
            )
        ) {
            return false
        } else {
            //vérifier si l'adresse email est valide
            return emailVerified()
        }
    }

    private fun emailVerified(): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(emailEt.text).matches()) {
            (emailEt.parent.parent as TextInputLayout).isErrorEnabled = true
            (emailEt.parent.parent as TextInputLayout).error = getString(R.string.check_email)
            return false
        }
        return true
    }

    private fun setError(et: TextInputEditText, errorMsg: String): Boolean {
        if (et.text?.isEmpty() == true) {
            (et.parent.parent as TextInputLayout).isErrorEnabled = true
            (et.parent.parent as TextInputLayout).error = errorMsg
            return true
        } else {
            (et.parent.parent as TextInputLayout).isErrorEnabled = false
            return false
        }
    }

    fun showDialog(activityName: Context, message: String) {
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
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    fun signIn(context: Context) {
        ApiService.userService.signIn(
            UserService.LoginBody(
                emailEt.text.toString(),
                passwordEt.text.toString()
            )
        ).enqueue(object : Callback<UserService.UserResponse> {
            override fun onResponse(
                call: Call<UserService.UserResponse>,
                response: Response<UserService.UserResponse>
            ) {
                if (response.code() == 200) {
                    if (rememberMe.isChecked) {
                        val sharedPreferences =
                            getSharedPreferences(Constant.SHARED_PREF_SESSION, MODE_PRIVATE)
                        val sharedPreferencesEditor: SharedPreferences.Editor =
                            sharedPreferences.edit()
                        val json = Gson().toJson(response.body()!!.user)
                        sharedPreferencesEditor.putString("USER_DATA", json)
                        sharedPreferencesEditor.apply()



                    }
                    val intent =
                        Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else if (response.code() == 403) {
                    showDialog(context, "Please Verify your account on ${emailEt.text.toString()}")
                } else if (response.code() == 400) {
                    showDialog(context, "Wrong password ❌")
                } else {
                    println("status code is " + response.code())
                }
            }

            override fun onFailure(call: Call<UserService.UserResponse>, t: Throwable) {

                println("HTTP ERROR")
                t.printStackTrace()
            }

        })
    }

    fun keepSession() {
        val intent =
            Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
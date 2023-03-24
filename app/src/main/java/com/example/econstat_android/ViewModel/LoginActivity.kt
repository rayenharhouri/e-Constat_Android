package com.example.econstat_android.ViewModel

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.econstat_android.MainActivity
import com.example.econstat_android.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity(){
    private lateinit var emailEt : TextInputEditText
    private lateinit var passwordEt : TextInputEditText
    private lateinit var emailLyt : TextInputLayout
    private lateinit var passwordLyt : TextInputLayout
    private lateinit var rememberMe : CheckBox
    private lateinit var signInBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        //INIT UI ELEMENTS
        emailEt =findViewById(R.id.et_email)
        emailLyt =findViewById(R.id.lyt_email)
        passwordEt =findViewById(R.id.et_password)
        passwordLyt =findViewById(R.id.lyt_password)
        rememberMe =findViewById(R.id.cb_rm)
        signInBtn = findViewById(R.id.buttonSignIn)
        supportActionBar?.hide()
        signInBtn.setOnClickListener{
            if(validateInput()){
                var  intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun validateInput(): Boolean {
        //Vérifier si les champs des 2 EditText ne sont pas vides
        if(setError(passwordEt,getString(R.string.must_not_be_empty)) || setError(emailEt,getString(R.string.must_not_be_empty))){
            return false
        }else{
            //vérifier si l'adresse email est valide
            return emailVerified()
        }
    }
    private fun emailVerified():Boolean {
        if(!Patterns.EMAIL_ADDRESS.matcher(emailEt.text).matches()){
            (emailEt.parent.parent as TextInputLayout).isErrorEnabled = true
            (emailEt.parent.parent as TextInputLayout).error = getString(R.string.check_email)
            return false
        }
        return true
    }

    private fun setError(et: TextInputEditText, errorMsg: String): Boolean {
        if(et.text?.isEmpty() == true){
            (et.parent.parent as TextInputLayout).isErrorEnabled = true
            (et.parent.parent as TextInputLayout).error = errorMsg
            return true
        }else{
            (et.parent.parent as TextInputLayout).isErrorEnabled = false
            return false
        }
    }
}
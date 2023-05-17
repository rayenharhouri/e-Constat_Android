package com.example.econstat_android.ViewModel

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
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

class profileActivity : Fragment() {
    private lateinit var emailEt : TextInputEditText
    private lateinit var emailLyt : TextInputLayout
    private lateinit var firstNameEt : TextInputEditText
    private lateinit var firstNameLyt : TextInputLayout
    private lateinit var lastNameEt : TextInputEditText
    private lateinit var lastNameLyt : TextInputLayout
    private lateinit var driverLicenseEt : TextInputEditText
    private lateinit var driverLicenseLyt : TextInputLayout
    private lateinit var btnConfirm : Button
    private lateinit var btnChangePwd : Button
    private lateinit var edit : LinearLayoutCompat

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.profile_layout, container, false)
        //INIT
        emailEt = view.findViewById(R.id.et_email1)
        firstNameEt = view.findViewById(R.id.et_firstName)
        lastNameEt = view.findViewById(R.id.et_lastName)
        driverLicenseEt = view.findViewById(R.id.et_driverLicense)
        emailLyt = view.findViewById(R.id.lyt_email)
        driverLicenseLyt = view.findViewById(R.id.lyt_driverLicense)
        lastNameLyt = view.findViewById(R.id.lyt_lastName)
        firstNameLyt = view.findViewById(R.id.lyt_firstName)
        btnConfirm = view.findViewById(R.id.btnConfirm)
        btnChangePwd = view.findViewById(R.id.btnChangePwd)
        edit = view.findViewById(R.id.edit)
        //Var
        var FieldsState = false
        val context = requireContext()
        //getting info from sharedpref
        val sharedPreferences =
            context.getSharedPreferences(Constant.SHARED_PREF_SESSION, MODE_PRIVATE)
        val userData = sharedPreferences.getString("USER_DATA", "")
        if (userData != null) {
            val user = Gson().fromJson(userData, User::class.java)
            emailEt.setText(user.email)
            firstNameEt.setText(user.name)
            lastNameEt.setText(user.lastName)
            driverLicenseEt.setText(user.driverLicense)
            edit.setOnClickListener {
                changeFieldsState(FieldsState)
                FieldsState = !FieldsState
            }
            btnChangePwd.setOnClickListener{
                val intent = Intent(context, changePwd::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
            btnConfirm.setOnClickListener {
                ApiService.userService.updateProfile(
                    UserService.updateProfileBody(
                        driverLicenseEt.text.toString(),
                        firstNameEt.text.toString(),
                        lastNameEt.text.toString(),
                        user.token
                    )
                ).enqueue(object : Callback<UserService.UserResponse> {
                    override fun onResponse(
                        call: Call<UserService.UserResponse>,
                        response: Response<UserService.UserResponse>
                    ) {
                        if (response.code() == 200) {
                            Toast.makeText(context, "Profile Updated ✅", Toast.LENGTH_SHORT).show()
                            requireActivity().recreate()
                        } else if (response.code() == 400) {
                            showDialog(context, "Error ❌")
                            println("status code is " + response.message())

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
        }
        return view
    }
    fun changeFieldsState(state: Boolean) {
        if (!state) {
            firstNameEt.isEnabled = true
            lastNameEt.isEnabled = true
            driverLicenseEt.isEnabled = true
        } else {
            firstNameEt.isEnabled = false
            lastNameEt.isEnabled = false
            driverLicenseEt.isEnabled = false
        }
    }
     fun showDialog(activityName:Context,message:String){
        val builder = AlertDialog.Builder(activityName)
        builder.setTitle("Caution ⚠️")
        builder.setMessage(message)
        builder.setPositiveButton("OK", null)
        val dialog = builder.create()
        dialog.show()
    }
}


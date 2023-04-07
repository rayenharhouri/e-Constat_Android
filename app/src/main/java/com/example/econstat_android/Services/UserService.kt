package com.example.econstat_android.Services

import com.example.econstat_android.Model.User
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT


interface UserService {

    data class UserResponse(
        @SerializedName("user")
        val user: User
    )

    data class UserBody(
        val name: String,
        val lastName: String,
        val email: String,
        val password: String,
        val number: Int,
        val adress: String,
        val driverLicense: String
    )
    data class LoginBody(
        val email: String,
        val password: String
    )
    data class emailBody(
        val email: String
    )
    data class otp(
        val otp: String
    )
    data class changePwdBody(
        val email: String,
        val newPassword: String,
        val newPasswordConfirm: String
    )

    @POST("/user/signUp")
    fun signUp(@Body userBody: UserBody): Call<UserResponse>

    @POST("/user/send-confirmation-email")
    fun sendVerification(@Body emailBody: emailBody) : Call<UserResponse>

    @POST("/user/login")
    fun signIn(@Body loginBody: LoginBody): Call<UserResponse>

    @POST("/user/forgotPassword")
    fun sendOtp(@Body emailBody: emailBody): Call<UserResponse>

    @POST("/user/confirmationOtp")
    fun confirmOtp(@Body otp: otp): Call<UserResponse>

    @PUT("/user/updatePassword")
    fun changePwd(@Body changePwdBody: changePwdBody): Call<UserResponse>

}
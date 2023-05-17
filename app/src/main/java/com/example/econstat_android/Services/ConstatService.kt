package com.example.econstat_android.Services

import com.example.econstat_android.Model.Car
import com.example.econstat_android.Model.Constat
import com.example.econstat_android.Model.Insurance
import com.example.econstat_android.Model.User
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ConstatService {
    data class ConstatResponse (
        @SerializedName("constat")
        val constat : Constat
    )
    data class ConstatResponseUserB (
        @SerializedName("user")
        val user: User,
        @SerializedName("car")
        val car : Car,
        @SerializedName("Insurance")
        val insurance : Insurance
    )
    data class MailResponse (
        @SerializedName("constat")
        val constat : Constat
    )
    data class constatBody(
        val InsuranceA : String,
        val CarDamageA : String,
        val CarA : String
    )
    data class sendReportBody(
        val email : String
    )

    data class constatBBody(
        val constatId : String,
        val UserB : String,
        val CarB : String,
        val InsuranceB : String
    )
    data class userB(
        val ids : String
    )
    @POST("/constat/addNew")
    fun NewConstat(@Body constatBody: constatBody): Call<ConstatResponse>
    @POST("/constat/addNewB")
    fun NewConstatB(@Body constatBody: constatBBody): Call<ConstatResponse>
    @POST("/car/getUserBdata")
    fun getUserB(@Body userB: userB): Call<ConstatResponseUserB>
    @POST("/constat/sendReport")
    fun sendReport(@Body sendReportBody: sendReportBody) : Call<MailResponse>
}
package com.example.econstat_android.Services

import com.example.econstat_android.Model.Insurance
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ConstatService {
    data class ConstatResponse (
        @SerializedName("constat")
        val insurance : Insurance
    )
    data class constatBody(
        val insurance : String,
        val carDamage : String
    )

    @POST("/constat/addNew")
    fun NewConstat(@Body constatBody: constatBody): Call<ConstatResponse>
}
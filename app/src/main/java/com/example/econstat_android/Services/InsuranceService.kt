package com.example.econstat_android.Services

import com.example.econstat_android.Model.Insurance
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.*

interface InsuranceService {
    data class InsuranceResponse (
        @SerializedName("insurance")
        val insurance : Insurance
    )
    data class insurancePostBody(
        val name: String,
        val numContrat: String,
        val agency: String,
        val validityFrom: String,
        val validityTo: String,
        val carId: String
    )
    data class getInsurancePostBody(
        val cars : String?
    )

    @POST("/insurance/addNew")
    fun newInsurance(@Body insurancePostBody: insurancePostBody) : Call<InsuranceResponse>

    @POST("/insurance/getAll")
    fun getInsurance(@Body getInsurancePostBody: getInsurancePostBody) : Call<InsuranceResponse>
}
package com.example.econstat_android.Services
import com.example.econstat_android.Model.Car
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call


interface CarService {

    data class CarResponse(

        val cars: List<Car>
    )

    data class CarBody(
        val brand: String,
        val type: String,
        val numSerie: String,
        val fiscalPower: Number,
        val numImmatriculation: String,
        val token:String
    )
    data class GetAllCarsBody(
        val token: String
    )

    ////////////////////////////////////////////////////////////////////////
    @POST("/car/addNew")
    fun addCar(@Body carBody: CarBody): Call<CarResponse>

    @POST("/car/allCars")
     fun getCars(@Body getAllCarsBody: GetAllCarsBody ): Call<CarResponse>

}
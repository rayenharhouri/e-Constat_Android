package com.example.econstat_android.Services
import com.example.econstat_android.Model.Car
import com.example.econstat_android.Model.CarDamage
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call


interface CarService {

    data class CarResponse(
        val cars: List<Car>
    )
    data class CarDamageResponse(
        val carDamage: CarDamage
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
    data class CarDamageAbody(
        val TopLeft : Boolean,
        val MidLeft : Boolean,
        val BottomLeft : Boolean,
        val TopRight :  Boolean,
        val MidRight : Boolean,
        val BottomRight : Boolean,
        val carId : String
    )
    data class CarDamageBbody(
        val TopLeft : Boolean,
        val MidLeft : Boolean,
        val BottomLeft : Boolean,
        val TopRight :  Boolean,
        val MidRight : Boolean,
        val BottomRight : Boolean,
        val carId : String
    )
    ////////////////////////////////////////////////////////////////////////
    @POST("/car/addNew")
    fun addCar(@Body carBody: CarBody): Call<CarResponse>

    @POST("/car/allCars")
     fun getCars(@Body getAllCarsBody: GetAllCarsBody ): Call<CarResponse>
    @POST("/carDamage/addNew")
    fun addCarDamageA(@Body carDamageAbody: CarDamageAbody): Call<CarDamageResponse>
    @POST("/carDamage/addNew")
    fun addCarDamageB(@Body carDamageBbody: CarDamageBbody): Call<CarDamageResponse>
}
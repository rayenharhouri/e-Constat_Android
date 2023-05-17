package com.example.econstat_android.ViewModel

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.econstat_android.Model.Car
import com.example.econstat_android.Model.Insurance
import com.example.econstat_android.Model.User
import com.example.econstat_android.R
import com.example.econstat_android.Services.ApiService
import com.example.econstat_android.Services.InsuranceService
import com.example.econstat_android.fragments.insuranceFromFragment
import com.example.econstat_android.utils.Constant
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarDetailsFragment : Fragment() {


    private lateinit var car: Car
    private lateinit var tvMatriculation: TextView
    private lateinit var tvbrand: TextView
    private lateinit var tvFiscalPower: TextView
    private lateinit var tvType: TextView
    private lateinit var tvNumSerie: TextView
    private lateinit var imageCar: ImageView
    private lateinit var btn_qr: ImageButton
    var qrGenerated = false
    var insuranceId = ""








    override fun onCreate( savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        car = arguments?.getParcelable<Car>("car")!!

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //INIT
        getInsurance()

        val    car = arguments?.getParcelable<Car>("car")!!

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_details, container, false)

    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_qr = view.findViewById(R.id.btn_qr)

        // Access the TextViews
        imageCar = view.findViewById(R.id.ivItemCar)

        tvMatriculation = view.findViewById(R.id.tvItemMatricule)
        tvbrand = view.findViewById(R.id.tvItemBrand)
        tvFiscalPower = view.findViewById(R.id.tvItemFiscalPower)
        tvType = view.findViewById(R.id.tvItemType)
        tvNumSerie = view.findViewById(R.id.tvItemNumSerie)

        // Set the text of the TextViews with the car data
        Picasso.get().load(Constant.image_URL + car.carImage).into(imageCar)


        tvMatriculation.text = car.numImmatriculation
        tvbrand.text = car.brand
        tvFiscalPower.text = car.fiscalPower.toString()
        tvType.text = car.type
        tvNumSerie.text = car.numSerie


        btn_qr.setOnClickListener {
            if (!qrGenerated) {
                Picasso.get().load(Constant.image_URL + car.carImage).into(imageCar)
                qrGenerated = true
            } else {
                var qrCodeContent = car._id + getUserData()._id + insuranceId
                generateQrCode(qrCodeContent)
                qrGenerated = false
            }
        }
        //Insurance
        val fragment = insuranceFromFragment.newInstance(car._id)
        fragmentManager?.beginTransaction()!!
            .replace(R.id.fragment_container,fragment)
            .commit()
    }



    private fun getScreenMetrics(): Pair<Int, Int> {
        val displayMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = requireActivity().windowManager.currentWindowMetrics

            val insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(
                WindowInsets.Type.systemBars() or WindowInsets.Type.displayCutout()
            )
            displayMetrics.widthPixels =
                windowMetrics.bounds.width() - insets.left - insets.right
            displayMetrics.heightPixels =
                windowMetrics.bounds.height() - insets.top - insets.bottom
            displayMetrics.density = Resources.getSystem().displayMetrics.density
        } else {
            @Suppress("DEPRECATION")
            val display = requireActivity().windowManager.defaultDisplay
            @Suppress("DEPRECATION")
            display.getMetrics(displayMetrics)
        }
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        return Pair(height, width)
    }

    private fun generateQrCode(qrcodeString: String) {
        val barcodeEncoder = BarcodeEncoder()
        val bitmap =
            barcodeEncoder.encodeBitmap(
                qrcodeString,
                BarcodeFormat.QR_CODE,
                getScreenMetrics().second,
                getScreenMetrics().first / 2
            )
        imageCar.setImageBitmap(bitmap)

    }
    private fun getUserData(): User {
        val sharedPreferences =
            requireContext().getSharedPreferences(
                Constant.SHARED_PREF_SESSION,
                Context.MODE_PRIVATE
            )
        val userData = sharedPreferences.getString("USER_DATA", "")
        return Gson().fromJson(userData, User::class.java)
    }
    fun getInsurance() {
        ApiService.insuranceService.getInsurance(
            InsuranceService.getInsurancePostBody(
                car._id
            )
        ).enqueue( object : Callback<InsuranceService.InsuranceResponse> {
            override fun onResponse(
                call: Call<InsuranceService.InsuranceResponse>,
                response: Response<InsuranceService.InsuranceResponse>
            ) {
                if (response.code() == 200) {
                    try {
                        val json = Gson().toJson(response.body()!!.insurance)
                        val insurance = Gson().fromJson(json, Insurance::class.java)
                        insuranceId = insurance._id
                    } catch (e: Exception) {
                        println(e.stackTrace)
                    }

                } else if (response.code() != 200) {
                    println("Error occured: "+response.code())
                }

                else {
                    println("status code is " + response.code())
                }
            }

            override fun onFailure(call: Call<InsuranceService.InsuranceResponse>, t: Throwable) {
                println("HTTP ERROR")
                t.printStackTrace()}
        })
    }

}
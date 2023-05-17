package com.example.econstat_android.ViewModel.QrCode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.econstat_android.Model.Car
import com.example.econstat_android.Model.Constat
import com.example.econstat_android.Model.Insurance
import com.example.econstat_android.Model.User
import com.example.econstat_android.R
import com.example.econstat_android.Services.ApiService
import com.example.econstat_android.Services.ConstatService
import com.example.econstat_android.databinding.ActivityQrCodeBinding
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QrCodeScanner : AppCompatActivity() {

	private lateinit var binding: ActivityQrCodeBinding
	private var qrScanIntegrator: IntentIntegrator? = null
	var reportId : String = ""


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityQrCodeBinding.inflate(layoutInflater)
		val view = binding.root
		supportActionBar?.hide()
		reportId = intent.getStringExtra("reportId").toString()
		setContentView(view)
		setOnClickListener()
		setupScanner()
	}

	private fun setupScanner() {
		qrScanIntegrator = IntentIntegrator(this)
		qrScanIntegrator?.setOrientationLocked(true)
	}

	private fun setOnClickListener() {
		binding.btnScan.setOnClickListener { performAction() }

	}

	private fun performAction() {
		// Code to perform action when button is clicked.
		qrScanIntegrator?.initiateScan()
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
		if (result != null) {
			// If QRCode has no data.
			if (result.contents == null) {
				Toast.makeText(this, getString(R.string.result_not_found), Toast.LENGTH_LONG).show()
			} else {
				// If QRCode contains data.
					println(result.contents)
					getUserBData(result.contents)
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data)
		}
	}
	fun getUserBData(ids: String){
		ApiService.constatService.getUserB(
			ConstatService.userB(ids)
		).enqueue(
			object : Callback<ConstatService.ConstatResponseUserB> {
				override fun onResponse(
					call: Call<ConstatService.ConstatResponseUserB>,
					response: Response<ConstatService.ConstatResponseUserB>
				) {
					if (response.code() == 200) {

						var carJson = Gson().toJson(response.body()!!.car)
						var car = Gson().fromJson(carJson, Car::class.java)
						var userJson = Gson().toJson(response.body()!!.user)
						var user = Gson().fromJson(userJson, User::class.java)
						var insuranceJson = Gson().toJson(response.body()!!.insurance)
						var insurance = Gson().fromJson(insuranceJson, Insurance::class.java)
						constatB(insurance._id,user._id,car._id,reportId)
						sendReport(user.email)

					} else if (response.code() == 409) {
						println(response.code())
					}
					else if (response.code() == 400) {
						println(response.code())
					}
					else {
						println("status code is " + response.code())
					}
				}

				override fun onFailure(call: Call<ConstatService.ConstatResponseUserB>, t: Throwable) {
					println("HTTP ERROR")
					t.printStackTrace()
				}
			}
		)
	}
	fun constatB(insurance: String,userB: String,carId : String,constatId:String) {
		ApiService.constatService.NewConstatB(
			ConstatService.constatBBody(constatId,userB,carId,insurance)
		).enqueue(
			object : Callback<ConstatService.ConstatResponse> {
				override fun onResponse(
					call: Call<ConstatService.ConstatResponse>,
					response: Response<ConstatService.ConstatResponse>
				) {
					if (response.code() == 200) {

					} else if (response.code() == 409) {
					}
					else if (response.code() == 400) {
					}
					else {
						println("status code is " + response.code())
					}
				}
				override fun onFailure(call: Call<ConstatService.ConstatResponse>, t: Throwable) {
					println("HTTP ERROR")
					t.printStackTrace()
				}
			}
		)
	}
	fun sendReport(email:String){
		ApiService.constatService.sendReport(ConstatService.sendReportBody(email)).enqueue(
			object : Callback<ConstatService.MailResponse> {
				override fun onResponse(
					call: Call<ConstatService.MailResponse>,
					response: Response<ConstatService.MailResponse>
				)
				{
					if (response.code() == 200) {

					}
				}

				override fun onFailure(call: Call<ConstatService.MailResponse>, t: Throwable) {
					println("HTTP ERROR")
					t.printStackTrace()
				}

			}
		)
	}

}

package com.example.econstat_android.ViewModel.QrCode

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.econstat_android.R
import com.google.zxing.integration.android.IntentIntegrator
import com.example.econstat_android.databinding.FragmentQrcodeBinding
import org.json.JSONException
import org.json.JSONObject

class QRCodeFragment : Fragment() {

	private lateinit var binding: FragmentQrcodeBinding
	private lateinit var qrScanIntegrator: IntentIntegrator

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentQrcodeBinding.inflate(layoutInflater)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setupScanner()
		setOnClickListener()
	}

	private fun setupScanner() {
		qrScanIntegrator = IntentIntegrator.forSupportFragment(this)
		qrScanIntegrator.setOrientationLocked(false)
	}

	private fun setOnClickListener() {
		binding.btnScan.setOnClickListener { performAction() }
	}

	private fun performAction() {
		// Code to perform action when button is clicked.
		qrScanIntegrator.initiateScan()
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
		if (result != null) {
			// If QRCode has no data.
			if (result.contents == null) {
				Toast.makeText(activity, R.string.result_not_found, Toast.LENGTH_LONG).show()
			} else {
				// If QRCode contains data.
				try {
					// Converting the data to json format
					val obj = JSONObject(result.contents)

					// Show values in UI.
					binding.name.text = obj.getString("name")
					binding.siteName.text = obj.getString("site_name")

				} catch (e: JSONException) {
					e.printStackTrace()

					// Data not in the expected format. So, whole object as toast message.
					Toast.makeText(activity, result.contents, Toast.LENGTH_LONG).show()
				}
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data)
		}
	}
}

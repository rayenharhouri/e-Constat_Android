package com.example.econstat_android.ViewModel.QrCode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.econstat_android.R
import com.example.econstat_android.databinding.ActivityHelperBinding

class HelperActivity : AppCompatActivity() {

	private lateinit var binding: ActivityHelperBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityHelperBinding.inflate(layoutInflater)
		val view = binding.root
		setContentView(view)

	}


}

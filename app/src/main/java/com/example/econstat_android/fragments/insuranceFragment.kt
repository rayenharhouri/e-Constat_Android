package com.example.econstat_android.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.example.econstat_android.Model.Insurance
import com.example.econstat_android.R
import com.squareup.picasso.Picasso

class insuranceFragment : Fragment() {
private lateinit var insurancePic : ImageView
    private var insuranceImage: String? = null

    companion object {
        private const val ARG_INSURANCE = "insuranceImage"
        fun newInstance(insuranceImage: String): insuranceFragment {
            val fragment = insuranceFragment()
            val args = Bundle()
            args.putString(ARG_INSURANCE, insuranceImage)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //insurance image from parameter
        arguments?.let {
            insuranceImage = it.getString(insuranceFragment.ARG_INSURANCE)
        }
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_insurance_layout, container, false)
        //INIT
        insurancePic = view.findViewById(R.id.insurancePic)
        //VAR
        val picasso = Picasso.get()
        println(insuranceImage)
        picasso.load(insuranceImage)
            .placeholder(R.drawable.giphy)
            .error(R.drawable.giphy)
            .fit()
            .centerCrop()
            .into(insurancePic)
        return view
    }


}
package com.example.econstat_android.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.econstat_android.R
import com.example.econstat_android.ViewModel.HomeCarFragment
import com.example.econstat_android.ViewModel.carA_DamageActivity


class homeFragment : Fragment() {
    private lateinit var reportBtn : Button
    private var isReport : Boolean = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        //INIT
        reportBtn = view.findViewById(R.id.newReport)

        //Start Activity
        reportBtn.setOnClickListener{
            val fragment = HomeCarFragment()
            val args = Bundle()
            args.putBoolean("isReport", isReport)
            fragment.setArguments(args)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView,fragment)
                .commit()
            }
        return view
    }

}
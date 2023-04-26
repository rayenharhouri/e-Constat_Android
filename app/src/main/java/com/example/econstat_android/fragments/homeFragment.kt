package com.example.econstat_android.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.econstat_android.R
import com.example.econstat_android.ViewModel.carA_DamageActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var reportBtn : Button
class homeFragment : Fragment() {
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
            val intent = Intent(requireContext(),carA_DamageActivity::class.java)
            startActivity(intent)
        }
        return view
    }

}
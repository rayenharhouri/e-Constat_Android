package com.example.econstat_android.ViewModel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.econstat_android.Model.User
import com.example.econstat_android.R
import com.example.econstat_android.Services.ApiService
import com.example.econstat_android.Services.CarService
import com.example.econstat_android.utils.Constant
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeCarFragment : Fragment() {
    private lateinit var rvCars: RecyclerView
    private lateinit var carAdapter: CarAdapter
    private lateinit var add_car_btn: ImageButton
    private var isReport: Boolean = false
    private  lateinit var txt : TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val fragmentManager = requireFragmentManager()
        val rootView = inflater.inflate(R.layout.fragment_home_car, container, false)

        ////////////////////////////////////////////////
        val sharedPreferences =
            requireContext().getSharedPreferences(Constant.SHARED_PREF_SESSION, Context.MODE_PRIVATE)
        val userData = sharedPreferences.getString("USER_DATA", "")
        val user = Gson().fromJson(userData, User::class.java)
        txt = rootView.findViewById(R.id.manage_your_cars_insurance)
        arguments?.let {
            isReport = it.getBoolean("isReport")
        }
        println(isReport)
        ApiService.carService.getCars(
            CarService.GetAllCarsBody(
                user.token
            )
        ).enqueue(object : Callback<CarService.CarResponse> {

            override fun onResponse(call: Call<CarService.CarResponse>, response: Response<CarService.CarResponse>) {
                if (response.code() == 200) {
                    val cars= response.body()?.cars
                    if (cars != null) {
                        carAdapter.cars =cars // update the adapter with the retrieved cars
                        carAdapter.notifyDataSetChanged() // notify the adapter that the data has changed
                    }
                } else {
                    Log.e(TAG, "Failed to get cars: ${response.code()}")
                }            }

            override fun onFailure(call: Call<CarService.CarResponse>, t: Throwable) {
                Log.e(TAG, "Failed to get cars", t)
            }
        })

        add_car_btn = rootView.findViewById(R.id.add_car)
        rvCars = rootView.findViewById(R.id.rv)
        rvCars.layoutManager = LinearLayoutManager(requireContext())
        carAdapter = CarAdapter(requireContext(),listOf(),fragmentManager,isReport) // create an empty adapter
        rvCars.adapter = carAdapter
        if(isReport) {
            add_car_btn.visibility = View.GONE
            txt.text = "Select the Damaged Car"
        }
        add_car_btn.setOnClickListener {
            if (!isReport) {
                navigateToAddCar(fragmentManager)
            }
        }
        return rootView
    }

    private fun navigateToCarDamage() {
        val intent = Intent(requireContext(),carA_DamageActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun navigateToAddCar(fragmentManager: FragmentManager) {

        val AddCarFragment = NewCarFragment()

        fragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, AddCarFragment)
            addToBackStack(null)
            commit()
        }
    }

    companion object {
        private const val TAG = "car"
    }
}






package com.example.econstat_android.ViewModel

import com.example.econstat_android.Services.ApiService
import com.example.econstat_android.Services.CarService
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.econstat_android.MainActivity
import com.example.econstat_android.Model.User
import com.example.econstat_android.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.econstat_android.utils.Constant
import com.google.gson.Gson

class NewCarFragment : Fragment() {
    private lateinit var spinnerBrand: Spinner
    private lateinit var spinnerType: Spinner
        private lateinit var etFiscalPower: TextInputEditText
    private lateinit var lytFiscalPower: TextInputLayout
    private lateinit var etNumSerie: TextInputEditText
    private lateinit var lytNumSerie: TextInputLayout
    private lateinit var etNumImmatriculation1: TextInputEditText
    private lateinit var lytNumImmatriculation1: TextInputLayout
    private lateinit var etNumImmatriculation2: TextInputEditText
    private lateinit var lytNumImmatriculation2: TextInputLayout
    private lateinit var doneBtn: ImageButton
    private lateinit var brand: String
    private lateinit var type: String
    private lateinit var numImmatriculation: String
    lateinit var contextt: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contextt = requireContext()
        val sharedPreferences =
            contextt.getSharedPreferences(Constant.SHARED_PREF_SESSION, Context.MODE_PRIVATE)
        return inflater.inflate(R.layout.fragment_new_car, container, false)
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
        spinnerBrand = view.findViewById(R.id.spinner_brand)
            spinnerType = view.findViewById(R.id.spinner_type)
        etFiscalPower = view.findViewById(R.id.et_fiscal_power)
        lytFiscalPower = view.findViewById(R.id.lyt_fiscal_power)
        etNumSerie = view.findViewById(R.id.et_num_serie)
        lytNumSerie = view.findViewById(R.id.lyt_num_serie)
        etNumImmatriculation1 = view.findViewById(R.id.et_numImmatriculation1)
        lytNumImmatriculation1 = view.findViewById(R.id.lyt_numImmatriculation1)
        etNumImmatriculation2 = view.findViewById(R.id.et_numImmatriculation2)
        lytNumImmatriculation2 = view.findViewById(R.id.lyt_numImmatriculation2)
        doneBtn = view.findViewById(R.id.done_btn)

        val brands = arrayOf(
            "Alfa Romeo",
            "Audi",
            "Bentley",
            "BMW",
            "Brabus",
            "Chery",
            "Chevrolet",
            "Chrysler",
            "Citroen"
        )
        val types = arrayOf("essence", "diesel")
        val adapterBrands =
            ArrayAdapter(contextt, android.R.layout.simple_expandable_list_item_1, brands)
        val adapterTypes = ArrayAdapter(contextt, android.R.layout.simple_list_item_1, types)
        spinnerBrand.adapter = adapterBrands
        spinnerType.adapter = adapterTypes
        spinnerBrand.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = brands[position]
                Toast.makeText(requireContext(), "selectedItem: $selectedItem", Toast.LENGTH_SHORT).show()
                brand = "$selectedItem"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // TODO("Not yet implemented")
            }
        }

        val spinnerType = view.findViewById<Spinner>(R.id.spinner_type)
        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = types[position]
                Toast.makeText(requireContext(), "selectedItem: $selectedItem", Toast.LENGTH_SHORT).show()
                type = "$selectedItem"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // TODO("Not yet implemented")
            }
        }

        doneBtn.setOnClickListener {
            val numImmatriculation = etNumImmatriculation1.text.toString() + " TUN " + etNumImmatriculation2.text.toString()
            if (validateInput()) {
                //////////////////////start
                val sharedPreferences =
                    requireContext().getSharedPreferences(Constant.SHARED_PREF_SESSION, Context.MODE_PRIVATE)
                val userData = sharedPreferences.getString("USER_DATA", "")
                val user = Gson().fromJson(userData, User::class.java)
                ApiService.carService.addCar(
                    CarService.CarBody(
                        brand,
                        type,
                        etNumSerie.text.toString(),
                        etFiscalPower.text.toString().toInt(),
                        numImmatriculation,
                        user.token
                    )
                )
                    .enqueue(object : Callback<CarService.CarResponse> {
                        override fun onResponse(
                            call: Call<CarService.CarResponse>,
                            response: Response<CarService.CarResponse>
                        ) {
                            if (response.code() == 200) {
                                val intent = Intent(activity, MainActivity::class.java)
                                startActivity(intent)
                                activity?.finish()
                                println("status code is " + response.code())
                                showDialog(contextt, "Car Added ")
                            } else if (response.code() == 409) {
                                showDialog(contextt, "Car Exist ")
                            } else {
                                println("status code is " + response.code())
                            }
                        }

                        override fun onFailure(call: Call<CarService.CarResponse>, t: Throwable) {
                            println("HTTP ERROR")
                            t.printStackTrace()
                        }
                    })
            }
        }








        ///////////////////////////////////////////////////////////////////////:

    }


    ////////////////////////////////////////////////////////
    private fun validateInput(): Boolean {
        //Vérifier si les champs des 3 EditText ne sont pas vides
        if (setError(etFiscalPower, getString(R.string.must_not_be_empty))||setError(etNumSerie, getString(
                R.string.must_not_be_empty
            ))||setError(etNumImmatriculation1, getString(R.string.must_not_be_empty))||setError(etNumImmatriculation2, getString(
                R.string.must_not_be_empty
            ))) {
            Toast.makeText(requireContext(), R.string.must_not_be_empty, Toast.LENGTH_SHORT).show()
            return false
        } else {
            return  true

        }
    }




    private fun setError(et: TextInputEditText, errorMsg: String): Boolean {
        if(et.text?.isEmpty() == true){
            (et.parent.parent as TextInputLayout).isErrorEnabled = true
            (et.parent.parent as TextInputLayout).error = errorMsg
            return true
        }else{
            (et.parent.parent as TextInputLayout).isErrorEnabled = false
            return false
        }
    }




    fun showDialog(activityName:Context,message:String){
        val builder = AlertDialog.Builder(activityName)
        builder.setTitle("Caution ⚠️")
        builder.setMessage(message)
        builder.setPositiveButton("OK", null)
        val dialog = builder.create()
        dialog.show()
    }
}
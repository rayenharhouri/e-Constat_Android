package com.example.econstat_android.fragments

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import com.example.econstat_android.Model.Insurance
import com.example.econstat_android.R
import com.example.econstat_android.Services.ApiService
import com.example.econstat_android.Services.InsuranceService
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class insuranceFromFragment : Fragment() {
    private var carId: String? = null

    companion object {
        private const val ARG_CAR_ID = "carId"
        fun newInstance(carId: String): insuranceFromFragment {
            val fragment = insuranceFromFragment()
            val args = Bundle()
            args.putString(ARG_CAR_ID, carId)
            fragment.arguments = args
            return fragment
        }
    }
    private lateinit var nameEt : TextInputEditText
    private lateinit var nameLyt : TextInputLayout
    private lateinit var agencyEt : TextInputEditText
    private lateinit var agencyLyt : TextInputLayout
    private lateinit var validityFromEt : TextInputEditText
    private lateinit var validityFromLyt : TextInputLayout
    private lateinit var validityToEt : TextInputEditText
    private lateinit var validityToLyt : TextInputLayout
    private lateinit var contractEt : TextInputEditText
    private lateinit var contractLyt : TextInputLayout
    private lateinit var submitBtn : Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Getting car id from parameter
        arguments?.let {
            carId = it.getString(ARG_CAR_ID)
        }
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_insurance_from, container, false)
        //INIT
        val activity = requireActivity()
        val context = requireContext()
        setFullScreen(activity)
        nameEt = view.findViewById(R.id.et_insuranceName)
        nameLyt = view.findViewById(R.id.lyt_insuranceName)
        agencyEt = view.findViewById(R.id.et_agency)
        agencyLyt = view.findViewById(R.id.lyt_agency)
        validityFromEt = view.findViewById(R.id.et_validityFrom)
        validityFromLyt = view.findViewById(R.id.lyt_validityFrom)
        validityToEt = view.findViewById(R.id.et_validityTo)
        validityToLyt = view.findViewById(R.id.lyt_validityTo)
        contractEt = view.findViewById(R.id.et_Contract)
        contractLyt = view.findViewById(R.id.lyt_Contract)
        submitBtn = view.findViewById(R.id.btnSubmit)
        //ClickListener To Open the date picker
        var validityState: Boolean
        validityFromEt.setOnClickListener {
            validityState = false
            showDatePickerDialog(validityState)
        }
        validityToEt.setOnClickListener {
            validityState = true
            showDatePickerDialog(validityState)
        }

        //Getting insurance if Exist
        getInsurance()

        //Request
        submitBtn.setOnClickListener {
            ApiService.insuranceService.newInsurance(
                InsuranceService.insurancePostBody(
                    nameEt.text.toString(),
                    contractEt.text.toString(),
                    agencyEt.text.toString(),
                    validityFromEt.text.toString(),
                    validityToEt.text.toString(),
                    carId.toString()
                )
            ).enqueue( object : Callback<InsuranceService.InsuranceResponse> {
                override fun onResponse(
                    call: Call<InsuranceService.InsuranceResponse>,
                    response: Response<InsuranceService.InsuranceResponse>
                ) {
                    if (response.code() == 200) {
                        getInsurance()
                    }
                    else if(response.code() == 400) {
                        showDialog(context,"All Fields are required")
                    }
                    else if(response.code() == 409) {
                        showDialog(context,"Contract Already exist")
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

     return view
    }

    private val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, monthOfYear)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateString = dateFormat.format(calendar.time)
    }
    private fun showDatePickerDialog(state : Boolean) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                if (!state) {
                    validityFromEt.setText("$dayOfMonth/$monthOfYear/$year")
                }else {
                    validityToEt.setText("$dayOfMonth/$monthOfYear/$year")
                }
            },
            year,
            month,
            dayOfMonth
        )

        datePickerDialog.show(parentFragmentManager, "datePickerDialog")
    }
    fun showDialog(activityName: Context, message:String){
        val builder = AlertDialog.Builder(activityName)
        builder.setTitle("Caution ⚠️")
        builder.setMessage(message)
        builder.setPositiveButton("OK", null)
        val dialog = builder.create()
        dialog.show()
    }
    fun setFullScreen(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            activity.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }
    fun getInsurance(){
        ApiService.insuranceService.getInsurance(
            InsuranceService.getInsurancePostBody(
                carId
            )
        ).enqueue( object : Callback<InsuranceService.InsuranceResponse> {
            override fun onResponse(
                call: Call<InsuranceService.InsuranceResponse>,
                response: Response<InsuranceService.InsuranceResponse>
            ) {
                if (response.code() == 200) {
                    val json = Gson().toJson(response.body()!!.insurance)
                    val insurance = Gson().fromJson(json,Insurance::class.java)
                    println(insurance)
                    if(!(insurance?.image.isNullOrBlank())){
                        val transaction = parentFragmentManager.beginTransaction()
                        transaction.replace(R.id.fragmentContainerView, insuranceFragment.newInstance(insurance.image))
                        transaction.commit()
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

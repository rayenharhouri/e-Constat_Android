package com.example.econstat_android.ViewModel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.econstat_android.Model.Car
import com.example.econstat_android.R
import com.example.econstat_android.databinding.CarItemBinding

import com.example.econstat_android.utils.Constant
import de.hdodenhof.circleimageview.CircleImageView

class CarAdapter(private val context: Context,var cars: List<Car>,private val fragmentManager: FragmentManager,private val isReport:Boolean) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {

        val mainView = CarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(mainView)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]

        Glide.with(holder.itemView.context)
            .load(Constant.image_URL + car.carImage)
            .into(holder.itemView.findViewById<CircleImageView>(R.id.ivCar))

        val tvMatricule = holder.itemView.findViewById<TextView>(R.id.tvMatricule)
        tvMatricule.text = car.numImmatriculation

        if(isReport){
            holder.itemView.setOnClickListener()
            {
                navigateToCarDamage(car,context)
                Toast.makeText(holder.itemView.context, "clicked", Toast.LENGTH_SHORT).show()
            }
        } else {
            holder.itemView.setOnClickListener()
            {
                navigateToCarDetails(car, fragmentManager)
                Toast.makeText(holder.itemView.context, "clicked", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun getItemCount(): Int {
        return cars.size
    }


    inner class CarViewHolder(mainView: CarItemBinding) : RecyclerView.ViewHolder(mainView.root) {


    }

    private fun navigateToCarDetails(car: Car, fragmentManager: FragmentManager) {
        val bundle = Bundle().apply {
            putParcelable("car", car)
        }
        val carDetailsFragment = CarDetailsFragment()
        carDetailsFragment.arguments = bundle
        fragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, carDetailsFragment)
            addToBackStack(null)
            commit()
        }
    }
    private fun navigateToCarDamage(car: Car,context: Context) {
        val intent = Intent(context, carA_DamageActivity::class.java)
        intent.putExtra("car", car._id)
        context.startActivity(intent)
    }
}







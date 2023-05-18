package com.example.econstat_android.Model

import java.io.Serializable
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car(
    val _id:String,
    val brand: String,
    val type: String,
    val numSerie: String,
    val fiscalPower: Int,
    val numImmatriculation: String,
    val carImage: String,
    val token:String

): Serializable, Parcelable

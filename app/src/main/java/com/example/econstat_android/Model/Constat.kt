package com.example.econstat_android.Model

import java.io.Serializable

data class Constat(
    val _id : String,
    val UserA : String,
    val UserB : String,
    val CarDamageA : String,
    val CarDamageB : String,
    val CarA : String,
    val carB : String,
    val InsuranceA : String,
    val InsuranceB : String
) : Serializable

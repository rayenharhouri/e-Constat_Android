package com.example.econstat_android.Model

import java.io.Serializable

data class Constat(
    val _id : String,
    val userA : String,
    val userB : String,
    val carDamageA : String,
    val carDamageB : String,
    val carA : String,
    val carB : String,
    val InsuranceA : String,
    val InsuranceB : String
) : Serializable

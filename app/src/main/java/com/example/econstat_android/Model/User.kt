package com.example.econstat_android.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import java.util.*
@Parcelize
data class User(
        val _id: String,
        val email: String,
        val password: String,
        val name: String,
        val lastName: String,
        val address: Date,
        val driverLicense: String,
        val delevredOn: String,
        val number: String,
        val token: String
    ) : Serializable, Parcelable

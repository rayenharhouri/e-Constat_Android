package com.example.econstat_android

import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import com.example.econstat_android.Services.ApiService
import com.example.econstat_android.Services.InsuranceService
import com.example.econstat_android.ViewModel.profileActivity
import com.example.econstat_android.fragments.SettingsFragment
import com.example.econstat_android.fragments.homeFragment
import com.example.econstat_android.fragments.insuranceFromFragment
import com.example.econstat_android.utils.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setFullScreen(this@MainActivity)
        setContentView(R.layout.activity_main)
        sessionManager = SessionManager(this)
        sessionManager.isLoggedIn = true
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    val fragment = homeFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .commit()
                    true
                }
                R.id.navigation_car -> {
                    val fragment = insuranceFromFragment.newInstance("643ddcad80b80bd0af1ce13f")
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView,fragment)
                        .commit()
                    true
                }
                R.id.navigation_profile -> {
                    val fragment = profileActivity()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .commit()
                    true
                }
                R.id.navigation_settings -> {
                    val fragment = SettingsFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .commit()
                    true
                }
                else -> false
            }
        }


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

}
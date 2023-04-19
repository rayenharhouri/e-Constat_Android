package com.example.econstat_android.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.InsetDrawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.MenuRes
import android.util.TypedValue
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.econstat_android.MainActivity
import com.example.econstat_android.R
import com.example.econstat_android.ViewModel.LoginActivity
import com.example.econstat_android.utils.SessionManager
import com.google.android.material.switchmaterial.SwitchMaterial


class SettingsFragment : Fragment() {

    private lateinit var sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_settings, container, false)
        val darkModeSwitch = view.findViewById<SwitchMaterial>(R.id.darkModeSwitch)

        //DARKMode Switch
        darkModeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            val backgroundColor = if (isChecked) {
                R.color.dark_background_color
            } else {
                R.color.light_background_color
            }
            view.setBackgroundColor(ContextCompat.getColor(requireContext(), backgroundColor))
        }
        //Language Menu
        val button = view.findViewById<Button>(R.id.menu_button)
        button.setOnClickListener { v: View ->
            showMenu(v, R.menu.popup_menu)
        }
        //General settings Section
        val expandButton = view.findViewById<Button>(R.id.expandButton)
        val expandSection = view.findViewById<LinearLayout>(R.id.expandSection)
        expandButton.setOnClickListener {
            if (expandSection.visibility == View.VISIBLE) {
                expandSection.visibility = View.GONE
            } else {
                expandSection.visibility = View.VISIBLE
            }
        }
        //Terms and conditions Section
        val expandButtonTermsAndCondition = view.findViewById<Button>(R.id.expandButtonTermsAndCondition)
        expandButtonTermsAndCondition.setOnClickListener {
            val backFragment = TermsAndConditionFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up, R.anim.slide_in_down, R.anim.slide_out_down)
                .replace(R.id.fragmentContainerView, backFragment)
                .addToBackStack(null)
                .commit()
        }
        //logout
        val logoutBtn = view.findViewById<Button>(R.id.logout)
        logoutBtn.setOnClickListener {
            val sharedPref: SharedPreferences =
                requireActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.clear()
            editor.apply()
            sessionManager = SessionManager(requireContext())
            sessionManager.isLoggedIn = false
            val intent = Intent(requireContext(),LoginActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    @SuppressLint("RestrictedApi")
    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val ICON_MARGIN = 16
        val popup = PopupMenu(requireContext()!!, v)
        popup.menuInflater.inflate(menuRes, popup.menu)
        if (popup.menu is MenuBuilder) {
            val menuBuilder = popup.menu as MenuBuilder
            menuBuilder.setOptionalIconsVisible(true)
            for (item in menuBuilder.visibleItems) {
                val iconMarginPx =
                    TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, ICON_MARGIN.toFloat(), resources.displayMetrics)
                        .toInt()
                if (item.icon != null) {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                        item.icon = InsetDrawable(item.icon, iconMarginPx, 0, iconMarginPx,0)
                    } else {
                        item.icon =
                            object : InsetDrawable(item.icon, iconMarginPx, 0, iconMarginPx, 0) {
                                override fun getIntrinsicWidth(): Int {
                                    return intrinsicHeight + iconMarginPx + iconMarginPx
                                }
                            }
                    }
                }
            }
        }
        val gravity = Gravity.END
        popup.gravity = GravityCompat.getAbsoluteGravity(gravity, resources.configuration.layoutDirection)
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }


}
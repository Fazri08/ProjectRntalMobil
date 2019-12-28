package com.android.rentalmobil.Customer.Activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.android.rentalmobil.Customer.Fragment.FragmentBooking
import com.android.rentalmobil.Customer.Fragment.FragmentCar
import com.android.rentalmobil.Customer.Fragment.FragmentProfile
import com.android.rentalmobil.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main_bottom.*

class MainBottom: AppCompatActivity() {

    lateinit var fragmentCar: FragmentCar
    lateinit var fragmentBooking: FragmentBooking
    lateinit var fragmentProfile: FragmentProfile


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_bottom)

        bn_main.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.bookcar -> {
                    fragmentCar = FragmentCar()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, fragmentCar)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }


                R.id.cart -> {
                    fragmentBooking = FragmentBooking()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, fragmentBooking)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.account_menu -> {
                    fragmentProfile = FragmentProfile()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, fragmentProfile)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

            }
            true
        }
    }
}
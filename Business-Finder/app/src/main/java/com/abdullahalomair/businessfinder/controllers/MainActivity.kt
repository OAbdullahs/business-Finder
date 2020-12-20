package com.abdullahalomair.businessfinder.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.ActivityMainBinding
import com.abdullahalomair.businessfinder.viewmodels.MainFragmentViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setContentView(mainBinding.root)


        val fragment = supportFragmentManager.findFragmentById(R.id.main_fragment_manager)
        if (fragment == null){
            val newFragment = MainFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.main_fragment_manager,newFragment)
                .commit()
        }

    }

}
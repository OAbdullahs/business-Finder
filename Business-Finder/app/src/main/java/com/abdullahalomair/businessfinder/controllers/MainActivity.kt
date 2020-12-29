package com.abdullahalomair.businessfinder.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.callbacks.CallBacks
import com.abdullahalomair.businessfinder.databinding.ActivityMainBinding
import com.abdullahalomair.businessfinder.model.navigator.Navigator
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.ForeCastDay
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses

private const val PLANS_LIST = "plan_list"
class MainActivity : AppCompatActivity(), CallBacks {
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(mainBinding.root)


        val fragment = supportFragmentManager.findFragmentById(R.id.main_fragment_manager)
        if (fragment == null) {
            val newFragment = MainFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.main_fragment_manager, newFragment)
                .commit()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.plans_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.plans_list_menu_item -> {
                val plansBottomSheet = PlansBottomSheet()
                plansBottomSheet.show(this.supportFragmentManager,PLANS_LIST)
                return true
            }
        }
        return true
    }

    override fun applicationNavigator(
        navigator: Navigator,
        businesses: Businesses?,
        weatherModel: ForeCastDay?,
        cityName: String?
    ) {
        when (navigator) {
            Navigator.BUSINESS_DETAILS -> {
                if (businesses != null) {
                    val newFragment =
                        BusinessDetailFragment.newInstance(businesses)
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_fragment_manager, newFragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
            Navigator.WEATHER_DETAIL -> {
                if (weatherModel != null && cityName != null) {
                    val newFragment =
                        WeatherDetailFragment.newInstance(weatherModel, cityName)
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_fragment_manager, newFragment)
                        .addToBackStack(null)
                        .commit()
                }

            }
        }
    }


}
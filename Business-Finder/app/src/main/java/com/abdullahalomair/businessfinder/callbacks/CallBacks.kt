package com.abdullahalomair.businessfinder.callbacks

import com.abdullahalomair.businessfinder.model.navigator.Navigator
import com.abdullahalomair.businessfinder.model.wathermodel.WeatherModel
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses

interface CallBacks {

    fun applicationNavigator(navigator: Navigator,
                             businesses: Businesses,
                             businessDetails: BusinessDetails)
}
package com.abdullahalomair.businessfinder.callbacks

import com.abdullahalomair.businessfinder.model.navigator.Navigator
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.ForeCastDay
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses

interface CallBacks {
    fun applicationNavigator(navigator: Navigator,
                             businesses: Businesses? = null,
                             businessDetails: BusinessDetails? = null,
                             weatherModel: ForeCastDay? = null,
                             cityName: String? = null)
}
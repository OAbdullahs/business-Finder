package com.abdullahalomair.businessfinder.controllers

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RawRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.abdullahalomair.businessfinder.*
import com.abdullahalomair.businessfinder.callbacks.CallBacks
import com.abdullahalomair.businessfinder.databinding.BusinessDetailsBinding
import com.abdullahalomair.businessfinder.model.navigator.Navigator
import com.abdullahalomair.businessfinder.model.wathermodel.WeatherModel
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses
import com.abdullahalomair.businessfinder.viewmodels.BusinessDetailViewModel
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

private const val UPPER_ARROW = "↑"
private const val DOWN_ARROW  = "↓"
private const val C_DEGREE    = "℃"
class BusinessDetailFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: BusinessDetailsBinding
    private val scope = CoroutineScope(Dispatchers.IO)
    private var callBacks: CallBacks? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callBacks = requireContext() as CallBacks
        binding = DataBindingUtil.inflate(inflater, R.layout.business_details, container, false)
        binding.viewModel = BusinessDetailViewModel(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val businessData: Businesses? = arguments?.getParcelable(BUSINESS)
        val businessDetails: BusinessDetails? = arguments?.getParcelable(BUSINESS_DETAILS)
        initMapView(savedInstanceState)
        if (businessDetails != null && businessData != null) {
            initSetTexts(businessData,businessDetails)

        }
    }
    private fun initSetTexts(businessData: Businesses, businessDetails: BusinessDetails){
        try {
            initSetTextsFinal(businessData, businessDetails)
        }catch (e:NoSuchElementException){
            scope.launch {
                val businessDetail = binding.viewModel?.getBusinessDetails(businessData.id)
                withContext(Dispatchers.Main){
                    if (businessDetail != null) initSetTextsFinal(businessData, businessDetail)
                }
            }
        }
    }

    private fun initSetTextsFinal(businessData: Businesses, businessDetails: BusinessDetails) {
        binding.apply {
            scope.launch {
                val getImageDrawable = Glide
                    .with(this@BusinessDetailFragment)
                    .asDrawable()
                    .load(businessData.imageUrl)
                    .submit()
                    .get()

                withContext(Dispatchers.Main) {
                    val latAndLng =
                        "${businessData.coordinates.latitude},${businessData.coordinates.longitude}"

                    initWeatherForeCast(latAndLng)
                    viewModel?.imageDrawable = getImageDrawable
                    viewModel?.businessTitle = businessData.name
                    viewModel?.businessAlias = businessData.categories.last().title
                    viewModel?.businessRatings = businessData.rating.toFloat()
                    viewModel?.isBusinessOpen = setOpenText(businessDetails.hours.last().isOpenNow)
                    viewModel?.priceTag = ". ${businessData.price}"

                }
            }

        }
    }

    private fun setOpenText(isOpen: Boolean): String = when (isOpen) {
        true -> requireContext().getText(R.string.business_open).toString()
        else -> requireContext().getText(R.string.business_closed).toString()
    }



    private fun initWeatherForeCast(location: String) {
        binding?.viewModel?.getWeatherForecast(location)?.observe(
            viewLifecycleOwner, { weatherForeCast ->
                binding.weatherShimmerFragment.hideShimmer()
                binding.apply {
                    viewModel?.weatherForeCastModel = weatherForeCast.forecast.foreCastDay
                    viewModel?.cityName =  weatherForeCast.location.name
                    //Current Weather Data
                    viewModel?.businessCityName = weatherForeCast.location.name
                    viewModel?.businessCountyName = weatherForeCast.location.country
                    viewModel?.currentWeather = "${weatherForeCast.current.tempC}$C_DEGREE"
                    val weatherIconRawRes =
                        getWeatherAnimationName(weatherForeCast.current.condition.code)
                    viewModel?.currentWeatherIcon = weatherIconRawRes
                    val maxDegree = weatherForeCast.forecast.foreCastDay[0].day.maxTemp_c
                    val minDegree = weatherForeCast.forecast.foreCastDay[0].day.minTemp_c
                    val finalTextAvg =
                        "$maxDegree$C_DEGREE $UPPER_ARROW $minDegree$C_DEGREE $DOWN_ARROW"
                    viewModel?.averageWeather = finalTextAvg
                    viewModel?.currentDate = weatherForeCast.forecast.foreCastDay[0].date
                    //Second Day Weather Data
                    val secondWeatherIconRawRes =
                        getWeatherAnimationName(weatherForeCast.forecast.foreCastDay[1].day.condition.code)
                    val secondDayTemp =
                        "${weatherForeCast.forecast.foreCastDay[1].day.avgTemp_c}$C_DEGREE"
                    val secondDayMaxAndMinDegrees =
                        "${weatherForeCast.forecast.foreCastDay[1].day.maxTemp_c}$C_DEGREE$UPPER_ARROW " +
                                "${weatherForeCast.forecast.foreCastDay[1].day.minTemp_c}$C_DEGREE$DOWN_ARROW"
                    viewModel?.secondDayWeatherIcon = secondWeatherIconRawRes
                    viewModel?.secondDayWeatherDegree = secondDayTemp
                    viewModel?.secondDayWeatherAvg = secondDayMaxAndMinDegrees
                    viewModel?.secondDayDate = weatherForeCast.forecast.foreCastDay[1].date

                    //Second Day Weather Data
                    val thirdWeatherIconRawRes =
                        getWeatherAnimationName(weatherForeCast.forecast.foreCastDay[2].day.condition.code)
                    val thirdDayTemp =
                        "${weatherForeCast.forecast.foreCastDay[2].day.avgTemp_c}$C_DEGREE"
                    val thirdDayMaxAndMinDegrees =
                        "${weatherForeCast.forecast.foreCastDay[2].day.maxTemp_c}$C_DEGREE$UPPER_ARROW " +
                                "${weatherForeCast.forecast.foreCastDay[2].day.minTemp_c}$C_DEGREE$DOWN_ARROW"
                    viewModel?.thirdDayWeatherIcon = thirdWeatherIconRawRes
                    viewModel?.thirdDayWeatherDegree = thirdDayTemp
                    viewModel?.thirdDayWeatherAvg = thirdDayMaxAndMinDegrees
                    viewModel?.thirdDayDate = weatherForeCast.forecast.foreCastDay[2].date


                }

            }
        )
    }


    private fun initMapView(savedInstanceState: Bundle?) {
        binding.googleMapView.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@BusinessDetailFragment)
        }
    }

    private suspend fun getBusinessDetails(businessId: String): BusinessDetails? {
        return binding?.viewModel?.getBusinessDetails(businessId)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            val businessData: Businesses? = arguments?.getParcelable(BUSINESS)
            if (businessData != null) {
                val latLng =
                    LatLng(businessData.coordinates.latitude, businessData.coordinates.longitude)
                googleMap.addMarker(
                    MarkerOptions()
                        .title(businessData.name)
                        .position(latLng)
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
            } else {
                val latLng = LatLng(24.7136, 46.6753)
                googleMap.addMarker(
                    MarkerOptions()
                        .title("Error")
                        .position(latLng)
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
            }

        }
    }

    @SuppressLint("WrongConstant")
    override fun onResume() {
        super.onResume()
        binding.googleMapView.onResume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.googleMapView.onDestroy()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.googleMapView.onDestroy()
    }

    companion object {
        fun newInstance(
            business: Businesses,
            businessDetails: BusinessDetails
        ): BusinessDetailFragment {
            val args = Bundle()
            args.apply {
                putParcelable(BUSINESS, business)
                putParcelable(BUSINESS_DETAILS, businessDetails)
            }
            return BusinessDetailFragment().apply {
                arguments = args
            }
        }
    }
}
package com.abdullahalomair.businessfinder.controllers

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullahalomair.businessfinder.*
import com.abdullahalomair.businessfinder.callbacks.CallBacks
import com.abdullahalomair.businessfinder.databinding.BusinessDetailsBinding
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.WeatherForeCast
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses
import com.abdullahalomair.businessfinder.viewmodels.BusinessDetailViewModel
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException
import java.lang.String.format
import java.util.*

private const val UPPER_ARROW = "↑"
private const val DOWN_ARROW  = "↓"
private const val C_DEGREE    = "℃"
private const val TIME_DETAIL = "TimeDetailBottomSheet"
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
        initMapView(savedInstanceState)

        if (businessData != null) {
            scope.launch {
                val hasNetwork = binding.viewModel?.hasNetwork()
                if (hasNetwork != null){
                    val businessDetail = if (hasNetwork){
                        binding.viewModel?.getBusinessDetails(businessData.id)
                    }
                    else {
                        binding.viewModel?.getBusinessDetailsLocal(businessData.id)
                    }
                    withContext(Dispatchers.Main){
                        if (businessDetail != null) initSetTexts(businessData, businessDetail)
                        else initSetTexts(businessData, BusinessDetails())
                    }
                }

            }
        }
    }



    private fun initSetTexts(businessData: Businesses, businessDetails: BusinessDetails){
        initPlanDay(businessData, businessDetails)
            initSetTextsFinal(businessData, businessDetails)
    }

    private fun initSetTextsFinal(businessData: Businesses, businessDetails: BusinessDetails) {
        try {
            binding.timeDetail.setOnClickListener {
            val timeDetailSheet =
                TimeDetailBottomSheet.newInstance(businessDetails.hours[0])
            timeDetailSheet.show(requireActivity().supportFragmentManager,TIME_DETAIL)
            }
        }catch (e: java.lang.Exception){

        }

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

                    initWeatherForeCast(latAndLng, businessData.id)
                    viewModel?.imageDrawable = getImageDrawable
                    viewModel?.businessTitle = businessData.name
                        viewModel?.businessAlias = businessData.categories.last().title
                    viewModel?.businessRatings = businessData.rating.toFloat()
                    try {
                    viewModel?.isBusinessOpen = setOpenText(businessDetails.hours.last().isOpenNow)
                    }catch (e: Exception){
                        Toast.makeText(
                            requireContext(),
                            requireContext().getText(R.string.error_time),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    viewModel?.priceTag = ". ${businessData.price}"

                }
            }

        }
    }

    private fun setOpenText(isOpen: Boolean): String = when (isOpen) {
        true -> requireContext().getText(R.string.business_open).toString()
        else -> requireContext().getText(R.string.business_closed).toString()
    }



    private fun initWeatherForeCast(location: String, businessId: String) {
        val hasNetwork = binding.viewModel?.hasNetwork()
        if (hasNetwork != null){
                scope.launch {
                    val weatherForeCast:WeatherForeCast? = if (hasNetwork) {
                        binding.viewModel?.getWeatherForecast(location, businessId)
                    }else {
                        binding.viewModel?.getWeatherForecastLocal(businessId)
                    }
                    withContext(Dispatchers.Main){
                        setWeatherDetails(weatherForeCast)
                    }
                }
        }


    }

    private fun setWeatherDetails(weatherForeCast: WeatherForeCast?){
        if (weatherForeCast != null){
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

                try {
                val maxDegree = weatherForeCast.forecast.foreCastDay[0].day.maxTemp_c
                val minDegree = weatherForeCast.forecast.foreCastDay[0].day.minTemp_c
                val finalTextAvg =
                    "$maxDegree$C_DEGREE$UPPER_ARROW $minDegree$C_DEGREE$DOWN_ARROW"
                viewModel?.averageWeather = finalTextAvg
                }catch (e: Exception){
                    viewModel?.averageWeather = requireContext().getString(R.string.error_weather)
                }

                binding.generalWeatherRecyclerview.apply {
                    layoutManager = GridLayoutManager(requireContext(),3,
                        LinearLayoutManager.VERTICAL,
                        false)
                    adapter = GeneralWeatherAdapter(weatherForeCast.forecast.foreCastDay,weatherForeCast.location.name)
                }
            }
        }
    }

    private fun initMapView(savedInstanceState: Bundle?) {
        try {

        binding.googleMapView.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@BusinessDetailFragment)
        }
        }catch (e:IllegalArgumentException){
            Toast.makeText(requireContext(),e.localizedMessage,Toast.LENGTH_SHORT).show()
        }
    }
    private fun initPlanDay(businessData: Businesses, businessDetails: BusinessDetails){
        binding.colorPicker.setOnClickListener {
            val colorDialogFragment = ColorPickerDialogFragment()
            colorDialogFragment.setTargetFragment(this, COLOR_REQUEST_CODE)
            colorDialogFragment.show(parentFragmentManager, "colorDialogFragment")
        }

        binding.callBusiness.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + businessData.phone))
                startActivity(intent)
            }catch (e:Exception){

            }
        }
        binding.selectDate.setOnClickListener {
            val args = Bundle()
            args.putParcelable(HOURS_LIST, businessDetails)
            val calenderDialogFragment = CalenderDialogFragment()
            calenderDialogFragment.setTargetFragment(this, DATE_REQUEST_CODE)
            calenderDialogFragment.arguments = args
            calenderDialogFragment.show(parentFragmentManager, "calenderDialogFragment")
        }

        binding.addPlanButton.setOnClickListener {
            val dateObject = binding.viewModel?.planDate
            if (dateObject != null){
            binding.addPlanButton.apply {
                playAnimation()
                val handler = Handler()
                handler.postDelayed(
                    {
                    scope.launch {
                        binding.viewModel?.addPlanToDataBase(
                            businessData,
                            businessData.id, binding.planTitleEditTex.text.toString()
                        )
                        withContext(Dispatchers.Main){
                            val bottomSheet = PlansBottomSheet()
                            bottomSheet.show(requireActivity().supportFragmentManager, "")
                        }
                    }
                }, 5000)
            }
            }else{
                Toast.makeText(requireContext(),
                    requireContext().getText(R.string.date_error),
                    Toast.LENGTH_SHORT).show()
            }

        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            COLOR_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (data?.extras?.containsKey(COLOR_NAME) == true) {
                        val color = data.extras!!.getInt(COLOR_NAME)
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            binding.viewModel?.randomColor = Color.valueOf(color).toArgb()
                        } else {
                            val hexColor = format("#%06X", 0xFFFFFF and color)
                            try {
                                binding.viewModel?.randomColor = hexColor.toInt()
                            } catch (e: TypeCastException) {
                            }
                        }
                    }
                }
            }
            DATE_REQUEST_CODE ->
                if (Activity.RESULT_OK == resultCode) {
                    val date: Date? = data?.extras?.getSerializable(DATE_PICKER) as Date?
                    binding.viewModel?.planDate = date
                }
        }

    }

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
        ): BusinessDetailFragment {
            val args = Bundle()
            args.apply {
                putParcelable(BUSINESS, business)
            }
            return BusinessDetailFragment().apply {
                arguments = args
            }
        }
    }
}
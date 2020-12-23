package com.abdullahalomair.businessfinder.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullahalomair.businessfinder.CITY_NAME
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.WEATHER_MODEL
import com.abdullahalomair.businessfinder.callbacks.CallBacks
import com.abdullahalomair.businessfinder.databinding.WeatherFragmentBinding
import com.abdullahalomair.businessfinder.getWeatherAnimationName
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.ForeCastDay
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.Hour
import com.abdullahalomair.businessfinder.viewmodels.WeatherDetailViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val C_DEGREE = "℃"

class WeatherDetailFragment : Fragment() {
    private lateinit var binding: WeatherFragmentBinding
    private var callBacks: CallBacks? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callBacks = requireContext() as CallBacks
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.weather_fragment,
            container, false
        )
        binding.viewModel = WeatherDetailViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weatherData: ForeCastDay? = arguments?.getParcelable(WEATHER_MODEL)
        val cityName: String? = arguments?.getString(CITY_NAME)
        if (weatherData != null && cityName != null) {
            initView(weatherData, cityName)
        }
    }




    private fun initView(weatherData: ForeCastDay, cityName: String) {
        binding?.apply {
            initRecyclerView(weatherData.hour)
            val simpleFormat = SimpleDateFormat("hh aa")
            val currentTime =
                simpleFormat.format(Date()) // remove space
            viewModel?.cityName = cityName
            viewModel?.currentTime = currentTime
            viewModel?.weatherDegree = "${weatherData.day.avgTemp_c}$C_DEGREE"
            viewModel?.maxTemp = "${weatherData.day.maxTemp_c}$C_DEGREE"
            viewModel?.minTemp = "${weatherData.day.minTemp_c}$C_DEGREE"
            viewModel?.weatherStatusText = weatherData.day.condition.text
            val imageAnimation = getWeatherAnimationName(weatherData.day.condition.code)
            viewModel?.weatherAnimation = imageAnimation
        }
    }

    private fun initRecyclerView(hours:List<Hour>) {
        binding.weatherTimeRecyclerview.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = WeatherAdapter(hours)
        }
    }

    override fun onStop() {
        super.onStop()
        callBacks = null
    }

    companion object {
        fun newInstance(weatherForeCast: ForeCastDay, cityName: String): WeatherDetailFragment {
            val args = Bundle()
            args.putParcelable(WEATHER_MODEL, weatherForeCast)
            args.putString(CITY_NAME, cityName)
            return WeatherDetailFragment().apply {
                arguments = args
            }
        }
    }
}
package com.abdullahalomair.businessfinder.controllers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.callbacks.CallBacks
import com.abdullahalomair.businessfinder.databinding.MainFragmentBinding
import com.abdullahalomair.businessfinder.model.navigator.Navigator
import com.abdullahalomair.businessfinder.model.wathermodel.WeatherModel
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessesList
import com.abdullahalomair.businessfinder.viewmodels.MainFragmentViewModel
import kotlinx.coroutines.*

private const val CATEGORY_POSITION = "Category_Position"
private const val ALL_CATEGORY = "All"
class MainFragment: Fragment() {
    private lateinit var binding: MainFragmentBinding
    private lateinit var mainFragmentViewModel: MainFragmentViewModel
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var recentAdapter: RestaurantAdapter
    private val scope = CoroutineScope(Dispatchers.IO)
    private lateinit var businessesList: BusinessesList
    private  var callback: CallBacks? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callback = context as CallBacks
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainFragmentViewModel = MainFragmentViewModel(requireContext())
        binding = DataBindingUtil.inflate(inflater,
            R.layout.main_fragment,
            container,false)
        binding.categoryProgressBar.visibility = View.GONE
        binding.categoryProgressBar.visibility = View.VISIBLE
        binding.recentVisitRecyclerview.apply {
            layoutManager  = LinearLayoutManager(requireContext())
        }
        val scrollHelper = LinearSnapHelper()
        scrollHelper.attachToRecyclerView(binding.recentVisitRecyclerview)
        binding.viewModel = mainFragmentViewModel
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            val textWatcher = binding.viewModel?.textWatcher
            if (textWatcher != null) {
                binding.searchRestaurants.addTextChangedListener(textWatcher)
            }
        updateUI("New York")

    }
    private fun updateRecyclerView(business: List<Businesses>){
        binding.recentVisitRecyclerview.apply {
            recentAdapter = RestaurantAdapter(
                    business,
                    this@MainFragment::getWeatherDetail,
                    this@MainFragment::getBusinessDetail,
                    )
            adapter = recentAdapter

        }
    }

//    override fun onResume() {
//        super.onResume()
//        updateUI("Los Angeles")
//    }

    private fun updateUI(searchId:String){
        binding.viewModel?.getBusinessList(searchId)?.observe(
            requireActivity(),{businessesData ->
                businessesList = businessesData
                scope.launch {
                    val data = async {  binding.viewModel?.getFinalCategoryData(businessesData)?.toList()}
                    withContext(Dispatchers.Main){
                        binding.categoryRecyclerview.apply {
                            if (data.await() != null){
                                categoryAdapter = CategoryAdapter(requireContext(),
                                    data.await()!!,
                                    this@MainFragment::updateViewByCategory)
                                adapter = categoryAdapter
                                layoutManager = LinearLayoutManager(
                                    requireContext(),
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                                adapter?.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                            }
                            binding.categoryRecyclerview.visibility = View.VISIBLE
                            binding.categoryProgressBar.visibility = View.GONE
                        }
                    }
                }
                updateRecyclerView(businessesData.businesses)
            }
        )
    }
    private fun updateViewByCategory(categoryName:String){
        if (categoryName == ALL_CATEGORY){
            updateRecyclerView(businessesList.businesses)
        }else{
            updateRecyclerView(businessesList.businesses.filter {
                    business ->
                business.categories.any { titles -> titles.title == categoryName }
            }
            )
        }
    }
    private suspend fun getBusinessDetail(businessId: String): BusinessDetails {
        val finalList = binding.viewModel?.getBusinessesDetails(businessId)
        return finalList ?: BusinessDetails()
    }

    private suspend fun getWeatherDetail(location: String):WeatherModel{
        val finalList = binding.viewModel?.getWeatherDetail(location)
        return finalList ?: WeatherModel()
    }




    override fun onStop() {
        super.onStop()
        callback = null

    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }



    companion object{
        fun newInstance(): MainFragment {
            return MainFragment()
        }

    }
}
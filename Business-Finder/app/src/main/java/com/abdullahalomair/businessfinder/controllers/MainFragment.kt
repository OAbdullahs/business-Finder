package com.abdullahalomair.businessfinder.controllers

import android.app.Activity
import android.content.res.AssetManager
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.callbacks.CallBacks
import com.abdullahalomair.businessfinder.databinding.MainFragmentBinding
import com.abdullahalomair.businessfinder.model.localmodel.Cities
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.WeatherForeCast
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessesList
import com.abdullahalomair.businessfinder.sharedpreference.EncryptSharedPreferences
import com.abdullahalomair.businessfinder.viewmodels.MainFragmentViewModel
import com.blongho.country_data.World
import com.google.gson.Gson
import kotlinx.coroutines.*

private const val DEFAULT_CITY = "New York"
private const val CATEGORY_POSITION = "Category_Position"
private const val ALL_CATEGORY = "All"
class MainFragment: Fragment() {
    private lateinit var binding: MainFragmentBinding
    private lateinit var mainFragmentViewModel: MainFragmentViewModel
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var recentAdapter: RestaurantAdapter
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private val scope = CoroutineScope(Dispatchers.IO)
    private  var businessesList: BusinessesList = BusinessesList()
    private  var callback: CallBacks? = null
    private var didUserWrite = false

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
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.main_fragment,
            container, false
        )
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
            initAutoTextView()

        if (binding.viewModel?.hasNetwork()!!){
            updateUIOnline(DEFAULT_CITY)
        }
        else{
            binding.viewModel?.getBusinessListLocal()?.observe(
                viewLifecycleOwner,{businessesList ->
                    if (businessesList != null) {
                        updateUI(businessesList)
                    }else
                    {
                        updateUIOnline(DEFAULT_CITY)
                    }
                }
            )
        }





    }

    private fun initAutoTextView(){
        scope.launch {
            val getCities = getCitiesList()
            val getRecentSearched = getRecentSearchQueries()
            withContext(Dispatchers.Main){
                if (getRecentSearched != null) {
                    arrayAdapter = customArrayAdapter(true, getRecentSearched.toList())
                    binding.searchRestaurants.setAdapter(arrayAdapter)
                    binding.searchRestaurants.setOnClickListener {
                        didUserWrite = false
                        binding.searchRestaurants.showDropDown()

                    }
                }
                binding.searchRestaurants.addTextChangedListener(autoCompleteTextWatcher(getCities))
                binding.searchRestaurants.setOnItemClickListener { parent, _, position, _ ->
                    setRecentSearchQuery(parent.getItemAtPosition(position).toString())
                    binding.searchRestaurants.text.clear()
                    updateUIOnline(parent.getItemAtPosition(position).toString())
                    binding.categoryProgressBar.visibility = View.VISIBLE
                    binding.categoryRecyclerview.visibility = View.GONE
                    val imm =
                        context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
                    imm?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)

                }
            }

        }
    }


    private fun autoCompleteTextWatcher(getCities:List<String>):TextWatcher {

        return object :TextWatcher{
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
              //No Need
            }
            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (!didUserWrite){
                    arrayAdapter =  customArrayAdapter(false,getCities)
                    binding.searchRestaurants.setAdapter(arrayAdapter)
                }
                didUserWrite = true
            }

            override fun afterTextChanged(s: Editable?) {
                //Nothing
            }

        }

    }

    private fun customArrayAdapter(isItSearched: Boolean, list:List<String>)
    :ArrayAdapter<String>{
        return object : ArrayAdapter<String>(
            requireContext(),
            R.layout.simple_dropdown_list,
            R.id.autoComplete,
            list
        ){
            override fun getView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                        val view = super.getView(position, convertView, parent)
                        val textView: TextView = view.findViewById(R.id.autoComplete)
                        if (isItSearched) {
                            var drawable:Drawable? = if (requireActivity().resources.configuration.uiMode and
                                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES){
                                requireContext().getDrawable(R.drawable.history_icon_day)
                            }else{
                                requireContext().getDrawable(R.drawable.history_icon_night)
                            }
                            if (drawable != null){
                                textView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                    drawable,
                                    null,
                                    null,
                                    null
                                )
                                textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
                            }
                        }
                        else{
                            val (_,country) = textView.text.toString().split(",")
                            val countryAfterModifying = country.replace(" ", "").toLowerCase()
                            val flag = World.getFlagOf(countryAfterModifying)
                            val drawable = requireContext().getDrawable(flag)
                            drawable?.setBounds(0,0,60,60)
                            if (drawable != null){
                                textView.setCompoundDrawables(
                                    drawable,
                                    null,
                                    null,
                                    null
                                )
                                textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
                            }

                        }
                        return view

            }
        }
    }
    private fun updateRecyclerView(business: List<Businesses>){
        binding.recentVisitRecyclerview.apply {
            recentAdapter = RestaurantAdapter(
                business)
            adapter = recentAdapter

        }
    }

    private fun getRecentSearchQueries():Set<String>?{
        val encryptSharedPreferences = EncryptSharedPreferences.get()
        return encryptSharedPreferences.getStoredQueries()
    }
    private fun setRecentSearchQuery(query: String) {
        val encryptSharedPreferences = EncryptSharedPreferences.get()
        val getQueries = getRecentSearchQueries()?.toMutableList()

        if (getQueries != null){
            if (getQueries.size == 5){
                 getQueries.removeAt(getQueries.size - 1)
                getQueries.add(query)
                encryptSharedPreferences.setStoredQueries(getQueries.toSet())
            }else{
                getQueries.add(query)
                encryptSharedPreferences.setStoredQueries(getQueries.toSet())
            }
        }
        else{
            val firstTime = setOf(query)
            encryptSharedPreferences.setStoredQueries(firstTime)
        }
        scope.launch {
            val getRecentSearched = getRecentSearchQueries()?.toList()
            withContext(Dispatchers.Main) {
                if (getRecentSearched != null) {
                    arrayAdapter = customArrayAdapter(true, getRecentSearched.toList())
                    binding.searchRestaurants.setAdapter(arrayAdapter)
                }
            }
        }
    }
    private fun getCitiesList(): MutableList<String> {
        val assetManager:AssetManager = requireContext().assets
        val cities = assetManager.open("cities.json").bufferedReader().use {
            it.readText()
        }
        val citiesObject = Gson().fromJson(cities, Cities::class.java)
        val mutableList = mutableListOf<String>()
        for (element in citiesObject.cities){
            mutableList.add(element.name + " , " + element.country)
        }
        return mutableList
    }
    private fun updateUIOnline(searchId: String){
        if (binding.viewModel?.hasNetwork()!!){
            binding.viewModel?.getBusinessList(searchId)?.observe(
                viewLifecycleOwner, { businessesData ->
                    scope.launch {
                        storeResultToDatBase(businessesData)
                    }
                    addToDatabase(businessesData)
                    updateUI(businessesData)
                }
            )
        }else{
            Toast.makeText(requireContext(),
                requireContext().getText(R.string.connect_to_the_internet),
            Toast.LENGTH_SHORT).show()
        }

    }
    private suspend fun storeResultToDatBase(businessList: BusinessesList){
         binding.viewModel?.storeBusinessDetails(businessList)
         binding.viewModel?.storeWeatherForCasts(businessList)
    }
    private fun addToDatabase(businessList: BusinessesList){
        binding.viewModel?.deleteBusinessListLocal()
        binding.viewModel?.insertBusinessListLocal(businessList)
    }
    private fun updateUI(businessLists: BusinessesList){
        businessesList = BusinessesList()
        businessesList = businessLists
        scope.launch {
            val finalCategory =
                binding.viewModel?.getFinalCategoryData(businessLists)?.toList()
            withContext(Dispatchers.Main) {
                binding.categoryRecyclerview.apply {
                    if (finalCategory != null) {
                        categoryAdapter = CategoryAdapter(
                            requireContext(),
                            finalCategory,
                            this@MainFragment::updateViewByCategory
                        )
                        adapter = categoryAdapter
                        layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        adapter?.stateRestorationPolicy =
                            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                    }
                    binding.categoryRecyclerview.visibility = View.VISIBLE
                    binding.categoryProgressBar.visibility = View.GONE
                }
            }

        }
        updateRecyclerView(businessLists.businesses)
    }
    private fun updateViewByCategory(categoryName: String){
        if (categoryName == ALL_CATEGORY){
            updateRecyclerView(businessesList.businesses)
        }else{
            updateRecyclerView(businessesList.businesses.filter { business ->
                business.categories.any { titles -> titles.title == categoryName }
            }
            )
        }
    }


    override fun onStop() {
        super.onStop()
        callback = null
        businessesList = BusinessesList()

    }

    override fun onResume() {
        super.onResume()
        binding?.viewModel?.getBusinessListLocal()?.observe(
            viewLifecycleOwner,{ data ->
                businessesList = data
                updateUI(data)

            }
        )
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
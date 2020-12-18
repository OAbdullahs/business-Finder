package com.abdullahalomair.businessfinder.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.MainFragmentBinding
import com.abdullahalomair.businessfinder.model.yelpmodel.Businesses
import com.abdullahalomair.businessfinder.viewmodels.MainFragmentViewModel
import kotlinx.coroutines.*

private const val CATEGORY_POSITION = "Category_Position"
private const val ALL_CATEGORY = "All"
class MainFragment: Fragment() {
private lateinit var binding: MainFragmentBinding
private lateinit var categoryAdapter: CategoryAdapter
private lateinit var recentAdapter: RestaurantAdapter
    private val scope = CoroutineScope(Dispatchers.IO)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        binding.viewModel =  MainFragmentViewModel(requireActivity() as MainActivity)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            val textWatcher = binding.viewModel?.textWatcher
            if (textWatcher != null) {
                binding.searchRestaurants.addTextChangedListener(textWatcher)
            }
        binding.viewModel?.getBusinessList("California")?.observe(
            requireActivity(),{busnissesData ->
                scope.launch {
                    val data = async {  binding.viewModel?.getFinalCategoryData(busnissesData)?.toList()}
                    withContext(Dispatchers.Main){
                        binding.categoryRecyclerview.apply {
                            if (data.await() != null){
                                categoryAdapter = CategoryAdapter(requireContext(), data.await()!!)
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
            updateRecyclerView(busnissesData.businesses)
            categoryName.observe(
                    requireActivity(),{data ->
                    if (data == ALL_CATEGORY){
                        updateRecyclerView(busnissesData.businesses)
                    }else{
                        updateRecyclerView(busnissesData.businesses.filter {
                                business -> business.categories.filter{
                                titles -> titles.title == data }.isNotEmpty()
                        }
                        )
                    }

            }
            )
            }

        )
    }
    private fun updateRecyclerView(business: List<Businesses>){
        binding.recentVisitRecyclerview.apply {
            recentAdapter = RestaurantAdapter(requireActivity() as MainActivity,requireContext(),business)
            adapter = recentAdapter

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val number:Int = binding.categoryRecyclerview.scrollState
        outState.putInt(CATEGORY_POSITION, number)
        super.onSaveInstanceState(outState)
    }






    companion object{
        private val categoryName: MutableLiveData<String> = MutableLiveData()
         fun getData(data: String){
             categoryName.value = data
        }

        fun newInstance(): MainFragment {
            return MainFragment()
        }

    }
}
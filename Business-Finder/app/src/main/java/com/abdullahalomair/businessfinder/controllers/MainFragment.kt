package com.abdullahalomair.businessfinder.controllers

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.MainFragmentBinding
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessesList
import com.abdullahalomair.businessfinder.model.yelpmodel.Categories
import com.abdullahalomair.businessfinder.sharedpreference.EncryptSharedPreferences
import com.abdullahalomair.businessfinder.viewmodels.MainFragmentViewModel
import kotlinx.coroutines.*

private const val CATEGORY_POSITION = "Category_Position"
class MainFragment: Fragment() {
private lateinit var binding: MainFragmentBinding
private lateinit var categoryAdapter: CategoryAdapter
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
            }
        )


    }

    override fun onSaveInstanceState(outState: Bundle) {
        val number:Int = binding.categoryRecyclerview.scrollState
        outState.putInt(CATEGORY_POSITION, number)
        super.onSaveInstanceState(outState)
    }


    companion object{
        fun newInstance(): MainFragment {
            return MainFragment()
        }

    }
}
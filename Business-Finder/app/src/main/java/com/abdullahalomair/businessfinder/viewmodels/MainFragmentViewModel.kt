package com.abdullahalomair.businessfinder.viewmodels

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.controllers.BusinessRepository
import com.abdullahalomair.businessfinder.controllers.CategoryAdapter
import com.abdullahalomair.businessfinder.controllers.MainActivity
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessesList
import com.abdullahalomair.businessfinder.model.yelpmodel.Categories
import kotlinx.coroutines.*


class MainFragmentViewModel(private val activity: MainActivity): BaseObservable() {

    private val repository = BusinessRepository.get()
      fun getBusinessList(location:String): LiveData<BusinessesList> {
        return repository.getBusinessList(location)
    }
    suspend fun  getFinalCategoryData(data: BusinessesList): MutableList<Pair<String, Int>> {
        val finalResult:MutableList<Pair<String,Int>>
            //Get Data from Server Then extract what needed
            val dataPairs = getCategoriesPair(data).filter { it.first.isNotBlank() }
            val categories = getListOfCategories(data).filter { it.title.isNotBlank() }
            finalResult = finalFilteringResult(dataPairs, categories)
        return finalResult

    }


    val textWatcher = object :TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {

        }

    }


    private suspend  fun finalFilteringResult(catPair: List<Pair<String,Int>>, cat: List<Categories>): MutableList<Pair<String, Int>> {
        val finalResult: MutableList<Pair<String,Int>> = mutableListOf()
        val text:String = activity.getString(R.string.all_categories)
        finalResult.add(text to catPair.size)
        cat.forEach { categories ->
            catPair.forEach {  pair ->
                if (categories.alias == pair.first){
                    finalResult.add(categories.title to pair.second)
                }
            }
        }
        return finalResult
    }
    private suspend fun getCategoriesPair(data: BusinessesList): List<Pair<String,Int>> {

        val categoryQuantity: MutableList<Pair<String, Int>> = mutableListOf()
        val categories = mutableSetOf<String>()
        data.businesses.forEach { data ->
            data.categories.forEach { categorey ->
                categories.add(categorey.alias)
            }
        }
        categories.forEach { value ->
            var counter = 0
            data.businesses.forEach { data ->
                data.categories.forEach { categorey ->
                    if (value == categorey.alias) {
                        counter++
                    }
                }
            }
            categoryQuantity.add(value to counter)
        }
        return categoryQuantity.toList()
    }
    private suspend  fun getListOfCategories(data: BusinessesList): List<Categories>{

        val categories: MutableList<Categories> = mutableListOf()
        data.businesses.forEach { data ->
            data.categories.forEach { categorey ->
                val categoryObject = Categories(categorey.alias, categorey.title)
                categories.add(categoryObject)
            }
        }
        return categories
    }
}
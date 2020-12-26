package com.abdullahalomair.businessfinder.viewmodels

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.controllers.BusinessRepository
import com.abdullahalomair.businessfinder.model.wathermodel.WeatherModel
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.WeatherForeCast
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessesList
import com.abdullahalomair.businessfinder.model.yelpmodel.Categories
import kotlinx.coroutines.*


class MainFragmentViewModel(private val context: Context): BaseObservable() {

    private val repository = BusinessRepository.get()

    //Get data from Local
    fun getBusinessListLocal():LiveData<BusinessesList>?{
        return repository.getBusinessListLocal()
    }
    suspend fun getBusinessDetailLocal(businessId: String): BusinessDetails?{
        return repository.getBusinessDetailLocal(businessId)
    }
    suspend fun getWeatherDataLocal(businessId: String): WeatherModel?{
        return repository.getWeatherDataLocal(businessId)
    }
    fun getWeatherForecastLocal(businessId: String): LiveData<WeatherForeCast>? {
        return repository.getWeatherForecastLocal(businessId)
    }

    //Insert Data to Database
    fun insertBusinessListLocal(businessesList: BusinessesList) =
        repository.insertBusinessListLocal(businessesList)

    fun insertBusinessDetailsLocal(businessDetails: BusinessDetails)  =
        repository.insertBusinessDetailsLocal(businessDetails)
    fun insertWeatherDataLocal(weatherModel: WeatherModel) =
        repository.insertWeatherDataLocal(weatherModel)
    fun insertWeatherForeCastLocal(weatherForeCast: WeatherForeCast) =
        repository.insertWeatherForeCastLocal(weatherForeCast)

    //Delete from Database
    fun deleteBusinessListLocal() =
        repository.deleteBusinessListLocal()
    fun deleteBusinessDetailsLocal() =
        repository.deleteBusinessDetailsLocal()
    fun deleteWeatherDataLocal() =
        repository.deleteWeatherDataLocal()
    fun deleteWeatherForeCastLocal() =
        repository.deleteWeatherForeCastLocal()


      fun getBusinessList(location:String): LiveData<BusinessesList> {
        return repository.getBusinessList(location)
    }

     suspend fun getWeatherDetail (location: String):WeatherModel {
        return repository.getWeatherData(location) ?: WeatherModel()
    }

     suspend fun getBusinessesDetails (businessId:String): BusinessDetails {
         return repository.getBusinessDetail(businessId) ?: BusinessDetails()
    }




     fun  getFinalCategoryData(data: BusinessesList): MutableList<Pair<String, Int>> {
        val finalResult:MutableList<Pair<String,Int>>
            //Get Data from Server Then extract what needed
            val dataPairs = getCategoriesPair(data).filter { it.first.isNotBlank() }
            val categories = getListOfCategories(data).filter { it.title.isNotBlank() }
            finalResult = finalFilteringResult(dataPairs, categories)
        return finalResult

    }




    private  fun finalFilteringResult(catPair: List<Pair<String,Int>>, cat: List<Categories>): MutableList<Pair<String, Int>> {
        val finalResult: MutableList<Pair<String,Int>> = mutableListOf()
        val text:String = context.getString(R.string.all_categories)
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
    private  fun getCategoriesPair(data: BusinessesList): List<Pair<String,Int>> {
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
    private  fun getListOfCategories(data: BusinessesList): List<Categories>{

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
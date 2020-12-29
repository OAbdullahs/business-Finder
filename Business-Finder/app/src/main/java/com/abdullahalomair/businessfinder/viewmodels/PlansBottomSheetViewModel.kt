package com.abdullahalomair.businessfinder.viewmodels

import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import com.abdullahalomair.businessfinder.controllers.BusinessRepository
import com.abdullahalomair.businessfinder.model.planmodel.PlanModel

class PlansBottomSheetViewModel: BaseObservable() {

    private val repository = BusinessRepository.get()

    fun getPlansList():LiveData<MutableList<PlanModel>>{
        return repository.getPlansList()
    }
    fun deleteAPlan(planModel: PlanModel){
        repository.deletePlanDetail(planModel)
    }

    var isListEmpty:Int? = null
    set(value){
        field = value
        notifyChange()
    }
    var isListEmptyRV:Int? = null
    set(value){
        field = value
        notifyChange()
    }
}
package com.abdullahalomair.businessfinder.viewmodels

import android.content.Context
import android.widget.Toast

import androidx.databinding.BaseObservable
import com.abdullahalomair.businessfinder.controllers.MainFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers


private const val DEFAULT_CLICK_DATA = "null"
class CategoryRecyclerViewMain( private val  context: Context): BaseObservable() {

    private  val observeOnScheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
    private val subscribeOnScheduler: Scheduler
        get() = Schedulers.io()

    var categoryName:String? = null
        set(value) {
                field = value
                notifyChange()
        }

    var categorySize: Int = 0



    //Send data to the fragment to notify the recycler view to be updated
    fun onBottomClick() {
        if (categoryName != null){
        Observable.just(categoryName)
                .observeOn(observeOnScheduler)
                .subscribeOn(subscribeOnScheduler)
                .subscribe({result -> MainFragment.getData(result ?:DEFAULT_CLICK_DATA) },
                        { error -> Toast.makeText(context,
                                   error.localizedMessage,
                                   Toast.LENGTH_SHORT).show()})
        }
    }



}
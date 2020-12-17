package com.abdullahalomair.businessfinder.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

private const val FILE_NAME = "searched_data"
private const val QUERY_DATA = "search_"
private const val COUNTER = "number_counter"
object EncryptSharedPreferences {

    private fun masterKeyValue(context: Context): MasterKey {
        return MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }
    private fun sharedPreference(context: Context): SharedPreferences {
        return EncryptedSharedPreferences.create(
            context,
            FILE_NAME,
            masterKeyValue(context),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

    }
    fun setStoredQuery(context: Context, query: String) {
        val counterNumber = getNumberCounter(context)
        sharedPreference(context).edit{
            putString(QUERY_DATA + counterNumber, query)
        }
    }
    fun resetNumberCounter(context: Context){
        sharedPreference(context).edit{
            putInt(COUNTER,0)
        }
    }
    fun getNumberCounter(context: Context):Int{
        return sharedPreference(context)
            .getInt(COUNTER,-1)
    }
    fun getSharedPreferenceValue(context: Context, queryNum:Int):String{
        return sharedPreference(context)
            .getString(QUERY_DATA+ queryNum,"")!!
    }
}
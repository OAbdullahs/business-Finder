package com.abdullahalomair.businessfinder.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.abdullahalomair.businessfinder.controllers.BusinessRepository
import java.util.*

private const val FILE_NAME = "searched_data"
private const val QUERY_DATA = "search_"
private const val COUNTER = "number_counter"
class EncryptSharedPreferences private constructor(private val context: Context){

    private fun masterKeyValue(): MasterKey {
        return MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }
    private fun sharedPreference(): SharedPreferences {
        return EncryptedSharedPreferences.create(
            context,
            FILE_NAME,
            masterKeyValue(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

    }
    fun setStoredQueries(queries: Set<String>) {
        sharedPreference().edit{
            putStringSet(QUERY_DATA, queries)
        }
    }
    fun getStoredQueries():Set<String>?{
        return sharedPreference()
            .getStringSet(QUERY_DATA,null)
    }
    companion object{
        private var INSTANCE: EncryptSharedPreferences? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = EncryptSharedPreferences(context)
            }
        }
        fun get(): EncryptSharedPreferences {
            return INSTANCE ?:
            throw IllegalStateException("EncryptSharedPreferences must be initialized")
        }
    }
}
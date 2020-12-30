package com.abdullahalomair.businessfinder

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert

import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

private const val DATA = "QUERY_DATA"
@RunWith(JUnit4::class)
class EncryptedSharedPreferencesTest {

    private lateinit var context: Context
    private lateinit var masterKey: MasterKey
    private lateinit var sharedPreferences: SharedPreferences
    @Before
    fun setUp(){
        context = ApplicationProvider.getApplicationContext()
        masterKey =  MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        sharedPreferences =
            EncryptedSharedPreferences.create(
                context,
                "testPrefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
    }

    @Test
    fun `verify_set_storage`(){
        val set = setOf("test0","test1")
        sharedPreferences.edit{
            putStringSet(DATA, set)
        }
       val storageData =  sharedPreferences.getStringSet(DATA,null)
        print(storageData)
        Assert.assertEquals(storageData, set)
    }

    @Test
    fun `verify_if_wrong_queries_should_result_null`(){
        val set = setOf("test0","test1")
        sharedPreferences.edit{
            putStringSet(DATA, set)
        }
        val storageData =  sharedPreferences.getStringSet("DATA",null)

        Assert.assertEquals(storageData, null)
    }

    @Test
    fun `verify_the_storage_will_store_only_five`(){
        val set = setOf("test0","test1","test2","test3","test4")
        sharedPreferences.edit{
            putStringSet(DATA, set)
        }
        val storageData =  sharedPreferences.getStringSet(DATA,null)?.toMutableList()
        if (storageData != null) {
            if (storageData.size == 5) {
                storageData.removeAt(0)
                storageData.add("query")
                sharedPreferences.edit{
                    putStringSet(DATA, storageData.toSet())
                }
            }
            else {
                sharedPreferences.edit{
                    putStringSet(DATA, storageData.toSet())
                }
            }
        }
        val storageDataFinal =  sharedPreferences.getStringSet(DATA,null)
        val finalSetShouldBe = setOf("query","test1","test2","test3","test4")

        Assert.assertEquals(storageDataFinal,finalSetShouldBe)
    }

    companion object{
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            FakeAndroidKeyStore.setup
        }
    }
}
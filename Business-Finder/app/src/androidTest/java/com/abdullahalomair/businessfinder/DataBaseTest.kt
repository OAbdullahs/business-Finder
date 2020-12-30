package com.abdullahalomair.businessfinder

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.abdullahalomair.businessfinder.database.BusinessFinderDao
import com.abdullahalomair.businessfinder.database.BusinessFinderDataBase
import com.abdullahalomair.businessfinder.model.planmodel.PlanModel
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.Location
import com.abdullahalomair.businessfinder.model.wathermodel.forecats.WeatherForeCast
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@RunWith(JUnit4::class)
class DataBaseTest {
    private lateinit var context: Context
    private lateinit var databaseDao: BusinessFinderDao
    private lateinit var database: BusinessFinderDataBase


    @Before
    fun setUp(){
        context = ApplicationProvider.getApplicationContext()
         database = Room.inMemoryDatabaseBuilder(
            context, BusinessFinderDataBase::class.java).build()
        databaseDao = database.getBusinessFinderDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }


    @Test
    fun `make_sure_for_inserting_Business_Detail_record`(){
        val businessDetails = BusinessDetails()
        businessDetails.id = "test01"

        databaseDao.insertBusinessDetails(businessDetails)
        runBlocking {
        val getFromDB = databaseDao.getBusinessDetails("test01")
            Assert.assertEquals(businessDetails.id,getFromDB?.id )
        }
    }
    @Test
    fun `Make_sure_function_delete_all_the_wanted_tables_in_the_database_for_BusinessDetail`(){
        val businessDetails = BusinessDetails(id = "test01")
        val businessDetails2 = BusinessDetails(id = "test02")
        val businessDetails3 = BusinessDetails(id = "test03")


        databaseDao.insertBusinessDetails(businessDetails)
        databaseDao.insertBusinessDetails(businessDetails2)
        databaseDao.insertBusinessDetails(businessDetails3)

        databaseDao.deleteBusinessDetailsPollWorker("test01")
        databaseDao.deleteBusinessDetailsPollWorker("test02")
        databaseDao.deleteBusinessDetailsPollWorker("test03")

        val getDb = databaseDao.getBusinessDetailsPollWorker()

        Assert.assertEquals(getDb, emptyList<BusinessDetails>())


    }

    @Test
    fun `Make_sure_function_delete_all_the_wanted_tables_in_the_database_for_WeatherForeCast`(){

        val weatherModel = WeatherForeCast(businessId = "test0")
        val weatherModel1 = WeatherForeCast(businessId = "test1")
        val weatherModel2 = WeatherForeCast(businessId = "test2")

        databaseDao.insertWeatherForeCast(weatherModel)
        databaseDao.insertWeatherForeCast(weatherModel1)
        databaseDao.insertWeatherForeCast(weatherModel2)

        databaseDao.deleteWeatherForeCastPollWorker("test0")
        databaseDao.deleteWeatherForeCastPollWorker("test1")
        databaseDao.deleteWeatherForeCastPollWorker("test2")

        val getDb = databaseDao.getWeatherForeCastPollWorker()

        Assert.assertEquals(getDb, emptyList<WeatherForeCast>())
    }

    @Test
    fun `Test_adding_planModel_to_DateBase`(){
        val planModel = PlanModel()

        databaseDao.insertPlanDetail(planModel)

        GlobalScope.launch (Dispatchers.Main){
         databaseDao.getPlanDetails().observeForever{
            Assert.assertNotNull(it)
        }
        }
    }
    @Test
    fun `Test_updating_BusinessDetail_value`(){
        val businessDetails = BusinessDetails(id = "test01", alias = "test01")

        databaseDao.insertBusinessDetails(businessDetails)
        runBlocking {
            val getBusinessDetail = databaseDao.getBusinessDetails("test01")
            getBusinessDetail?.alias = "Restaurant"
            databaseDao.updateBusinessDetailsPollWorker(getBusinessDetail!!)
            val finalBusinessDetail = databaseDao.getBusinessDetails("test01")
            Assert.assertEquals(finalBusinessDetail?.alias,"Restaurant")
        }

    }
    @Test
    fun `Test_updating_Weather_value`(){
        val weatherForeCast = WeatherForeCast(businessId = "test01",location = Location(name = "Riyadh") )

        databaseDao.insertWeatherForeCast(weatherForeCast)
        runBlocking {
            val getWeatherForeCast = databaseDao.getWeatherForeCast("test01")
            getWeatherForeCast?.location?.name = "Hofuf"
            if (getWeatherForeCast != null) {
                databaseDao.updateWeatherForeCastPollWorker(getWeatherForeCast)
            }
            val finalWeatherForeCast = databaseDao.getWeatherForeCast("test01")
            Assert.assertEquals(finalWeatherForeCast?.location?.name,"Hofuf")
        }

    }
}
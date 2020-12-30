package com.abdullahalomair.businessfinder

import android.os.Build
import com.abdullahalomair.businessfinder.controllers.BusinessFinderApplication
import com.abdullahalomair.businessfinder.controllers.MainActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config



@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P], application = BusinessFinderApplication::class)
class MainActivityTest {
    private lateinit var mainActivity: MainActivity

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        val controller =  Robolectric.buildActivity(MainActivity::class.java)
        mainActivity = controller
            .create()
            .get()
    }
    @Test
    fun `When loading main fragment the main fragment should be launched`(){
        val fragment =
            mainActivity.supportFragmentManager.findFragmentById(R.id.main_fragment_manager)
        Assert.assertNotNull(fragment)
    }

    @Test
    fun `Test bottom sheet if click is it open`(){
       val shadowActivity = Shadows.shadowOf(mainActivity)
        shadowActivity.clickMenuItem(R.id.plans_list_menu_item)
        val fragment =
            mainActivity.supportFragmentManager.findFragmentByTag("plan_list")
        Assert.assertNotNull(fragment)
    }
}
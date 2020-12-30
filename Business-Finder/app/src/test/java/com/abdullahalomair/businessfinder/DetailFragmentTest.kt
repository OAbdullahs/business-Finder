package com.abdullahalomair.businessfinder


import com.abdullahalomair.businessfinder.viewmodels.BusinessDetailViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.util.*

class DetailFragmentTest {
    private lateinit var viewModel:BusinessDetailViewModel

    @Before
    fun setUp(){
        viewModel = Mockito.mock(BusinessDetailViewModel::class.java)
    }
    @Test
    fun `Test if plan date set the value of start date should be changed`(){
       viewModel.planDate = Date()
        Assert.assertNotNull(viewModel.startDate)
    }
}
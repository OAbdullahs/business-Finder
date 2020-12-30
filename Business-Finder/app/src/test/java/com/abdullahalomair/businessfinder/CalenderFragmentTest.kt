package com.abdullahalomair.businessfinder

import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat

class CalenderFragmentTest {

    @Test
    fun `Test if the time sent from the server are converted correctly`(){
        val startTimeFromServer = 1040
        val endTimeFromServer = 1110
        val start = startTimeFromServer.toString().chunked(2).joinToString(separator = ":")
        val end = endTimeFromServer.toString().chunked(2).joinToString(separator = ":")
        val sdfStart = SimpleDateFormat("H:mm").parse(start)
        val sdfEnd = SimpleDateFormat("H:mm").parse(end)
        val finalStart = SimpleDateFormat("hh:mm aa").format(sdfStart)
        val finalEnd = SimpleDateFormat("hh:mm aa").format(sdfEnd)
        val  message = "The business is open from $finalStart to $finalEnd"
        val expectedResult = "The business is open from 10:40 AM to 11:10 AM"
        Assert.assertEquals(message, expectedResult)
    }
    @Test
    fun `Test if the time sent from the server are not converted correctly`(){
        var errorMassage = "Error"
        try {
        val startTimeFromServer = 40
        val endTimeFromServer = 1110
        val start = startTimeFromServer.toString().chunked(2).joinToString(separator = ":")
        val end = endTimeFromServer.toString().chunked(2).joinToString(separator = ":")
        val sdfStart = SimpleDateFormat("H:mm").parse(start)
        val sdfEnd = SimpleDateFormat("H:mm").parse(end)
        val finalStart = SimpleDateFormat("hh:mm aa").format(sdfStart)
        val finalEnd = SimpleDateFormat("hh:mm aa").format(sdfEnd)
        errorMassage = "The business is open from $finalStart to $finalEnd"
        }catch (e:Exception){}

        Assert.assertEquals(errorMassage, "Error")
    }
}
package com.abdullahalomair.businessfinder.controllers

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.abdullahalomair.businessfinder.*
import com.abdullahalomair.businessfinder.model.yelpmodel.BusinessDetails
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class CalenderDialogFragment:DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val getHours: BusinessDetails? = arguments?.getParcelable(HOURS_LIST)
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)
        var message =  context?.getString(R.string.error_time).toString()
        if (getHours != null) {
            try {
                val hours = getHours.hours[0].open[calendar.get(Calendar.DAY_OF_WEEK)]
                val start = hours.start.toString().chunked(2).joinToString(separator = ":")
                val end = hours.end.toString().chunked(2).joinToString(separator = ":")
                val sdfStart = SimpleDateFormat("H:mm").parse(start)
                val sdfEnd = SimpleDateFormat("H:mm").parse(end)
                val finalStart = SimpleDateFormat("hh:mm aa").format(sdfStart)
                val finalEnd = SimpleDateFormat("hh:mm aa").format(sdfEnd)
                message = "The business is open from $finalStart to $finalEnd"
            } catch (e:Exception){}
        }


        val dateListener =
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
                val resultDate: Date = GregorianCalendar(year, month, day).time
                val timeDialogFragment = TimeDialogFragment(message)
                val args = Bundle()
                args.putSerializable(TIME_PICKER, resultDate)
                timeDialogFragment.apply {
                setTargetFragment(this@CalenderDialogFragment, TIME_REQUEST_CODE)
                arguments = args
                show(this@CalenderDialogFragment.requireFragmentManager(),"")
            }

        }
        val dateBicker =  DatePickerDialog(
        requireContext(),
        dateListener,
        initialYear,
        initialMonth,
        initialDay
        )
        dateBicker.datePicker.minDate = calendar.time.time
        calendar.add(Calendar.DAY_OF_MONTH,2)
        dateBicker.datePicker.maxDate = calendar.time.time
        return dateBicker
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            TIME_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val timeData:Date? = data?.getSerializableExtra(TIME_PICKER) as Date?
                    if (timeData!= null){
                      val args = Bundle()
                        args.putSerializable(DATE_PICKER, timeData)
                        val intent = Intent().putExtras(args)
                        targetFragment?.onActivityResult(DATE_REQUEST_CODE,Activity.RESULT_OK,intent)

                    }

                }
            }
        }
    }
}
package com.abdullahalomair.businessfinder.controllers

import android.app.Activity
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.abdullahalomair.businessfinder.*
import java.text.SimpleDateFormat
import java.util.*

class TimeDialogFragment(private var message: String ="") : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date:Date? = arguments?.getSerializable(TIME_PICKER) as Date?
        val calendar = Calendar.getInstance()
        calendar.time = date
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH) + 1
        val initialDate = calendar.get(Calendar.DAY_OF_MONTH)
        var initialHour = calendar.get(Calendar.HOUR_OF_DAY)
        var initialMinute = calendar.get(Calendar.MINUTE)
        val dateListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            val formats = SimpleDateFormat("yyyy-MM-dd HH:mm")
            val resultDate: Date = formats.parse("$initialYear-$initialMonth-$initialDate $hourOfDay:$minute")
            val args = Bundle()

            args.putSerializable(TIME_PICKER, resultDate)
            val intent = Intent().putExtras(args)
            targetFragment?.onActivityResult(TIME_REQUEST_CODE, Activity.RESULT_OK,intent)

        }
        val timePicker = TimePickerDialog(
            requireContext(),
            dateListener,
            initialHour,
            initialMinute,
            false)

        if (message.isEmpty()){
            message = context?.getString(R.string.error_time)!!
        }
            timePicker.setMessage(message)
        return timePicker
    }
}
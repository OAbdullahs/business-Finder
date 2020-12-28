package com.abdullahalomair.businessfinder.controllers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.abdullahalomair.businessfinder.COLOR_NAME
import com.abdullahalomair.businessfinder.COLOR_REQUEST_CODE
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.ColorPickerDialogFragmentBinding
import com.skydoves.colorpickerview.ActionMode
import com.skydoves.colorpickerview.listeners.ColorListener
import com.skydoves.colorpickerview.listeners.ColorPickerViewListener

class ColorPickerDialogFragment: DialogFragment() {
    private lateinit var binding: ColorPickerDialogFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.color_picker_dialog_fragment,
            container, false)
        dialog?.window?.setGravity(Gravity.TOP)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initColorPicker()
    }

    private fun initColorPicker(){
        binding.colorPickerView.actionMode = ActionMode.LAST
        binding.colorPickerView.setColorListener(object : ColorListener{
            override fun onColorSelected(color: Int, fromUser: Boolean) {
                val args = Bundle()
                args.putInt(COLOR_NAME, color)

                val intent = Intent().putExtras(args)

                targetFragment?.onActivityResult(COLOR_REQUEST_CODE, Activity.RESULT_OK,intent)
            }

        })
    }
}
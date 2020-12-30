package com.abdullahalomair.businessfinder.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullahalomair.businessfinder.HOURS_DETAIL
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.TimeBottomSheetBinding
import com.abdullahalomair.businessfinder.model.yelpmodel.Hours
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TimeDetailBottomSheet: BottomSheetDialogFragment() {
private lateinit var binding:TimeBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.time_bottom_sheet,
            container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hours: Hours? = arguments?.getParcelable(HOURS_DETAIL)
        if (hours != null){
            binding.timeRecyclerview.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = TimeDetailAdapter(hours.open)
            }
        }
    }

    companion object{
        fun newInstance( hours: Hours):TimeDetailBottomSheet {
            val args = Bundle()
            args.putParcelable(HOURS_DETAIL,hours)
            return TimeDetailBottomSheet().apply {
                arguments = args
            }
        }
    }
}
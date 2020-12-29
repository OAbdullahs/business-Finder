package com.abdullahalomair.businessfinder.controllers

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.R
import com.abdullahalomair.businessfinder.databinding.PlanADayBottomSheetBinding
import com.abdullahalomair.businessfinder.model.planmodel.PlanModel
import com.abdullahalomair.businessfinder.utils.SimpleItemTouchCallback
import com.abdullahalomair.businessfinder.viewmodels.PlansBottomSheetViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val VISIBILITY_GONE = View.GONE
private const val VISIBILITY_VISIBLE = View.VISIBLE
class PlansBottomSheet: BottomSheetDialogFragment() {
    private lateinit var binding: PlanADayBottomSheetBinding
    private lateinit var planModels: List<PlanModel>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.plan_a_day_bottom_sheet,
            container, false
        )
        binding.viewModel = PlansBottomSheetViewModel()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel?.getPlansList()?.observe(
            viewLifecycleOwner, { listPlanModel ->
                if (listPlanModel != null) {
                    planModels = listPlanModel
                    binding.viewModel?.isListEmpty = VISIBILITY_GONE
                    binding.bottomSheetRecyclerView.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = PlansAdapter(planModels)
                    }


                    val itemTouchHelper = ItemTouchHelper(SimpleItemTouchCallback(::deletePlan))

                    itemTouchHelper.attachToRecyclerView(binding.bottomSheetRecyclerView)

                    if (listPlanModel.isEmpty()){
                        binding.viewModel?.isListEmpty = VISIBILITY_VISIBLE
                        binding.viewModel?.isListEmptyRV = VISIBILITY_GONE
                    }
                } else {
                    binding.viewModel?.isListEmptyRV = VISIBILITY_GONE
                }

            }
        )
    }

    private fun deletePlan (position:Int){
        binding.viewModel?.deleteAPlan(planModels[position])
    }


}
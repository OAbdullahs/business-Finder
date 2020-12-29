package com.abdullahalomair.businessfinder.controllers

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
import com.abdullahalomair.businessfinder.viewmodels.PlansBottomSheetViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val VISIBILITY_GONE = View.GONE
private const val VISIBILITY_VISIBLE = View.VISIBLE
class PlansBottomSheet: BottomSheetDialogFragment() {
    private lateinit var binding: PlanADayBottomSheetBinding

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
                if (listPlanModel.isNotEmpty()) {
                    binding.viewModel?.isListEmpty = VISIBILITY_GONE
                    binding.bottomSheetRecyclerView.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = PlansAdapter(listPlanModel)
                    }

                    val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(
                        0,
                        ItemTouchHelper.RIGHT
                    ) {
                        override fun onMove(
                            recyclerView: RecyclerView,
                            viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder
                        ): Boolean {
                            return false
                        }

                        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                            val position = viewHolder.absoluteAdapterPosition
                            binding.viewModel?.deleteAPlan(listPlanModel[position])
                            listPlanModel.removeAt(position)
                            binding.bottomSheetRecyclerView.adapter?.notifyItemRemoved(position)
                        }

                    }

                    val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
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



}
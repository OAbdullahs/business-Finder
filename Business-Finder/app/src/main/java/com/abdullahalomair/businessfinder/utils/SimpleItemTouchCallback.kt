package com.abdullahalomair.businessfinder.utils

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.abdullahalomair.businessfinder.R


class SimpleItemTouchCallback(private val position:(Int) ->Unit): ItemTouchHelper.SimpleCallback(
    0,
    ItemTouchHelper.RIGHT
) {

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val trashBinIcon = viewHolder.itemView.resources.getDrawable(
            R.drawable.delete_icon,
            null
        )
        trashBinIcon.bounds = Rect(
            60,
            viewHolder.itemView.top + 220,
            60 + trashBinIcon.intrinsicWidth,
            viewHolder.itemView.top + trashBinIcon.intrinsicHeight
                    + 220
        )
        c.clipRect(0f, viewHolder.itemView.top.toFloat(),
        dX, viewHolder.itemView.bottom.toFloat())
        if(dX < viewHolder.itemView.width/4) {
            c.drawColor(Color.GRAY)
        }
        else {
            c.drawColor(Color.RED)
        }
        trashBinIcon.draw(c)
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.absoluteAdapterPosition
        position(position)
        viewHolder.bindingAdapter?.notifyItemRemoved(position)
    }


}
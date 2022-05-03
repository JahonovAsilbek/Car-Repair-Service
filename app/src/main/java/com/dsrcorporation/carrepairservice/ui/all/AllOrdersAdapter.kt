package com.dsrcorporation.carrepairservice.ui.all

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dsrcorporation.carrepairservice.R
import com.dsrcorporation.carrepairservice.databinding.ItemOrderBinding
import com.dsrcorporation.carrepairservice.utils.ItemTouchHelperAdapter
import com.dsrcorporation.domain.models.order.Order
import java.text.SimpleDateFormat
import java.util.*

class AllOrdersAdapter : RecyclerView.Adapter<AllOrdersAdapter.VH>(), ItemTouchHelperAdapter {

    var onOrderClick: OnOrderClick? = null
    var data = ArrayList<Order>()

    inner class VH(var itemOrderBinding: ItemOrderBinding) : RecyclerView.ViewHolder(itemOrderBinding.root) {
        fun onBind(order: Order, position: Int) {
            itemOrderBinding.model.text = order.model
            var info = ""
            for (task in order.tasks) {
                info += task.task + "\n"
            }
            itemOrderBinding.info.text = info
            itemOrderBinding.date.text = SimpleDateFormat("dd.MM.yyyy").format(Date(order.date))

            itemOrderBinding.root.setOnClickListener {
                onOrderClick?.onClick(order, position)
            }

            if (order.isClosed) {
                itemOrderBinding.root.setBackgroundResource(R.color.green)
            } else {
                itemOrderBinding.root.setBackgroundResource(R.color.red)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(data[position], position)
    }

    override fun getItemCount(): Int = data.size

    fun addData(list: List<Order>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    interface OnOrderClick {
        fun onClick(order: Order, position: Int)
    }

    override fun onItemSwipe(position: Int, itemOrderBinding: ItemOrderBinding, isOpened: Boolean) {
        notifyItemChanged(position)
    }

}
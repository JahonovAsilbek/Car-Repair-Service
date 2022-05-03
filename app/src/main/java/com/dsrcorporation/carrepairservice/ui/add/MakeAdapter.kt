package com.dsrcorporation.carrepairservice.ui.add

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dsrcorporation.carrepairservice.databinding.ItemChooseDialogBinding
import com.dsrcorporation.domain.models.make.Make

class MakeAdapter : RecyclerView.Adapter<MakeAdapter.VH>() {

    private val data = ArrayList<Make>()
    var onMakeClick: OnMakeClick? = null

    inner class VH(var itemChooseDialogBinding: ItemChooseDialogBinding) : RecyclerView.ViewHolder(itemChooseDialogBinding.root) {
        fun onBind(make: Make) {
            itemChooseDialogBinding.tv.text = make.Make_Name

            itemChooseDialogBinding.tv.setOnClickListener {
                onMakeClick?.onClick(make)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemChooseDialogBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount(): Int = data.size

    interface OnMakeClick {
        fun onClick(make: Make)
    }

    fun addData(list: List<Make>) {
        data.addAll(list)
        notifyDataSetChanged()
        Log.d("AAAA", "addData:$list ")
    }


}
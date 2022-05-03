package com.dsrcorporation.carrepairservice.ui.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dsrcorporation.carrepairservice.databinding.ItemChooseDialogBinding
import com.dsrcorporation.domain.models.models.Model

class ModelAdapter : RecyclerView.Adapter<ModelAdapter.VH>() {

    private val data = ArrayList<Model>()
    var onModelClick: OnModelClick? = null

    inner class VH(var itemChooseDialogBinding: ItemChooseDialogBinding) : RecyclerView.ViewHolder(itemChooseDialogBinding.root) {
        fun onBind(make: Model) {
            itemChooseDialogBinding.tv.text = make.Model_Name

            itemChooseDialogBinding.tv.setOnClickListener {
                onModelClick?.onClick(make)
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

    interface OnModelClick {
        fun onClick(model: Model)
    }

    fun addData(list: List<Model>) {
        data.addAll(list)
        notifyDataSetChanged()
    }
}
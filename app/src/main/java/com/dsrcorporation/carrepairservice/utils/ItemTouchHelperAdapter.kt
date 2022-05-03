package com.dsrcorporation.carrepairservice.utils

import com.dsrcorporation.carrepairservice.databinding.ItemOrderBinding

interface ItemTouchHelperAdapter {
    fun onItemSwipe(position: Int, itemOrderBinding: ItemOrderBinding, isOpened:Boolean)
}
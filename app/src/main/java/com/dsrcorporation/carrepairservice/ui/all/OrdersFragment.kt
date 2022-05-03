package com.dsrcorporation.carrepairservice.ui.all

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.dsrcorporation.carrepairservice.App
import com.dsrcorporation.carrepairservice.R
import com.dsrcorporation.carrepairservice.databinding.FragmentOrdersBinding
import com.dsrcorporation.carrepairservice.utils.network.Resource
import com.dsrcorporation.carrepairservice.utils.vm.BindingFragment
import com.dsrcorporation.carrepairservice.vm.AllOrdersViewModel
import com.dsrcorporation.domain.models.order.Order
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrdersFragment : BindingFragment<FragmentOrdersBinding>() {

    private var position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt("position")
        }
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: AllOrdersViewModel by viewModels { factory }
    private lateinit var adapter: AllOrdersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.appComponent.inject(this)
        setHasOptionsMenu(true)
        setupUI()
        loadData()

    }

    private fun loadData() {
        when (position) {
            0 -> {
                lifecycleScope.launch {
                    viewModel.getAllorders().collect {
                        when (it) {
                            is Resource.Loading -> {}
                            is Resource.Error<*> -> {}
                            is Resource.Success<*> -> {
                                val list = it.data as List<Order>
                                adapter.addData(list)
                            }
                        }
                    }
                }
            }
            1 -> {
                lifecycleScope.launch {
                    viewModel.getOrderByStatus(isClosed = true).collect {
                        when (it) {
                            is Resource.Loading -> {}
                            is Resource.Error<*> -> {}
                            is Resource.Success<*> -> {
                                val list = it.data as List<Order>
                                adapter.addData(list)
                            }
                        }
                    }
                }
            }
            2 -> {
                lifecycleScope.launch {
                    viewModel.getOrderByStatus(isClosed = false).collect {
                        when (it) {
                            is Resource.Loading -> {}
                            is Resource.Error<*> -> {}
                            is Resource.Success<*> -> {
                                val list = it.data as List<Order>
                                adapter.addData(list)
                            }
                        }
                    }
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sort) {
            Toast.makeText(requireContext(), "Menu", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun setupUI() {
        adapter = AllOrdersAdapter()
        binding.rv.adapter = adapter
        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)
        binding.rv.addItemDecoration(itemDecoration)

        adapter.onOrderClick = object : AllOrdersAdapter.OnOrderClick {
            override fun onClick(order: Order, position: Int) {
                // open order info fragment
            }
        }

        val itemTouch = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                return makeMovementFlags(dragFlags, swipeFlags)
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemOrderBinding = (viewHolder as AllOrdersAdapter.VH).itemOrderBinding
                adapter.onItemSwipe(viewHolder.adapterPosition, itemOrderBinding, adapter.data[viewHolder.adapterPosition].isClosed)
                Log.d("AAAA", "onSwiped: ${adapter.data[viewHolder.adapterPosition].isClosed}")
                val status = adapter.data[viewHolder.adapterPosition].isClosed
                val id = adapter.data[viewHolder.adapterPosition].id
                viewModel.changeOrderStatus(!status, id!!)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouch)
        itemTouchHelper.attachToRecyclerView(binding.rv)

    }


    companion object {
        @JvmStatic
        fun newInstance(postion: Int) =
            OrdersFragment().apply {
                arguments = Bundle().apply {
                    putInt("position", postion)
                }
            }
    }

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentOrdersBinding::inflate
}
package com.dsrcorporation.carrepairservice.ui.all

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.dsrcorporation.carrepairservice.App
import com.dsrcorporation.carrepairservice.R
import com.dsrcorporation.carrepairservice.databinding.FragmentOrdersBinding
import com.dsrcorporation.carrepairservice.utils.network.Resource
import com.dsrcorporation.carrepairservice.utils.showToast
import com.dsrcorporation.carrepairservice.utils.vm.BindingFragment
import com.dsrcorporation.domain.models.order.Order
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrdersFragment : BindingFragment<FragmentOrdersBinding>() {

    private var position: Int? = null
    private var isClosed: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt("position")
            when (position) {
                0 -> {
                    isClosed = null
                }
                1 -> {
                    isClosed = true
                }
                2 -> {
                    isClosed = false
                }
            }
        }
        setHasOptionsMenu(true)
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: AllOrdersViewModel by viewModels { factory }
    private lateinit var adapter: OrdersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.appComponent.inject(this)
        setupUI()
        loadData()

    }

    private fun loadData() {
        lifecycleScope.launch {
            viewModel.getAllorders(isClosed = isClosed).collect {
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var name = false
        var nameDesc = false
        var date = false
        var dateDesc = false
        when (item.itemId) {
            R.id.language -> {

            }
            R.id.name -> {
                name = true
            }
            R.id.name_desc -> {
                nameDesc = true
            }
            R.id.date -> {
                date = true
            }
            R.id.date_desc -> {
                dateDesc = true
            }
        }
        lifecycleScope.launch {
            viewModel.getSortedOrder(
                isClosed, name = name, nameDesc = nameDesc, date = date, dateDesc = dateDesc
            ).collect {
                when (it) {
                    is Resource.Error<*> -> {
                        requireContext().showToast(it.message.toString())
                    }
                    Resource.Loading -> {}
                    is Resource.Success<*> -> {
                        val list = it.data as List<Order>
                        adapter.addData(list)
                    }
                }
            }
        }
        return true
    }

    private fun setupUI() {
        adapter = OrdersAdapter()
        binding.rv.adapter = adapter
        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)
        binding.rv.addItemDecoration(itemDecoration)

        adapter.onOrderClick = object : OrdersAdapter.OnOrderClick {
            override fun onClick(order: Order, position: Int) {
                val bundle = Bundle()
                bundle.putSerializable("order", order)
                findNavController().navigate(R.id.orderInfoFragment, bundle)
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
                val itemOrderBinding = (viewHolder as OrdersAdapter.VH).itemOrderBinding
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
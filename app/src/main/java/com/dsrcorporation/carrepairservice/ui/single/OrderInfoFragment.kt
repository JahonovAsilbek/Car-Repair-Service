package com.dsrcorporation.carrepairservice.ui.single

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.dsrcorporation.carrepairservice.App
import com.dsrcorporation.carrepairservice.R
import com.dsrcorporation.carrepairservice.databinding.FragmentOrderInfoBinding
import com.dsrcorporation.carrepairservice.ui.all.AllOrdersViewModel
import com.dsrcorporation.carrepairservice.utils.vm.BindingFragment
import com.dsrcorporation.domain.models.order.Order
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

private const val ARG_PARAM1 = "order"

class OrderInfoFragment : BindingFragment<FragmentOrderInfoBinding>() {

    private var order: Order? = null

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: AllOrdersViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            order = it.getSerializable(ARG_PARAM1) as Order
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupVM()
        setupUI()
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun setupUI() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.model.text = getString(R.string.model) + " " + order?.model
        binding.make.text = getString(R.string.make) + " " + order?.make
        binding.serialNumber.text = getString(R.string.serial_number) + " " + order?.serialNumber
        binding.vin.text = getString(R.string.vin) + " " + order?.vin
        binding.date.text = getString(R.string.date) + ": " + SimpleDateFormat("dd.MM.yyyy HH:MM:SS").format(Date(order!!.date))
        var tasks = ""
        order?.tasks?.forEach {
            tasks += "${getString(R.string.task)} $${it.cost} - ${it.task}\n"
        }
        binding.tasks.text = tasks

        when (order?.isClosed) {
            true -> {
                binding.open.isChecked = true
            }
            false -> {
                binding.close.isChecked = true
            }
            else -> {}
        }

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.open -> {
                    viewModel.changeOrderStatus(true, order?.id!!)
                }
                R.id.close -> {
                    viewModel.changeOrderStatus(false, order?.id!!)
                }
            }
        }
    }

    private fun setupVM() {
        App.appComponent.inject(this)
    }

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentOrderInfoBinding::inflate


}
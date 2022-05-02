package com.dsrcorporation.carrepairservice.ui.all

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.dsrcorporation.carrepairservice.App
import com.dsrcorporation.carrepairservice.databinding.FragmentAllOrdersBinding
import com.dsrcorporation.carrepairservice.utils.network.Resource
import com.dsrcorporation.carrepairservice.utils.vm.BindingFragment
import com.dsrcorporation.carrepairservice.vm.AllOrdersViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllOrdersFragment : BindingFragment<FragmentAllOrdersBinding>() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: AllOrdersViewModel by viewModels { factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.appComponent.inject(this)


        // tested if the code is working properly or not
        lifecycleScope.launch {
            viewModel.getVehicle("5UXWX7C5*BA").collect {
                when (it) {
                    is Resource.Error<*> -> {
                        Log.d("AAAA", "onCreateView:${it.message} ")
                    }
                    Resource.Loading -> {

                    }
                    is Resource.Success<*> -> {
                        Log.d("AAAA", "onCreateView:${it.data} ")
                    }
                }
            }
        }

    }

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentAllOrdersBinding::inflate
}
package com.dsrcorporation.carrepairservice.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.dsrcorporation.carrepairservice.R
import com.dsrcorporation.carrepairservice.databinding.FragmentMainBinding
import com.dsrcorporation.carrepairservice.ui.MainActivity
import com.dsrcorporation.carrepairservice.utils.vm.BindingFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : BindingFragment<FragmentMainBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setSupportActionBar(binding.toolbar)
        setupVM()
        setupUI()
    }

    private fun setupUI() {
        binding.addOrderBtn.setOnClickListener {
            findNavController().navigate(R.id.addOrderFragment)
        }
    }

    private fun setupVM() {
        val adapter = MainAdapter(requireActivity())
        binding.vp.adapter = adapter
        binding.vp.isUserInputEnabled = false

        TabLayoutMediator(binding.tabLayout, binding.vp) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.all)
                }
                1 -> {
                    tab.text = getString(R.string.open)
                }
                2 -> {
                    tab.text = getString(R.string.closed)
                }
            }
        }.attach()
    }

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentMainBinding::inflate

}
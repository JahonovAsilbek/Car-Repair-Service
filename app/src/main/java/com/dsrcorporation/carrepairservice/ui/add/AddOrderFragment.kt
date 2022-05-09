package com.dsrcorporation.carrepairservice.ui.add

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.dsrcorporation.carrepairservice.App
import com.dsrcorporation.carrepairservice.R
import com.dsrcorporation.carrepairservice.databinding.ChooseDialogBinding
import com.dsrcorporation.carrepairservice.databinding.FragmentAddOrderBinding
import com.dsrcorporation.carrepairservice.utils.*
import com.dsrcorporation.carrepairservice.utils.network.Resource
import com.dsrcorporation.carrepairservice.utils.vm.BindingFragment
import com.dsrcorporation.domain.models.make.Make
import com.dsrcorporation.domain.models.models.Model
import com.dsrcorporation.domain.models.order.Order
import com.dsrcorporation.domain.models.order.Task
import com.dsrcorporation.domain.models.vehicle.Vehicle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddOrderFragment : BindingFragment<FragmentAddOrderBinding>() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: AddOrderViewModel by viewModels { factory }
    private var makeID: Int = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupVM()
        setupUI()

    }

    private fun setupVM() {
        App.appComponent.inject(this)
    }

    private fun setupUI() {
        addTask()
        loadMakes()
        binding.addTask.setOnClickListener {
            setupUI()
        }

        binding.addOrder.setOnClickListener {
            addOrder()
        }

        binding.check.setOnClickListener {
            val vin = binding.vin.text()
            lifecycleScope.launch {
                viewModel.getVehicle(vin).collect {
                    when (it) {
                        is Resource.Error<*> -> {
                            requireContext().showToast(getString(R.string.vehicle_not_found))
                            binding.vin.setText("")
                        }
                        Resource.Loading -> {}
                        is Resource.Success<*> -> {
                            val vehicle = it.data as Vehicle
                            binding.make.text = vehicle.Make
                            binding.model.text = vehicle.Model
                        }
                    }
                }
            }
        }

        binding.make.setOnClickListener {
            showMakeDialog()
        }

        binding.model.setOnClickListener {
            showModelDialog()
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun showModelDialog() {
        if (makeID == -1) {
            requireContext().showToast(getString(R.string.choose_make))
        } else {
            val dialog = AlertDialog.Builder(requireContext(), R.style.RoundedCornersDialog)
            val view = ChooseDialogBinding.inflate(layoutInflater)
            val adapter = ModelAdapter()
            view.rvDialog.adapter = adapter

            val alert = dialog.create()
            alert.setView(view.root)
            alert.show()

            lifecycleScope.launch {
                viewModel.getModelsByMakeID(makeID).collect {
                    when (it) {
                        Resource.Loading -> {
                            view.progress.visible()
                            view.rvDialog.invisible()
                        }
                        is Resource.Error<*> -> {

                        }
                        is Resource.Success<*> -> {
                            view.progress.invisible()
                            view.rvDialog.visible()
                            val list = it.data as List<Model>
                            adapter.addData(list)
                        }
                    }
                }
            }

            val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)
            view.rvDialog.addItemDecoration(itemDecoration)
            adapter.onModelClick = object : ModelAdapter.OnModelClick {
                override fun onClick(model: Model) {
                    binding.model.text = model.Model_Name
                    alert.cancel()
                }
            }
        }
    }

    private fun showMakeDialog() {
        val dialog = AlertDialog.Builder(requireContext(), R.style.RoundedCornersDialog)
        val view = ChooseDialogBinding.inflate(layoutInflater)
        val adapter = MakeAdapter()
        view.rvDialog.adapter = adapter
        view.rvDialog.layoutManager = LinearLayoutManager(requireContext())

        val alert = dialog.create()
        alert.setView(view.root)
        alert.show()

        lifecycleScope.launch {
            viewModel.getAllMakes().collect {
                when (it) {
                    is Resource.Loading -> {
                        view.progress.visible()
                        view.rvDialog.invisible()
                    }
                    is Resource.Error<*> -> {}
                    is Resource.Success<*> -> {
                        view.progress.invisible()
                        view.rvDialog.visible()
                        val list = it.data as List<Make>
                        adapter.addData(list)
                    }
                }
            }
        }

        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)
        view.rvDialog.addItemDecoration(itemDecoration)
        adapter.onMakeClick = object : MakeAdapter.OnMakeClick {
            override fun onClick(make: Make) {
                binding.make.text = make.Make_Name
                makeID = make.Make_ID
                binding.model.text = ""
                alert.cancel()
            }
        }
    }

    private var makesData = ArrayList<Make>()
    private fun loadMakes() {
        lifecycleScope.launch {
            viewModel.getAllMakes().collect {
                when (it) {
                    is Resource.Loading -> {}
                    is Resource.Error<*> -> {}
                    is Resource.Success<*> -> {
                        val list = it.data as List<Make>
                        makesData.addAll(list)
                    }
                }
            }
        }

    }

    private fun addOrder() {
        val tasks = ArrayList<Task>()
        var allFieldsFull = true
        val taskContainer: LinearLayout = binding.taskContainer
        for (i in 0 until taskContainer.childCount) {
            val task: LinearLayout = taskContainer.getChildAt(i) as LinearLayout
            val taskName = task.getChildAt(0) as EditText
            val cost = task.getChildAt(1) as EditText

            Log.d("AAAA", "addOrder name:${taskName.text()} ")
            Log.d("AAAA", "addOrder cost:${cost.text()} ")
            if (taskName.text().isNotEmpty() && cost.text().isNotEmpty()) {
                tasks.add(Task(taskName.text(), cost.text().toInt()))
            } else {
                requireContext().showToast(getString(R.string.fill_all_fields))
                allFieldsFull = false
                break
            }
        }

        val vin = binding.vin.text()
        val brand = binding.make.text()
        val model = binding.model.text()
        val serialNumber = binding.serialNumber.text()

        if (vin.isNotEmpty() && brand.isNotEmpty() && model.isNotEmpty() && serialNumber.isNotEmpty() && tasks.isNotEmpty() && allFieldsFull) {
            val order = Order(model = model, make = brand, serialNumber = serialNumber, tasks = tasks, isClosed = false, date = System.currentTimeMillis(), vin = vin)
            viewModel.addOrder(order)
            findNavController().popBackStack()
        } else requireContext().showToast(getString(R.string.fill_all_fields))
    }

    private fun addTask() {
        val taskContainer: LinearLayout = binding.taskContainer

        val task1: LinearLayout = LinearLayout(requireContext())
        task1.layoutParams =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        task1.orientation = LinearLayout.HORIZONTAL

        val taskName = EditText(ContextThemeWrapper(requireContext(), R.style.edittext_style))
        taskName.hint = getString(R.string.hint_task)
        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50.dpToPx(resources.displayMetrics), 1.0f)
        layoutParams.marginEnd = 16.dpToPx(resources.displayMetrics)
        layoutParams.bottomMargin = 16.dpToPx(resources.displayMetrics)
        taskName.layoutParams = layoutParams
        taskName.setPadding(16.dpToPx(resources.displayMetrics), 0, 16.dpToPx(resources.displayMetrics), 0)
        taskName.setBackgroundResource(R.drawable.edittext_back)
        taskName.maxLines = 1
        taskName.ellipsize = TextUtils.TruncateAt.END


        val cost = EditText(ContextThemeWrapper(requireContext(), R.style.edittext_style))
        cost.inputType = InputType.TYPE_CLASS_NUMBER
        cost.hint = getString(R.string.hint_cost)
        cost.layoutParams = layoutParams
        cost.setPadding(16.dpToPx(resources.displayMetrics), 0, 16.dpToPx(resources.displayMetrics), 0)
        cost.setBackgroundResource(R.drawable.edittext_back)
        cost.ellipsize = TextUtils.TruncateAt.END

        task1.addView(taskName)
        task1.addView(cost)

        taskContainer.addView(task1)
    }

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentAddOrderBinding::inflate
}
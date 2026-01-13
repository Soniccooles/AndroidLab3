package com.itandrew.androidlab3.presenter

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.itandrew.androidlab3.R
import com.itandrew.androidlab3.databinding.FragmentMainBinding
import com.itandrew.androidlab3.di.appComponent
import com.itandrew.androidlab3.data.model.ColorInfo
import com.itandrew.androidlab3.UiState
import dev.androidbroadcast.vbpd.viewBinding
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)

    private var availableColors: List<ColorInfo> = emptyList()

    private var isUserInteractingWithBrightness = false
    private var isUserInteractingWithColorSpinner = false

    private var isInitialSetup = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.lightState.observe(viewLifecycleOwner) { uiState ->
            updateProgressBar()
            when (uiState) {
                is UiState.Loading -> {
                    binding.lightSwitch.isEnabled = false
                }
                is UiState.Success -> {
                    binding.lightSwitch.isEnabled = true
                    uiState.value?.let {
                        if (binding.lightSwitch.isChecked != it) {
                            binding.lightSwitch.isChecked = it
                        }
                    }
                }
                is UiState.Failure -> {
                    binding.lightSwitch.isEnabled = true
                }
            }
        }

        viewModel.colors.observe(viewLifecycleOwner) { uiState ->
            updateProgressBar()
            when (uiState) {
                is UiState.Loading -> {
                    binding.colorSpinner.isEnabled = false
                }
                is UiState.Success -> {
                    binding.colorSpinner.isEnabled = true
                    uiState.value?.let { colors ->
                        availableColors = colors
                        val colorNames = colors.map { it.name }
                        val adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            colorNames
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.colorSpinner.adapter = adapter

                        viewModel.currentColor.value?.let { currentColorUiState ->
                            if (currentColorUiState is UiState.Success) {
                                val index = colorNames.indexOf(currentColorUiState.value?.name)
                                if (index != -1 && binding.colorSpinner.selectedItemPosition != index) {
                                    binding.colorSpinner.setSelection(index, false)
                                }
                            }
                        }
                    }
                }
                is UiState.Failure -> {
                    binding.colorSpinner.isEnabled = true
                }
            }
        }

        viewModel.currentColor.observe(viewLifecycleOwner) { uiState ->
            updateProgressBar()
            if (isUserInteractingWithColorSpinner) return@observe

            when (uiState) {
                is UiState.Loading -> {
                    binding.colorSpinner.isEnabled = false
                }
                is UiState.Success -> {
                    binding.colorSpinner.isEnabled = true
                    uiState.value?.let { currentColor ->
                        val colorNames = availableColors.map { it.name }
                        val index = colorNames.indexOf(currentColor.name)
                        if (index != -1 && binding.colorSpinner.selectedItemPosition != index) {
                            binding.colorSpinner.setSelection(index, false)
                        }
                    }
                    isInitialSetup = false
                }
                is UiState.Failure -> {
                    binding.colorSpinner.isEnabled = true
                }
            }
        }

        viewModel.brightnessLevel.observe(viewLifecycleOwner) { uiState ->
            updateProgressBar()
            when (uiState) {
                is UiState.Loading -> {
                    binding.brightnessSeekbar.isEnabled = false
                }
                is UiState.Success -> {
                    binding.brightnessSeekbar.isEnabled = true
                    uiState.value?.let { brightnessLevel ->
                        binding.brightnessSeekbar.max = brightnessLevel.max
                        binding.brightnessSeekbar.min = brightnessLevel.min
                    }
                }
                is UiState.Failure -> {
                    binding.brightnessSeekbar.isEnabled = true
                }
            }
        }

        viewModel.currentBrightness.observe(viewLifecycleOwner) { uiState ->
            updateProgressBar()
            if (isUserInteractingWithBrightness) return@observe

            when (uiState) {
                is UiState.Loading -> {
                    binding.brightnessSeekbar.isEnabled = false
                }
                is UiState.Success -> {
                    binding.brightnessSeekbar.isEnabled = true
                    uiState.value?.let { currentLevel ->
                        if (binding.brightnessSeekbar.progress != currentLevel) {
                            binding.brightnessSeekbar.progress = currentLevel
                        }
                    }
                }
                is UiState.Failure -> {
                    binding.brightnessSeekbar.isEnabled = true
                }
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            if (message.isNullOrEmpty()) {
                binding.tvErrorMessage.visibility = View.GONE
            } else {
                binding.tvErrorMessage.text = message
                binding.tvErrorMessage.visibility = View.VISIBLE
            }
        }
    }

    private fun setupListeners() {
        binding.lightSwitch.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (compoundButton.isPressed) {
                viewModel.setLightState(isChecked)
            }
        }

        binding.colorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (binding.colorSpinner.adapter == null || (binding.colorSpinner.adapter?.count ?: 0) == 0) {
                    return
                }

                if (isInitialSetup) return

                val selectedColorName = parent?.getItemAtPosition(position).toString()
                val currentViewModelColorName = (viewModel.currentColor.value as? UiState.Success)?.value?.name

                if (selectedColorName != currentViewModelColorName) {
                    viewModel.setColor(selectedColorName)
                }
                isUserInteractingWithColorSpinner = false
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        binding.brightnessSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                isUserInteractingWithBrightness = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (isInitialSetup) return

                isUserInteractingWithBrightness = false
                seekBar?.let {
                    val currentLevel = (viewModel.currentBrightness.value as? UiState.Success)?.value
                    if (currentLevel == null || it.progress != currentLevel) {
                        viewModel.setBrightness(it.progress)
                    }
                }
            }
        })
    }

    private fun updateProgressBar() {
        val anyLoading = viewModel.lightState.value is UiState.Loading ||
                viewModel.colors.value is UiState.Loading ||
                viewModel.brightnessLevel.value is UiState.Loading ||
                viewModel.currentBrightness.value is UiState.Loading ||
                viewModel.currentColor.value is UiState.Loading

        binding.progressBar.visibility = if (anyLoading) View.VISIBLE else View.GONE
    }
}
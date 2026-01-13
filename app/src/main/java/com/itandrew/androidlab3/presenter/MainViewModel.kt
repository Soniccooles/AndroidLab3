package com.itandrew.androidlab3.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itandrew.androidlab3.data.model.BrightnessLevel
import com.itandrew.androidlab3.data.model.ColorInfo
import com.itandrew.androidlab3.domain.GetBrightnessLevelsUseCase
import com.itandrew.androidlab3.domain.GetColorsUseCase
import com.itandrew.androidlab3.domain.GetCurrentBrightnessUseCase
import com.itandrew.androidlab3.domain.GetCurrentColorUseCase
import com.itandrew.androidlab3.domain.GetLightStateUseCase
import com.itandrew.androidlab3.domain.SetBrightnessUseCase
import com.itandrew.androidlab3.domain.SetColorUseCase
import com.itandrew.androidlab3.domain.TurnLightOffUseCase
import com.itandrew.androidlab3.domain.TurnLightOnUseCase
import kotlinx.coroutines.launch
import com.itandrew.androidlab3.UiState
import com.itandrew.androidlab3.toUiState
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getLightStateUseCase: GetLightStateUseCase,
    private val getColorsUseCase: GetColorsUseCase,
    private val getCurrentColorUseCase: GetCurrentColorUseCase,
    private val getBrightnessLevelsUseCase: GetBrightnessLevelsUseCase,
    private val getCurrentBrightnessUseCase: GetCurrentBrightnessUseCase,
    private val turnLightOnUseCase: TurnLightOnUseCase,
    private val turnLightOffUseCase: TurnLightOffUseCase,
    private val setColorUseCase: SetColorUseCase,
    private val setBrightnessUseCase: SetBrightnessUseCase,
) : ViewModel() {
    private val _lightState = MutableLiveData<UiState<Boolean>>()
    val lightState: LiveData<UiState<Boolean>> = _lightState
    private val _colors = MutableLiveData<UiState<List<ColorInfo>>>()
    val colors: LiveData<UiState<List<ColorInfo>>> = _colors
    private val _brightnessLevel = MutableLiveData<UiState<BrightnessLevel>>()
    val brightnessLevel: LiveData<UiState<BrightnessLevel>> = _brightnessLevel
    private val _currentBrightness = MutableLiveData<UiState<Int>>()
    val currentBrightness: LiveData<UiState<Int>> = _currentBrightness
    private val _currentColor = MutableLiveData<UiState<ColorInfo>>()
    val currentColor: LiveData<UiState<ColorInfo>> = _currentColor
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        loadData()
    }

    // Загружаем текущее состояние лампочки
    private fun loadData() {
        viewModelScope.launch {
            postUiState(_lightState, UiState.Loading)
            postUiState(_colors, UiState.Loading)
            postUiState(_brightnessLevel, UiState.Loading)
            postUiState(_currentBrightness, UiState.Loading)
            postUiState(_currentColor, UiState.Loading)

            postUiState(_lightState, getLightStateUseCase().toUiState())
            postUiState(_colors, getColorsUseCase().toUiState())
            postUiState(_brightnessLevel, getBrightnessLevelsUseCase().toUiState())
            postUiState(_currentBrightness, getCurrentBrightnessUseCase().toUiState())
            postUiState(_currentColor, getCurrentColorUseCase().toUiState())


        }
    }

    private fun <T> postUiState(liveData: MutableLiveData<UiState<T>>, uiState: UiState<T>) {
        liveData.postValue(uiState)
        if (uiState is UiState.Failure)
            _errorMessage.postValue(uiState.message)
        else
            _errorMessage.postValue("")
    }

    fun setLightState(isOn: Boolean) {
        viewModelScope.launch {
            _lightState.postValue(UiState.Loading)
            _errorMessage.postValue("")

            val setResult = if (isOn) {
                turnLightOnUseCase().toUiState()
            } else {
                turnLightOffUseCase().toUiState()
            }

            if (setResult is UiState.Success) {
                val currentStateResult = getLightStateUseCase().toUiState()
                postUiState(_lightState, currentStateResult)
            } else if (setResult is UiState.Failure) {
                _errorMessage.postValue("Failed to switch light: ${setResult.message}")
                postUiState(_lightState, getLightStateUseCase().toUiState())
            } else {
                _errorMessage.postValue("Unknown error while trying to switch light. Returned: $setResult")
                postUiState(_lightState, getLightStateUseCase().toUiState())
            }
        }
    }

    fun setBrightness(level: Int) {
        viewModelScope.launch {
            _currentBrightness.postValue(UiState.Loading)
            _errorMessage.postValue("")
            val setBrightnessResult = setBrightnessUseCase(level).toUiState()
            if (setBrightnessResult is UiState.Success) {
                val getCurrentBrightnessResult = getCurrentBrightnessUseCase().toUiState()
                postUiState(_currentBrightness, getCurrentBrightnessResult)
            }

            else if (setBrightnessResult is UiState.Failure) {
                _errorMessage.postValue("Failed to set brightness: " + setBrightnessResult.message)
            }
            else {
                _errorMessage.postValue("Unknown error while setting brightness. Returned: $setBrightnessResult")
            }
        }
    }

    fun setColor(color: String) {
        viewModelScope.launch {
            _currentColor.postValue(UiState.Loading)
            _errorMessage.postValue("")

            val setColorResult = setColorUseCase(color).toUiState()

            if (setColorResult is UiState.Success) {
                val getCurrentColorResult = getCurrentColorUseCase().toUiState()
                postUiState(_currentColor, getCurrentColorResult)
            }
            else if (setColorResult is UiState.Failure) {
                _errorMessage.postValue("Failed to set color: ${setColorResult.message}")
            }
            else {
                _errorMessage.postValue("Unknown error while setting color. Returned: ${setColorResult}")
            }
        }
    }
}
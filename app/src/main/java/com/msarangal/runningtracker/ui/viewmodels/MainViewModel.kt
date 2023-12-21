package com.msarangal.runningtracker.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msarangal.runningtracker.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: MainRepository
) : ViewModel() {
    private var _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    private var _sharedFlow = MutableSharedFlow<Int>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    private var _numLiveData: MutableLiveData<Int> = MutableLiveData()
    val numLiveData: LiveData<Int> = _numLiveData

    val numFlow = flow {
        repeat(times = 5) { num ->
            emit(num)
            delay(2000L)
        }
    }

    fun updateStateFlow(num: Int) {
        _stateFlow.value = num
    }

    fun updateSharedFlow(num: Int) {
        viewModelScope.launch {
            _sharedFlow.emit(num)
            delay(1000)
            _sharedFlow.emit(num * 2)
        }
    }

    fun updateLiveData(num: Int) {
        _numLiveData.value = num
    }
}
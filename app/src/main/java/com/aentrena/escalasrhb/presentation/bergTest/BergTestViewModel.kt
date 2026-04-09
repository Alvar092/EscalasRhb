package com.aentrena.escalasrhb.presentation.bergTest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aentrena.escalasrhb.domain.model.scales.BergItem
import com.aentrena.escalasrhb.domain.model.scales.BergTest
import com.aentrena.escalasrhb.domain.useCases.scales.SaveBergTestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class BergTestViewModel(
    val test: BergTest,
    val items: List<BergItem>,
    private val saveBerg: SaveBergTestUseCase
): ViewModel() {
    private val _currentItemIndex = MutableStateFlow(0)
    val currentItemIndex: StateFlow<Int> = _currentItemIndex.asStateFlow()

    val currentItem = items[currentItemIndex.value]

    private val _selectedScoreItem = MutableStateFlow<Int?>
    val selectedScoreItem: StateFlow<Int> = _selectedScoreItem.asStateFlow()

    val currentItemDefinition

    private var timerJob: Job? = null

    fun startTimer() {
        timerJob = viewModelScope.launch {
            while(true) {
                delay(100)
                _elapsedTime.value += 0.1
            }
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    suspend fun finishTest() {
        // TODO: saveBerg(test = test)
    }

}
package com.aentrena.escalasrhb.presentation.bergTest

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aentrena.escalasrhb.domain.model.scales.BergItem
import com.aentrena.escalasrhb.domain.model.scales.BergTest
import com.aentrena.escalasrhb.domain.useCases.scales.GetBergByIdUseCase
import com.aentrena.escalasrhb.domain.useCases.scales.SaveBergTestUseCase
import com.aentrena.escalasrhb.presentation.bergTest.resources.BergItemCatalog
import com.aentrena.escalasrhb.presentation.bergTest.resources.BergItemDefinition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

sealed class BergTestUiState {
    object Loading: BergTestUiState()
    data class Ready(val test: BergTest, val items: List<BergItem>): BergTestUiState()
    object Error: BergTestUiState()
}

@HiltViewModel
class BergTestViewModel @Inject constructor(
    private val saveBerg: SaveBergTestUseCase,
    private val getBerg: GetBergByIdUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val testId: UUID = UUID.fromString(checkNotNull(savedStateHandle["testId"]))

    private val _uiState = MutableStateFlow<BergTestUiState>(BergTestUiState.Loading)
    val uiState: StateFlow<BergTestUiState> = _uiState.asStateFlow()

    private val _test = MutableStateFlow<BergTest?>(null)
    val test: StateFlow<BergTest?> = _test.asStateFlow()

    val items = mutableStateListOf<BergItem>()

    init {
        viewModelScope.launch {
            getBerg(testId)
                .collect { test ->
                    if (test != null) {
                        items.clear()
                        items.addAll(test.items)
                        _test.value = test
                        _uiState.value = BergTestUiState.Ready(test, test.items)
                    } else {
                        _uiState.value = BergTestUiState.Error
                    }
                }
        }
        }

    var _currentItemIndex = MutableStateFlow(0)
    var currentItemIndex: StateFlow<Int> = _currentItemIndex.asStateFlow()

    val currentItem: BergItem
        get() = items[currentItemIndex.value]

    private var _selectedScoreItem = MutableStateFlow<Int?>(null)
    var selectedScoreItem: StateFlow<Int?> = _selectedScoreItem.asStateFlow()

    val currentItemDefinition: BergItemDefinition
        get() = BergItemCatalog.definitions[currentItem.itemType]
                ?: error("No definition found for item type ${currentItem.itemType}")

    private val _elapsedTime = MutableStateFlow(0.0)
    val elapsedTime: StateFlow<Double> = _elapsedTime.asStateFlow()

    val formattedTime: StateFlow<String> = _elapsedTime
        .map { time ->
            val minutes = time.toInt() / 60
            val seconds = time.toInt() % 60
            val millis = ((time % 1) * 10).toInt()
            String.format("%02d:%02d.%01d", minutes, seconds, millis)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = "00:00.0"
        )

    val totalScore: Int
        get() = items.mapNotNull{it.score}.sum()

    fun selectScore(score: Int) {
        _selectedScoreItem.value = score
        items[currentItemIndex.value].score = score
    }


    private var timerJob: Job? = null

    private val _isTimerRunning = MutableStateFlow(false)
    val isTimerRunning: StateFlow<Boolean> = _isTimerRunning.asStateFlow()

    fun startTimer() {
        _isTimerRunning.value = true
        timerJob = viewModelScope.launch {
            while(true) {
                delay(100)
                _elapsedTime.value += 0.1
            }
        }
    }

    fun stopTimer() {
        _isTimerRunning.value = false
        timerJob?.cancel()
        timerJob = null
    }

    fun saveAndStop() {
        stopTimer()
        _elapsedTime.value = 0.0
        items[currentItemIndex.value].timeRecorded = null
        items[currentItemIndex.value].score = null
        _selectedScoreItem.value = items[currentItemIndex.value].score
    }

    fun resetTimer() {
        stopTimer()
        _elapsedTime.value = 0.0
        items[currentItemIndex.value].timeRecorded = null
        items[currentItemIndex.value].score = null
        _selectedScoreItem.value = null
    }

    fun nextItem() {
        if(isTimerRunning.value == true) {
            stopTimer()
        }
        if (currentItemIndex.value >= items.size - 1) return
        _currentItemIndex.value++
        _selectedScoreItem.value = items[currentItemIndex.value].score

        //Save time
        _elapsedTime.value = items[currentItemIndex.value].timeRecorded ?: 0.0
    }

    fun backItem() {
        if(isTimerRunning.value == true) {
            stopTimer()
        }

        if (currentItemIndex.value > 0) {
            _currentItemIndex.value--
            _selectedScoreItem.value = items[currentItemIndex.value].score

            //Save time
            _elapsedTime.value = items[currentItemIndex.value].timeRecorded ?: 0.0
        }

    }

    suspend fun finishTest() {
        // TODO: saveBerg(test = test)
    }

}
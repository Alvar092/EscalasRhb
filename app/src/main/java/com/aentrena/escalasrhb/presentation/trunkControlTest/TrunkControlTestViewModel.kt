package com.aentrena.escalasrhb.presentation.trunkControlTest

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aentrena.escalasrhb.domain.model.scales.BodySide
import com.aentrena.escalasrhb.domain.model.scales.TrunkControlTest
import com.aentrena.escalasrhb.domain.model.scales.TrunkControlTestItem
import com.aentrena.escalasrhb.domain.useCases.scales.GetTrunkControlByIdUseCase
import com.aentrena.escalasrhb.domain.useCases.scales.SaveTrunkControlTestUseCase
import com.aentrena.escalasrhb.presentation.trunkControlTest.resources.TrunkControlItemCatalog
import com.aentrena.escalasrhb.presentation.trunkControlTest.resources.TrunkControlItemDefinition
import com.aentrena.escalasrhb.analytics.CrashlyticsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

sealed class TrunkControlUiState {
    object Loading: TrunkControlUiState()
    data class Ready(val test: TrunkControlTest, val items: List<TrunkControlTestItem>): TrunkControlUiState()
    object Error: TrunkControlUiState()
}

@HiltViewModel
class TrunkControlTestViewModel @Inject constructor(
    private val saveTrunkControlTest: SaveTrunkControlTestUseCase,
    private val getTrunkControlTest: GetTrunkControlByIdUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val testId: UUID = UUID.fromString(checkNotNull(savedStateHandle["testId"])).also {
        CrashlyticsHelper.setScreen("trunk_control_test")
        CrashlyticsHelper.setTestType("TRUNK_CONTROL_TEST")
        CrashlyticsHelper.setTestId(it.toString())
    }

    private val _uiState = MutableStateFlow<TrunkControlUiState>(TrunkControlUiState.Loading)
    val uiState: StateFlow<TrunkControlUiState> = _uiState.asStateFlow()

    private val _test = MutableStateFlow<TrunkControlTest?>(null)
    val test: StateFlow<TrunkControlTest?> = _test.asStateFlow()

    val items = mutableStateListOf<TrunkControlTestItem>()

    private val _showSideDialog = MutableStateFlow(false)
    val showSideDialog: StateFlow<Boolean> = _showSideDialog.asStateFlow()

    private val _isLastItem = MutableStateFlow(false)
    val isLastItem: StateFlow<Boolean> = _isLastItem.asStateFlow()

    init {
        viewModelScope.launch {
            getTrunkControlTest(testId)
                .collect { test ->
                    if (test != null) {
                        items.clear()
                        items.addAll(test.items)

                        val currentSide = _test.value?.side
                        _test.value = test.copy(side = currentSide ?: test.side)
                        Log.d("NAV", "Lado seleccionado ${test.side}" )

                        _uiState.value = TrunkControlUiState.Ready(test, test.items)
                        if (_test.value?.side == null) {
                            _showSideDialog.value = true
                        }
                    } else {
                        _uiState.value = TrunkControlUiState.Error
                    }
                }
        }
    }

    var _currentItemIndex = MutableStateFlow(0)
    var currentItemIndex: StateFlow<Int> = _currentItemIndex.asStateFlow()

    val currentItem: TrunkControlTestItem
        get() = items[currentItemIndex.value]

    private var _selectedScoreItem = MutableStateFlow<Int?>(null)
    var selectedScoreItem: StateFlow<Int?> = _selectedScoreItem.asStateFlow()

    val currentItemDefinition: TrunkControlItemDefinition
        get() = TrunkControlItemCatalog.definitions[currentItem.itemType]
            ?: error("No definition found for item type ${currentItem.itemType}")

    val totalScore: Int
        get() = items.mapNotNull{it.score}.sum()

    fun onSideSelected(side: BodySide) {
        _showSideDialog.value = false
        _test.update { currentTest -> currentTest?.copy(side = side) }
        CrashlyticsHelper.setSide(side.name)
    }

    fun selectScore(score: Int) {
        _selectedScoreItem.value = score
        items[currentItemIndex.value].score = score
        CrashlyticsHelper.setCurrentScore(score)
    }

    fun nextItem() {
        if (currentItemIndex.value >= items.size - 1) return
        _currentItemIndex.value++
        _selectedScoreItem.value = items[currentItemIndex.value].score
        _isLastItem.value = currentItemIndex.value == items.size - 1
        CrashlyticsHelper.setItemIndex(_currentItemIndex.value)
    }

    fun backItem() {
        if (currentItemIndex.value > 0) {
            _currentItemIndex.value--
            _selectedScoreItem.value = items[currentItemIndex.value].score
            CrashlyticsHelper.setItemIndex(_currentItemIndex.value)
        }
    }

    fun finishTest() {
        viewModelScope.launch {
            test.value?.let { saveTrunkControlTest(it) }
            Log.d("REPO", "Test guardado ${test.value}")
        }
    }


}
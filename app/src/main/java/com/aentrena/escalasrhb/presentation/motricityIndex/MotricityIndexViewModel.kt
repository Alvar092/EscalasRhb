package com.aentrena.escalasrhb.presentation.motricityIndex

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aentrena.escalasrhb.domain.model.scales.BergItem
import com.aentrena.escalasrhb.domain.model.scales.BergTest
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexItem
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexTest
import com.aentrena.escalasrhb.domain.useCases.scales.GetMotricityByIdUseCase
import com.aentrena.escalasrhb.domain.useCases.scales.SaveMotricityIndexUseCase
import com.aentrena.escalasrhb.presentation.bergTest.BergTestUiState
import com.aentrena.escalasrhb.presentation.bergTest.resources.BergItemCatalog
import com.aentrena.escalasrhb.presentation.bergTest.resources.BergItemDefinition
import com.aentrena.escalasrhb.presentation.motricityIndex.resources.MotricityIndexCatalog
import com.aentrena.escalasrhb.presentation.motricityIndex.resources.MotricityItemDefinition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

sealed class MotricityIndexUiState {
    object Loading: MotricityIndexUiState()
    data class Ready(val test: MotricityIndexTest, val items: List<MotricityIndexItem>): MotricityIndexUiState()
    object Error: MotricityIndexUiState()
}

@HiltViewModel
class MotricityIndexViewModel @Inject constructor(
    private val saveMotricity: SaveMotricityIndexUseCase,
    private val getMotricity: GetMotricityByIdUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val testId: UUID = UUID.fromString(checkNotNull(savedStateHandle["testId"]))

    private val _uiState = MutableStateFlow<MotricityIndexUiState>(MotricityIndexUiState.Loading)
    val uiState: StateFlow<MotricityIndexUiState> = _uiState.asStateFlow()

    private val _test = MutableStateFlow<MotricityIndexTest?>(null)
    val test: StateFlow<MotricityIndexTest?> = _test.asStateFlow()

    val items = mutableStateListOf<MotricityIndexItem>()

    private val _isLastItem = MutableStateFlow(false)
    val isLastItem: StateFlow<Boolean> = _isLastItem.asStateFlow()


    init {
        viewModelScope.launch {
            getMotricity(testId)
                .collect { test ->
                    if (test != null) {
                        items.clear()
                        items.addAll(test.items)
                        _test.value = test
                        _uiState.value = MotricityIndexUiState.Ready(test, test.items)
                    } else {
                        _uiState.value = MotricityIndexUiState.Error
                    }
                }
        }
    }

    var _currentItemIndex = MutableStateFlow(0)
    var currentItemIndex: StateFlow<Int> = _currentItemIndex.asStateFlow()

    val currentItem: MotricityIndexItem
        get() = items[currentItemIndex.value]

    private var _selectedScoreItem = MutableStateFlow<Int?>(null)
    var selectedScoreItem: StateFlow<Int?> = _selectedScoreItem.asStateFlow()

    val currentItemDefinition: MotricityItemDefinition
        get() = MotricityIndexCatalog.definitions[currentItem.itemType]
            ?: error("No definition found for item type ${currentItem.itemType}")

    val upperLimbScore: Int
        get() = items.filter { it.itemType.isUpperLimb }
            .sumOf{ it.score ?: 0 } + 1

    val loweLimbScore: Int
        get() = items.filter { it.itemType.isLowerLimb }
            .sumOf { it.score ?: 0 } + 1

    fun selectScore(score: Int) {
        _selectedScoreItem.value = score
        items[currentItemIndex.value].score = score
    }

    fun nextItem() {
        if (currentItemIndex.value >= items.size - 1) return
        _currentItemIndex.value++
        _selectedScoreItem.value = items[currentItemIndex.value].score
        _isLastItem.value = currentItemIndex.value == items.size -1
    }

    fun backItem() {
        if (currentItemIndex.value > 0) {
            _currentItemIndex.value--
            _selectedScoreItem.value = items[currentItemIndex.value].score
        }
    }

    fun finishTest() {
        viewModelScope.launch {
            test.value?.let { saveMotricity(it) }
            Log.d("REPO", "Test guardado ${test.value}")
        }
    }


}
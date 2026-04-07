package com.aentrena.escalasrhb.presentation.scalesMenu

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.model.TestType
import com.aentrena.escalasrhb.domain.model.patients.Patient
import com.aentrena.escalasrhb.domain.useCases.scales.CreateTestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ScalesMenuViewModel @Inject constructor(
    private var createTestUseCase: CreateTestUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

   private val _selectedPatient = MutableStateFlow<Patient?>(null)
    val selectedPatient: StateFlow<Patient?> = _selectedPatient

    fun setSelectedPatient(patient: Patient) {
        _selectedPatient.value = patient
    }
    val _createdTest = MutableStateFlow<ClinicalTest?>(null)

    init {
        Log.d("ScalesMenuVM", "ViewModel creado, selectedPatient: ${savedStateHandle.get<Patient?>("selected_patient")}")
    }

    val testType: TestType =
        savedStateHandle.get<String>("testType")
            ?.let { TestType.valueOf(it)}
            ?: error("testType argument is required")


    val isStartButtonEnabled: Boolean
        get() = selectedPatient.value != null && _createdTest.value != null

    val patientDisplayName: String
        get() = selectedPatient.value?.let {"Paciente: ${it.name}"} ?: "Seleccionar paciente"
}
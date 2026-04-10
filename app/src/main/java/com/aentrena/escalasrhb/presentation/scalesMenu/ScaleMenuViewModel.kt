package com.aentrena.escalasrhb.presentation.scalesMenu

import android.util.Log
import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.model.TestType
import com.aentrena.escalasrhb.domain.model.patients.Patient
import com.aentrena.escalasrhb.domain.useCases.patient.GetPatientsUseCase
import com.aentrena.escalasrhb.domain.useCases.scales.CreateTestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ScaleMenuViewModel @Inject constructor(
    private var getPatients: GetPatientsUseCase,
    private var createTest: CreateTestUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val patients: StateFlow<List<Patient>> = getPatients()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

   private val _selectedPatient = MutableStateFlow<Patient?>(null)
    val selectedPatient: StateFlow<Patient?> = _selectedPatient

    fun setSelectedPatient(patient: Patient) {
        _selectedPatient.value = patient
        createTestForPatient()
    }
    val _createdTest = MutableStateFlow<ClinicalTest?>(null)
    val createdTest: StateFlow<ClinicalTest?> = _createdTest


    val isStartButtonEnabled: StateFlow<Boolean> =
        combine(selectedPatient, _createdTest) { patient, test ->
            patient != null && test != null
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false
        )

    val testType: TestType =
        savedStateHandle.get<String>("testType")
            ?.let { TestType.valueOf(it)}
            ?: error("testType argument is required")

    val patientDisplayName: String
        get() = selectedPatient.value?.let {"Paciente: ${it.name}"} ?: "Seleccionar paciente"


    private fun createTestForPatient() {
        val patient = selectedPatient.value ?: return

        viewModelScope.launch {
                val test  = createTest(
                    type = testType,
                    patientId = patient.id,
                    side = null
                )
            _createdTest.value = test
            Log.d("VM", "Test creado: ${_createdTest.value}")
        }
    }
}
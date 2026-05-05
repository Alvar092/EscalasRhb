package com.aentrena.escalasrhb.presentation.patients

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aentrena.escalasrhb.domain.model.patients.ClinicalHistory
import com.aentrena.escalasrhb.domain.model.patients.Patient
import com.aentrena.escalasrhb.domain.useCases.patient.GetPatientByIdUseCase
import com.aentrena.escalasrhb.domain.useCases.patient.GetPatientTestsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PatientsDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPatientByIdUseCase: GetPatientByIdUseCase,
    private val getPatientTestsUseCase: GetPatientTestsUseCase
): ViewModel() {

    private val patientId: UUID = UUID.fromString(checkNotNull(savedStateHandle["patientId"]))

    val patient: StateFlow<Patient?> = getPatientByIdUseCase(patientId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val tests: StateFlow<List<ClinicalHistory>> = getPatientTestsUseCase(patientId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}
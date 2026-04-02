package com.aentrena.escalasrhb.presentation.patients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aentrena.escalasrhb.domain.model.patients.Patient
import com.aentrena.escalasrhb.domain.useCases.patient.CreatePatientUseCase
import com.aentrena.escalasrhb.domain.useCases.patient.GetPatientsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PatientsViewModel @Inject constructor(
    private val getPatients: GetPatientsUseCase,
    private val createPatient: CreatePatientUseCase
): ViewModel() {

    val patients: StateFlow<List<Patient>> = getPatients()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun addPatient(name: String, birthDate: Long) {
        viewModelScope.launch {
            val newPatient = Patient(id = UUID.randomUUID(), name = name, dateOfBirth = birthDate)
            createPatient(newPatient)
        }
    }

    fun onEditPatient(patient: Patient) {
        // TODO: editar paciente
    }

    fun onDeletePatient(patient: Patient) {
        viewModelScope.launch {
            // TODO: borrar paciente
        }
    }
}
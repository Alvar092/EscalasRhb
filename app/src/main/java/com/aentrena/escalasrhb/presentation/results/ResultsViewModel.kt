package com.aentrena.escalasrhb.presentation.results

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aentrena.escalasrhb.data.services.pdf.PdfGenerator
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.model.patients.Patient
import com.aentrena.escalasrhb.domain.useCases.patient.GetPatientByIdUseCase
import com.aentrena.escalasrhb.domain.useCases.scales.GetTestResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val getTest: GetTestResultUseCase,
    private val getPatient: GetPatientByIdUseCase,
    private val pdfGenerator: PdfGenerator,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val testId: String = checkNotNull(savedStateHandle["testId"])

    private val _patient = MutableStateFlow<Patient?>(null)
    val patient: StateFlow<Patient?> = _patient.asStateFlow()

    private val _test = MutableStateFlow<ClinicalTest?>(null)
    val test: StateFlow<ClinicalTest?> = _test.asStateFlow()

    val formattedDate: StateFlow<String> = _test
        .filterNotNull()
        .map { test ->
            val instant = Instant.ofEpochMilli(test.date)
            val formatter = DateTimeFormatter
                .ofPattern("dd 'de' MMMM 'de' yyyy", Locale("es"))
                .withZone(ZoneId.systemDefault())
            formatter.format(instant)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    init {
        loadTest()
    }

    private fun loadTest() {
        viewModelScope.launch {
            val id = UUID.fromString(testId)
            getTest(id)
                .catch { "No test found" }
                .filterNotNull()
                .collect { test ->
                    _test.value = test
                    loadPatient(test.patientId)
                }
        }
    }

    private fun loadPatient(patientId: UUID) {
        viewModelScope.launch {
            getPatient(patientId).collect { _patient.value = it }
        }
    }

    fun generatePdf(test: ClinicalTest, patient: Patient): Uri {
        return pdfGenerator.generatePdf(test, patient)
    }
}
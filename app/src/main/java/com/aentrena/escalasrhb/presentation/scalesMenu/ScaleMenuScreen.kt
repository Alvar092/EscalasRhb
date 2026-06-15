package com.aentrena.escalasrhb.presentation.scalesMenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aentrena.escalasrhb.R
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTestItem
import com.aentrena.escalasrhb.domain.model.TestType
import com.aentrena.escalasrhb.domain.model.displayName
import com.aentrena.escalasrhb.domain.model.patients.Patient
import com.aentrena.escalasrhb.presentation.patients.AddPatientForm
import com.aentrena.escalasrhb.presentation.patients.PatientList
import com.aentrena.escalasrhb.presentation.patients.PatientsScreenMode
import com.aentrena.escalasrhb.presentation.theme.EscalasRhbTheme
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaleMenuScreen(
    patients: List<Patient>,
    selectedPatient: Patient?,
    createdTest: ClinicalTest?,
    testType: TestType,
    patientDisplayName: String?,
    isStartButtonEnabled: Boolean,
    onSelectPatient: (Patient) -> Unit,
    onAddPatient: (String, Long) -> Unit,
    onNavigateToInfo: () -> Unit,
    onStartTest: () -> Unit
) {

    var showPatientSheet by remember { mutableStateOf(false) }
    var showAddPatientSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val patientLabel = patientDisplayName
        ?.let { stringResource(R.string.patient_selected_format, it)}
        ?: stringResource(R.string.patients_select)


    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(top = 24.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Text(
                            text = stringResource(testType.displayName),
                            style = MaterialTheme.typography.displaySmall
                        )
                    }
                    }
                )
            }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { showPatientSheet = true },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 68.dp),
                shape = RoundedCornerShape(12.dp)
            ){
                Text(patientLabel)
            }



            Button(
                onClick = onNavigateToInfo,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 68.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(stringResource(R.string.scale_info))
            }



            Button(
                onClick = onStartTest,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 68.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = isStartButtonEnabled
            ) {
                Text(stringResource(R.string.scale_start))
            }

        }
    }
    if (showPatientSheet) {
        ModalBottomSheet(onDismissRequest = {showPatientSheet = false}, sheetState = sheetState) {
            PatientList(
                patients = patients,
                mode = PatientsScreenMode.Select,
                onSelectPatient = { patient ->
                    onSelectPatient(patient)
                    showPatientSheet = false
                },
                onCreatePatient = {
                    showPatientSheet = false
                    showAddPatientSheet = true
                },
                onLookDetail = {},
                onEditPatient = {},
                onDeletePatient = {}
            )
        }
    }

    if (showAddPatientSheet) {
        ModalBottomSheet(
            onDismissRequest = {showAddPatientSheet = false},
            sheetState = rememberModalBottomSheetState()
        ) {
            AddPatientForm(
                onSave = { name, birthDate ->
                    onAddPatient(name, birthDate)
                    showAddPatientSheet = false
                }
            )
        }
    }
}

@Preview(showBackground = true, locale = "es")
@Composable
private fun ScaleMenuScreen_Preview() {
    EscalasRhbTheme {
        val fakeTest = object : ClinicalTest {
            override val id = UUID.randomUUID()
            override val date = System.currentTimeMillis()
            override val evaluator = null
            override val patientId = UUID.randomUUID()
            override val side = null
            override val maxScore = 56
            override val totalScore = 0
            override val items = emptyList<ClinicalTestItem>()
            override val testType = TestType.BERG
        }
        val muestra = listOf(
            Patient(UUID.randomUUID(), "Ana Maria Martinez", System.currentTimeMillis()),
            Patient(UUID.randomUUID(), "Carlos", System.currentTimeMillis()),
            Patient(UUID.randomUUID(), "Laura", System.currentTimeMillis())
        )

        val defaultLabel = stringResource(R.string.patient_selected_format)

        ScaleMenuScreen(
            patients = muestra,
            selectedPatient = Patient(UUID.randomUUID(), "Ana Maria Martinez", System.currentTimeMillis()),
            createdTest = fakeTest,
            testType = TestType.BERG,
            patientDisplayName = defaultLabel,
            isStartButtonEnabled = false,
            onSelectPatient = {},
            onAddPatient = { _,_ -> },
            onNavigateToInfo = {},
            onStartTest = {}
        )
    }
}


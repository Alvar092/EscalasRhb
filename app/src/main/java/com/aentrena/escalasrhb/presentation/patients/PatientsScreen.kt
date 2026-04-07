package com.aentrena.escalasrhb.presentation.patients

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aentrena.escalasrhb.domain.model.patients.Patient
import com.aentrena.escalasrhb.presentation.theme.P1L
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

enum class PatientsScreenMode { Browse, Select}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientsScreen(
    patients: List<Patient>,
    mode: PatientsScreenMode,
    onLookDetail: (Patient) -> Unit,
    onSelectPatient: (Patient) -> Unit,
    onEditPatient: (Patient) -> Unit,
    onAddPatient: (String, Long) -> Unit,
    onNavigateBack: () -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Pacientes")
                }
            )
        },
        floatingActionButton = {
            if (mode == PatientsScreenMode.Browse) {
                ExtendedFloatingActionButton(
                    onClick = { showSheet = true },
                    icon = { Icon(Icons.Default.Add, contentDescription = null) },
                    text = { Text("Añadir paciente") }
                )
            }
        }
    ) { padding ->
        if(patients.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay pacientes creados.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Iterate, similar to for
                items(patients, key = {it.id}) { patient ->
                    val dismissState = rememberSwipeToDismissBoxState()
                    SwipeToDismissBox(state = dismissState, backgroundContent = {/*TODO: deletePatient()*/}) {
                        PatientCard(
                            patient = patient,
                            onClick = {
                                when (mode) {
                                    PatientsScreenMode.Browse -> onLookDetail(patient)
                                    PatientsScreenMode.Select -> {
                                        onSelectPatient(patient)
                                    }
                                }
                            },
                            onEdit = { onEditPatient(patient)}
                        )
                    }
                }
            }
        }
        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = {showSheet = false},
                sheetState = sheetState
            ){
                AddPatientForm(
                    onSave = { name, birthDate ->
                        onAddPatient(name, birthDate)
                        showSheet = false
                    }
                )
            }
        }
    }
}

@Composable
fun PatientCard(
    patient: Patient,
    onClick: () -> Unit,
    onEdit: () -> Unit
) {
   Card(
       modifier = Modifier
           .fillMaxWidth()
           .clickable { onClick() }
           .padding(horizontal = 12.dp),
       shape = RoundedCornerShape(14.dp),
       colors = CardDefaults.cardColors(
           containerColor = P1L,
           contentColor = MaterialTheme.colorScheme.onPrimary
       ),
       elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
   ) {
       Row(
           modifier = Modifier
               .fillMaxWidth()
               .padding(horizontal = 16.dp, vertical = 14.dp),
           verticalAlignment = Alignment.CenterVertically
       ) {
           Surface(
               modifier = Modifier
                   .size(44.dp)
                   .clip(CircleShape),
               color = MaterialTheme.colorScheme.primaryContainer
           ){
             Box(contentAlignment = Alignment.Center) {
                 Text(
                     text = patient.name.first().uppercase(),
                     style = MaterialTheme.typography.headlineMedium,
                 )
             }
           }
           Spacer(modifier = Modifier.weight(1f))

           Column(
               modifier = Modifier
                   .weight(1f)
           ) {
               Text(
                   text = patient.name,
                   style = MaterialTheme.typography.bodyMedium
               )

               Text(
                   text = "${patient.age}",
                   style = MaterialTheme.typography.bodySmall
               )
           }

       }
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPatientForm(
    onSave: (name: String, birthDate: Long) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    val selectedDateText = datePickerState.selectedDateMillis?.let {
        SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault()).format(Date(it))
    } ?: "Seleccionar fecha"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = selectedDateText,
            onValueChange = {},
            label = { Text("Fecha de nacimiento") },
            readOnly = false,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true }
        )

        Button(
            onClick = {
                val date = datePickerState.selectedDateMillis
                if (name.isNotBlank() && date != null) {
                    onSave(name, date)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Aceptar") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Preview
@Composable
private fun PatientsScreen_Preview() {
    val muestra = listOf(
        Patient(UUID.randomUUID(), "Ana Maria Martinez", System.currentTimeMillis()),
        Patient(UUID.randomUUID(), "Carlos",System.currentTimeMillis()),
        Patient(UUID.randomUUID(), "Laura", System.currentTimeMillis())
        )

    MaterialTheme {
        PatientsScreen(patients = muestra,
            mode = PatientsScreenMode.Browse,
            onLookDetail = {},
            onSelectPatient = {},
            onEditPatient = {},
            onAddPatient = {_, _ -> },
            onNavigateBack = {}
        )
    }
}

@Preview
@Composable
private fun PatientsScreenEmpty_Preview() {
    PatientsScreen(
        patients = emptyList(),
        mode = PatientsScreenMode.Browse,
        onLookDetail = {},
        onSelectPatient = {},
        onEditPatient = {},
        onAddPatient = {_, _ -> },
        onNavigateBack = {}
    )
}




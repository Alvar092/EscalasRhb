package com.aentrena.escalasrhb.presentation.patients

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aentrena.escalasrhb.domain.model.patients.Patient
import com.aentrena.escalasrhb.presentation.theme.P1L
import java.time.LocalDate
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientsScreen(
    patients: List<Patient> = emptyList(),
    onLookDetail: (Patient) -> Unit = {},
    onEditPatient: (Patient) -> Unit = {},
    onAddPatient: () -> Unit = {}
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Pacientes")} )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddPatient,
                icon = { Icon(Icons.Default.Add, contentDescription = null)},
                text = {Text("Añadir paciente")}
            )
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
                    SwipeToDismissBox(state = dismissState, backgroundContent = {}) {
                        PatientCard(
                            patient = patient,
                            onClick = { onLookDetail(patient)},
                            onEdit = { onEditPatient(patient)},
                        )
                    }
                }
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
           .clickable { onClick() },
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
                     text = patient.name,
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

@Preview
@Composable
private fun PatientsScreen_Preview() {
    val muestra = listOf(
        Patient(UUID.randomUUID(), "Ana", System.currentTimeMillis()),
        Patient(UUID.randomUUID(), "Carlos",System.currentTimeMillis()),
        Patient(UUID.randomUUID(), "Laura", System.currentTimeMillis())
        )

    MaterialTheme {
        PatientsScreen(patients = muestra)
    }
}

@Preview
@Composable
private fun PatientsScreenEmpty_Preview() {
    PatientsScreen()
}


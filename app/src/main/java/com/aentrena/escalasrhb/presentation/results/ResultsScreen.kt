package com.aentrena.escalasrhb.presentation.results

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.model.patients.Patient
import com.aentrena.escalasrhb.domain.model.scales.BergItem
import com.aentrena.escalasrhb.domain.model.scales.BergItemType
import com.aentrena.escalasrhb.domain.model.scales.BergTest
import com.aentrena.escalasrhb.presentation.theme.EscalasRhbTheme
import java.util.UUID

@Composable
fun ResultsScreen(
    test: ClinicalTest?,
    patient: Patient?,
    formattedDate: String,
    onExportPdf: () -> Unit
) {
    Scaffold(
        bottomBar = {
            Button(
                onClick = { /* navigateToRoot */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Home, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Volver a inicio")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) { test?.let { safeTest ->

            Text(
                text = "${test.testType}",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Paciente: ${patient?.name}",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Fecha: ${formattedDate}",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Text(
                text = "${test.totalScore}/${test.maxScore}",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }




            Button(
                onClick = { onExportPdf() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.Share, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Exportar a PDF")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ResultsScreen_Preview() {
    val fakeBergTest = BergTest(
        id = UUID.randomUUID(),
        date = System.currentTimeMillis(),
        evaluator = "Dr. García",
        patientId = UUID.randomUUID(),
        items = BergItemType.entries.map { itemType ->
            BergItem(
                itemType = itemType,
                score = 3,
                timeRecorded = null
            )
        }
    )

    EscalasRhbTheme {
        ResultsScreen(
            test = fakeBergTest,
            patient = Patient(
                UUID.randomUUID(),
                "Ana Maria Martinez",
                System.currentTimeMillis()
            ),
            formattedDate = "25/12/2025",
            onExportPdf = {}
        )
    }
}


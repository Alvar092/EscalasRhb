package com.aentrena.escalasrhb.presentation.patients

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aentrena.escalasrhb.domain.model.patients.ClinicalHistory
import com.aentrena.escalasrhb.domain.model.patients.Patient
import com.aentrena.escalasrhb.R
import com.aentrena.escalasrhb.domain.model.TestType
import com.aentrena.escalasrhb.presentation.theme.EscalasRhbTheme
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientDetailScreen(
    patient: Patient,
    tests: List<ClinicalHistory>,
    onTestClick: (ClinicalHistory) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(title = { Text ("Paciente") })
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                PatientHeader(
                    patient = patient,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            item {
                Text(
                    text = "Test",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            items(tests, key = { it.id }) { test ->
                ClinicalHistoryRow(
                    test = test,
                    onClick = { onTestClick(test) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
private fun PatientHeader(
    patient: Patient,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = null,
            modifier = Modifier.size(60.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = patient.name,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = "${patient.age}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun ClinicalHistoryRow(
    test: ClinicalHistory,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = test.testType.name,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = Instant.ofEpochMilli(test.date)
                    .atZone(ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Text(
            text = "${test.totalScore}/${test.maxScore}",
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview (showBackground = true)
@Composable
private fun PatientDetailScreen_Preview() {
    val patient = Patient(UUID.randomUUID(), "Ana Maria Martinez", System.currentTimeMillis())

    val tests = listOf(
        ClinicalHistory(
            id = "1",
            patientId = "p1",
            testType = TestType.BERG,
            date = System.currentTimeMillis(),
            totalScore = 42,
            maxScore = 56
        ),
        ClinicalHistory(
            id = "2",
            patientId = "p1",
            testType = TestType.MOTRICITY_INDEX,
            date = System.currentTimeMillis(),
            totalScore = 78,
            maxScore = 100
        ),
        ClinicalHistory(
            id = "3",
            patientId = "p1",
            testType = TestType.TRUNK_CONTROL_TEST,
            date = System.currentTimeMillis(),
            totalScore = 75,
            maxScore = 100
        )
    )

    MaterialTheme {
        PatientDetailScreen(
            patient = patient,
            tests = tests,
            onTestClick = {}
        )
    }
}


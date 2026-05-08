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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aentrena.escalasrhb.R
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTestItem
import com.aentrena.escalasrhb.domain.model.TestType
import com.aentrena.escalasrhb.domain.model.displayName
import com.aentrena.escalasrhb.domain.model.patients.Patient
import com.aentrena.escalasrhb.domain.model.scales.BergItem
import com.aentrena.escalasrhb.domain.model.scales.BergItemType
import com.aentrena.escalasrhb.domain.model.scales.BergTest
import com.aentrena.escalasrhb.domain.model.scales.BodySide
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexItem
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexItemType
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexTest
import com.aentrena.escalasrhb.presentation.bergTest.resources.BergItemCatalog
import com.aentrena.escalasrhb.presentation.results.resources.TestResultItem
import com.aentrena.escalasrhb.presentation.theme.EscalasRhbTheme
import java.util.UUID

@Composable
fun ResultsScreen(
    test: ClinicalTest?,
    patient: Patient?,
    formattedDate: String,
    resultItems: List<TestResultItem>,
    onExportPdf: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    Scaffold(
        bottomBar = {
            Button(
                onClick = { onNavigateToHome() },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            test?.let { safeTest ->
                item {
                    Text(
                        text = "${test.testType.displayName}",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                item {
                    Text(
                        text = "Paciente: ${patient?.name}",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Left
                    )
                }

                item {

                    Text(
                        text = "Fecha: ${formattedDate}",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Left
                    )
                }

                if (test.testType != TestType.BERG) {
                    item {
                        Text(
                            text = "${test.side}",
                            style = MaterialTheme.typography.displayMedium,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                item {
                        if (test.testType != TestType.BERG){
                            Text(
                                text = "${test.totalScore}/${test.maxScore}",
                                style = MaterialTheme.typography.displayMedium,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                        else {
                            Text(
                                text = "${test.totalScore}/${test.maxScore}",
                                style = MaterialTheme.typography.displayMedium,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                }
            }
            item {
                Button(
                    onClick = { onExportPdf() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Exportar a PDF")
                }
            }

            items(resultItems) { item ->
                ClinicalTestItemCard(item = item)
            }

        }
    }
}

@Composable
private fun ClinicalTestItemCard(
    item: TestResultItem
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(item.titleRes),
            style = MaterialTheme.typography.titleSmall,
        )
        item.options.forEach { (score, textRes) ->
            val isSelected = score == item.selectedScore
            Text(
                text = stringResource(textRes),
                style = if (isSelected) {
                    MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                } else {
                    MaterialTheme.typography.bodyMedium
                },
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun ResultsScreen_Preview() {
    val fakeBergTest = BergTest(
        id = UUID.randomUUID(),
        date = System.currentTimeMillis(),
        evaluator = "Dr. García",
        side = null,
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
            onExportPdf = {},
            resultItems =listOf(
                TestResultItem(
                    titleRes = R.string.berg_sittingtostanding_title,
                    options = listOf(
                        4 to R.string.berg_sittingtostanding_score_4,
                        3 to R.string.berg_sittingtostanding_score_3,
                        2 to R.string.berg_sittingtostanding_score_2,
                        1 to R.string.berg_sittingtostanding_score_1,
                        0 to R.string.berg_sittingtostanding_score_0,
                    ),
                    selectedScore = 3
                ),
                TestResultItem(
                    titleRes = R.string.berg_sittingtostanding_title,
                    options = listOf(
                        4 to R.string.berg_sittingtostanding_score_4,
                        3 to R.string.berg_sittingtostanding_score_3,
                        2 to R.string.berg_sittingtostanding_score_2,
                        1 to R.string.berg_sittingtostanding_score_1,
                        0 to R.string.berg_sittingtostanding_score_0,
                    ),
                    selectedScore = 3
                ),
                TestResultItem(
                    titleRes = R.string.berg_sittingtostanding_title,
                    options = listOf(
                        4 to R.string.berg_sittingtostanding_score_4,
                        3 to R.string.berg_sittingtostanding_score_3,
                        2 to R.string.berg_sittingtostanding_score_2,
                        1 to R.string.berg_sittingtostanding_score_1,
                        0 to R.string.berg_sittingtostanding_score_0,
                    ),
                    selectedScore = 3
                )
            ) ,
            onNavigateToHome = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ResultsScreenMotricity_Preview() {
    val fakeMotricityTest = MotricityIndexTest(
        id = UUID.randomUUID(),
        date = System.currentTimeMillis(),
        evaluator = "Dr. García",
        patientId = UUID.randomUUID(),
        side = BodySide.RIGHT,
        items = MotricityIndexItemType.entries.map { itemType ->
            MotricityIndexItem(
                itemType = itemType,
                score = 1
            )
        }
    )

    EscalasRhbTheme {
        ResultsScreen(
            test = fakeMotricityTest,
            patient = Patient(
                UUID.randomUUID(),
                "Ana Maria Martinez",
                System.currentTimeMillis()
            ),
            formattedDate = "25/12/2025",
            onExportPdf = {},
            resultItems =listOf(
                TestResultItem(
                    titleRes = R.string.motricity_pinchGrip_title,
                    options = listOf(
                        4 to R.string.motricity_pinchGrip_score_0,
                        3 to R.string.motricity_standard_score_14,
                        2 to R.string.motricity_standard_score_19,
                        1 to R.string.motricity_standard_score_25,
                        0 to R.string.motricity_standard_score_33,
                    ),
                    selectedScore = 3
                ),
                TestResultItem(
                    titleRes = R.string.motricity_pinchGrip_title,
                    options = listOf(
                        4 to R.string.motricity_pinchGrip_score_0,
                        3 to R.string.motricity_standard_score_14,
                        2 to R.string.motricity_standard_score_19,
                        1 to R.string.motricity_standard_score_25,
                        0 to R.string.motricity_standard_score_33,
                    ),
                    selectedScore = 2
                ),
                TestResultItem(
                    titleRes = R.string.motricity_pinchGrip_title,
                    options = listOf(
                        4 to R.string.motricity_pinchGrip_score_0,
                        3 to R.string.motricity_standard_score_14,
                        2 to R.string.motricity_standard_score_19,
                        1 to R.string.motricity_standard_score_25,
                        0 to R.string.motricity_standard_score_33,
                    ),
                    selectedScore = 1
                )
            ) ,
            onNavigateToHome = {}
        )
    }
}


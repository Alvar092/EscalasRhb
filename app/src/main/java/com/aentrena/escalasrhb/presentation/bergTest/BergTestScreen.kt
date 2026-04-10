package com.aentrena.escalasrhb.presentation.bergTest

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aentrena.escalasrhb.domain.model.scales.BergItemType
import com.aentrena.escalasrhb.presentation.bergTest.resources.BergItemCatalog
import com.aentrena.escalasrhb.presentation.bergTest.resources.BergItemDefinition
import com.aentrena.escalasrhb.presentation.theme.EscalasRhbTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BergTestScreen(
    currentItemIndex: Int,
    definition: BergItemDefinition,
    selectedScore: Int?,
    totalScore: Int,
    isTimerRunning: Boolean,
    formattedTime: String,
    itemCount: Int,
    onNextItem: () -> Unit,
    onBackItem: () -> Unit,
    onSelectScore: (Int) -> Unit,
    onStartTimer: () -> Unit,
    onStopTimer: () -> Unit,
    onResetTimer: () -> Unit,
    onFinish: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                TextButton(onClick = onBackItem) {
                    Text("Atrás")
                }

                Text(
                    text = " Total:\n $totalScore / 56"
                )

                TextButton(onClick = onNextItem) {
                    Text("Siguiente")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Index
            Text(
                text = "${currentItemIndex + 1} / ${itemCount}",
                style = MaterialTheme.typography.headlineMedium
            )

            //Title
            Text(
                text = (stringResource(definition.titleRes)),
                style = MaterialTheme.typography.headlineMedium
            )

            // Description
            Text(
                text = (stringResource(definition.descriptionRes)),
                style = MaterialTheme.typography.bodyMedium
            )

            // Timer
            if (definition.needsTimer) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = formattedTime,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    TextButton(onClick = {
                        if(!isTimerRunning) {
                            onStartTimer()
                        } else {
                            onStopTimer()
                        }
                    })
                    {
                        if (isTimerRunning) {
                            Text("Pausar")
                        } else {
                            Text("iniciar")
                        }
                    }

                    TextButton(onClick = onResetTimer )
                    {
                        Text("Reset")
                    }
                }
            }

            // Answer options
            definition.scoringOptions.forEach { option ->
                val isSelected = selectedScore == option.score
                Box(
                     modifier = Modifier
                         .fillMaxWidth()
                         .border(
                             1.dp,
                             if (isSelected) Color.Blue else Color.Blue.copy(alpha = 0.7f)
                         )
                         .background(if (isSelected) MaterialTheme.colorScheme.secondary else Color.Transparent)
                         .clickable { onSelectScore(option.score) }
                         .padding(horizontal = 12.dp, vertical = 16.dp)

                ) {
                    Text(
                        text = stringResource(option.textRes),
                        color = if (isSelected) MaterialTheme.colorScheme.onSecondary
                        else MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun BergTestScreen_Preview() {
    EscalasRhbTheme {
        BergTestScreen(
            currentItemIndex = 0,
            definition = BergItemCatalog.definitions[BergItemType.SITTING_TO_STANDING]!!,
            selectedScore = 0,
            totalScore = 0,
            isTimerRunning = false,
            formattedTime = "00:00.0",
            itemCount = 14,
            onNextItem = {},
            onBackItem = {},
            onSelectScore = {},
            onStartTimer = {},
            onStopTimer = {},
            onResetTimer = {},
            onFinish = {}
        )
    }
}

@Preview
@Composable
private fun BergTestScreenTimer_Preview() {
    EscalasRhbTheme {
        BergTestScreen(
            currentItemIndex = 0,
            definition = BergItemCatalog.definitions[BergItemType.STANDING_UNSUPPORTED_FEET_TOGETHER]!!,
            selectedScore = 3,
            totalScore = 0,
            isTimerRunning = false,
            formattedTime = "00:00.0",
            itemCount = 14,
            onNextItem = {},
            onBackItem = {},
            onSelectScore = {},
            onStartTimer = {},
            onStopTimer = {},
            onResetTimer = {},
            onFinish = {}
        )
    }
}


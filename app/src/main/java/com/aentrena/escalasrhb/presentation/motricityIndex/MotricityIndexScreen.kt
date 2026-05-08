package com.aentrena.escalasrhb.presentation.motricityIndex

import android.app.AlertDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.aentrena.escalasrhb.domain.model.scales.BodySide
import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexItemType
import com.aentrena.escalasrhb.presentation.motricityIndex.resources.MotricityIndexCatalog
import com.aentrena.escalasrhb.presentation.motricityIndex.resources.MotricityItemDefinition
import com.aentrena.escalasrhb.presentation.theme.EscalasRhbTheme


@Composable
fun MotricityIndexScreen(
    currentItemIndex: Int,
    definition: MotricityItemDefinition,
    selectedScore: Int?,
    upperLimbScore: Int,
    loweLimbScore: Int,
    itemCount: Int,
    showSideDialog: Boolean,
    onSideSelected: (BodySide) -> Unit,
    onNextItem: () -> Unit,
    onBackItem: () -> Unit,
    onSelectScore: (Int) -> Unit,
    onFinish: () -> Unit,
    isLastItem: Boolean
) {
    if (showSideDialog) {
        SideSelectionDialog(onSideSelected = onSideSelected)
    }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                TextButton(onClick = onBackItem) {
                    Text("Atrás")
                }

                Column( modifier = Modifier
                    .padding()) {
                    Text(
                        text = "Puntuación MS: ${upperLimbScore}/ 100"
                    )
                    Text(
                        text = "Puntuación MI: ${loweLimbScore}/ 100"
                    )
                }


                TextButton(onClick = if (isLastItem) {
                    onFinish
                } else {
                    onNextItem
                }) {
                    Text(if (isLastItem) {
                        "Terminar"
                    } else {
                        "Siguiente"
                    }
                    )
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




@Composable
fun SideSelectionDialog(
    onSideSelected: (BodySide) -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        title = {Text("Selecciona el lado a evaluar")},
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                BodySide.entries.forEach { side ->
                    OutlinedButton(
                        onClick = { onSideSelected(side)},
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(side.name)
                    }
                }
            }
        },
        confirmButton = {}
    )
}

@Preview
@Composable
private fun MotricityIndexScreen_Preview() {
    EscalasRhbTheme {
        MotricityIndexScreen(
            currentItemIndex = 4,
            definition = MotricityIndexCatalog.definitions[MotricityIndexItemType.KNEE_EXTENSION]!!,
            selectedScore = 0,
            upperLimbScore = 20,
            loweLimbScore = 0,
            itemCount = 6,
            showSideDialog = false,
            onSideSelected = {},
            onNextItem = {},
            onBackItem = {},
            onSelectScore = {},
            onFinish = {},
            isLastItem = false
        )
    }
}
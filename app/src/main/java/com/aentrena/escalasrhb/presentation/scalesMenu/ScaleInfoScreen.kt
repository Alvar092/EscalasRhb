package com.aentrena.escalasrhb.presentation.scalesMenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aentrena.escalasrhb.domain.model.TestType
import com.aentrena.escalasrhb.domain.model.info
import com.aentrena.escalasrhb.presentation.theme.EscalasRhbTheme
import com.aentrena.escalasrhb.presentation.theme.P1L
import com.aentrena.escalasrhb.presentation.theme.TextOnPrim
import com.aentrena.escalasrhb.presentation.theme.TextOnPrimD

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaleInfoScreen(
    testType: TestType,
    onBack: () -> Unit
){
    val info = testType.info

    
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(title = { Text(
                text = "Información de la escala",
                style = MaterialTheme.typography.headlineMedium
            )},
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.tertiary
                )
            )
        }
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text("Descripción:", style = MaterialTheme.typography.headlineMedium)
            Text(text = stringResource(id = info.descriptionResId), style = MaterialTheme.typography.bodyMedium)

            Text("Materiales:", style = MaterialTheme.typography.headlineMedium)
            val materials = stringArrayResource(id = info.materialsResId)
            materials.forEach {
                Text(it)
            }


            Text("Calificación:", style = MaterialTheme.typography.headlineMedium)
            Text(text = stringResource(id = info.scoringResId), style = MaterialTheme.typography.bodyMedium)

            Text("Interpretación:", style = MaterialTheme.typography.headlineMedium)
            Text(text = stringResource(id = info.interpretationResId), style = MaterialTheme.typography.bodyMedium)


            Text("Recomendaciones:", style = MaterialTheme.typography.headlineMedium)
            Text(text = stringResource(id = info.recommendationsResId), style = MaterialTheme.typography.bodyMedium)


            Text("Referencias:", style = MaterialTheme.typography.headlineMedium)
            Text(text = info.referenceUrl, style = MaterialTheme.typography.bodyMedium)


        }

    }
}

@Preview
@Composable
private fun ScaleInfoScreen_Preview() {
    EscalasRhbTheme {
        ScaleInfoScreen( testType = TestType.BERG, onBack = {} )
    }
}



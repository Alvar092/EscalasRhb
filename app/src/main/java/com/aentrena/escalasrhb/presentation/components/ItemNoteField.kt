package com.aentrena.escalasrhb.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aentrena.escalasrhb.R

/**
 * Campo de observaciones asociado a un ítem de un test clínico.
 * Colapsado por defecto salvo que el ítem ya tenga una nota guardada.
 * [itemKey] identifica el ítem actual (p.ej. su índice) para reiniciar el estado
 * de expansión al cambiar de ítem.
 */
@Composable
fun ItemNoteField(
    note: String?,
    onNoteChange: (String?) -> Unit,
    itemKey: Any,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember(itemKey) { mutableStateOf(!note.isNullOrEmpty()) }

    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider()
        Spacer(Modifier.height(8.dp))

        TextButton(onClick = { isExpanded = !isExpanded }) {
            Icon(
                imageVector = if (note.isNullOrEmpty()) Icons.Default.Add else Icons.Default.Edit,
                contentDescription = null
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = stringResource(
                    if (note.isNullOrEmpty()) R.string.item_note_add else R.string.item_note_edit
                )
            )
        }

        AnimatedVisibility(visible = isExpanded) {
            OutlinedTextField(
                value = note ?: "",
                onValueChange = { onNoteChange(it.ifEmpty { null }) },
                placeholder = { Text(stringResource(R.string.item_note_placeholder)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 80.dp, max = 140.dp)
            )
        }
    }
}

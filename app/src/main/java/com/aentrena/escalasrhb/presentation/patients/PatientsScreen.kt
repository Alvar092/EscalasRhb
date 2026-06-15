package com.aentrena.escalasrhb.presentation.patients

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aentrena.escalasrhb.R
import com.aentrena.escalasrhb.domain.model.patients.Patient
import com.aentrena.escalasrhb.presentation.theme.P1L
import java.text.ParseException
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
    onDeletePatient: (Patient) -> Unit,
    onAddPatient: (String, Long) -> Unit,
    onNavigateBack: () -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSheet by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(R.string.home_menu_patients))
                }
            )
        },
        floatingActionButton = {
            if (mode == PatientsScreenMode.Browse) {
                ExtendedFloatingActionButton(
                    onClick = { showSheet = true },
                    icon = { Icon(Icons.Default.Add, contentDescription = null) },
                    text = { Text(stringResource(R.string.patients_add)) }
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
                    text = stringResource(R.string.patients_empty),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
                PatientList(
                    patients = patients,
                    mode = PatientsScreenMode.Browse,
                    onLookDetail = onLookDetail,
                    onSelectPatient = onSelectPatient,
                    onEditPatient = onEditPatient,
                    onDeletePatient = onDeletePatient,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientList(
    patients: List<Patient>,
    mode: PatientsScreenMode,
    onLookDetail: (Patient) -> Unit,
    onSelectPatient: (Patient) -> Unit,
    onCreatePatient: (() -> Unit)? = null,
    onEditPatient: (Patient) -> Unit,
    onDeletePatient: (Patient) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (onCreatePatient != null) {
            item {
                OutlinedButton(
                    onCreatePatient,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.patients_add))
                }
            }
        }

        // Iterate, similar to for
        items(patients, key = {it.id}) { patient ->
            var showDeleteDialog by remember { mutableStateOf(false) }
            var showEditSheet by remember { mutableStateOf(false) }
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { value ->
                    when (value) {
                        SwipeToDismissBoxValue.StartToEnd -> showEditSheet = true
                        SwipeToDismissBoxValue.EndToStart -> showDeleteDialog = true
                        SwipeToDismissBoxValue.Settled -> {}
                    }
                    false
                }
            )
            SwipeToDismissBox(
                state = dismissState,
                enableDismissFromStartToEnd = mode == PatientsScreenMode.Browse,
                enableDismissFromEndToStart = mode == PatientsScreenMode.Browse,
                backgroundContent = {
                    when (dismissState.targetValue) {
                        SwipeToDismissBoxValue.StartToEnd -> SwipeActionBackground(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            icon = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.patients_edit),
                            alignment = Alignment.CenterStart
                        )
                        SwipeToDismissBoxValue.EndToStart -> SwipeActionBackground(
                            color = MaterialTheme.colorScheme.errorContainer,
                            tint = MaterialTheme.colorScheme.onErrorContainer,
                            icon = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.patients_delete),
                            alignment = Alignment.CenterEnd
                        )
                        SwipeToDismissBoxValue.Settled -> {}
                    }
                }
            ) {
                PatientCard(
                    patient = patient,
                    onClick = {
                        when (mode) {
                            PatientsScreenMode.Browse -> onLookDetail(patient)
                            PatientsScreenMode.Select -> {
                                onSelectPatient(patient)
                            }
                        }
                    }
                )
            }

            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text(stringResource(R.string.patients_delete_title)) },
                    text = { Text(stringResource(R.string.patients_delete_message, patient.name)) },
                    confirmButton = {
                        TextButton(onClick = {
                            showDeleteDialog = false
                            onDeletePatient(patient)
                        }) {
                            Text(stringResource(R.string.patients_delete))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text(stringResource(R.string.patients_cancel))
                        }
                    }
                )
            }

            if (showEditSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showEditSheet = false },
                    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
                ) {
                    AddPatientForm(
                        initialName = patient.name,
                        initialBirthDate = patient.dateOfBirth,
                        onSave = { name, birthDate ->
                            onEditPatient(patient.copy(name = name, dateOfBirth = birthDate))
                            showEditSheet = false
                        }
                    )
                }
            }
        }
    }

}

@Composable
private fun SwipeActionBackground(
    color: Color,
    tint: Color,
    icon: ImageVector,
    contentDescription: String?,
    alignment: Alignment
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .background(color = color, shape = RoundedCornerShape(14.dp))
            .padding(horizontal = 20.dp),
        contentAlignment = alignment
    ) {
        Icon(imageVector = icon, contentDescription = contentDescription, tint = tint)
    }
}

@Composable
fun PatientCard(
    patient: Patient,
    onClick: () -> Unit
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
    initialName: String = "",
    initialBirthDate: Long? = null,
    onSave: (name: String, birthDate: Long) -> Unit
) {
    var name by remember { mutableStateOf(initialName) }
    var birthDateField by remember {
        mutableStateOf(TextFieldValue(initialBirthDate?.let { formatBirthDateMillis(it) } ?: ""))
    }
    var showDatePicker by remember { mutableStateOf(false) }
    var showDateError by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.patients_add_name)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = birthDateField,
            onValueChange = { newValue ->
                val formatted = formatBirthDateInput(newValue.text)
                birthDateField = TextFieldValue(formatted, selection = TextRange(formatted.length))
                showDateError = false
            },
            label = { Text(stringResource(R.string.patients_birthdate_label)) },
            placeholder = { Text(stringResource(R.string.patients_birthdate_format)) },
            singleLine = true,
            isError = showDateError,
            supportingText = {
                if (showDateError) {
                    Text(stringResource(R.string.patients_birthdate_invalid))
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val birthDate = parseBirthDate(birthDateField.text)
                if (name.isNotBlank() && birthDate != null) {
                    onSave(name, birthDate)
                } else {
                    showDateError = birthDate == null
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.patients_save))
        }
    }
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = parseBirthDate(birthDateField.text) ?: System.currentTimeMillis(),
            initialDisplayMode = DisplayMode.Picker,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis <= System.currentTimeMillis()
                }
            }
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val formatted = formatBirthDateMillis(millis)
                        birthDateField = TextFieldValue(formatted, selection = TextRange(formatted.length))
                        showDateError = false
                    }
                    showDatePicker = false
                }) { Text(stringResource(R.string.patients_save)) }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

private fun formatBirthDateInput(input: String): String {
    val digits = input.filter { it.isDigit() }.take(8)
    return buildString {
        digits.forEachIndexed { index, c ->
            if (index == 2 || index == 4) append('/')
            append(c)
        }
    }
}

private fun parseBirthDate(text: String): Long? {
    if (text.length != 10) return null
    return try {
        val time = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply { isLenient = false }.parse(text)?.time
        time?.takeIf { it <= System.currentTimeMillis() }
    } catch (e: ParseException) {
        null
    }
}

private fun formatBirthDateMillis(millis: Long): String =
    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(millis))

@Preview
@Composable
private fun PatientsScreen_Preview() {
    val muestra = listOf(
        Patient(UUID.randomUUID(), "Ana Maria Martinez", System.currentTimeMillis()),
        Patient(UUID.randomUUID(), "Carlos",System.currentTimeMillis()),
        Patient(UUID.randomUUID(), "Laura", System.currentTimeMillis())
        )

    MaterialTheme {
        PatientsScreen(
            patients = muestra,
            mode = PatientsScreenMode.Browse,
            onLookDetail = {},
            onSelectPatient = {},
            onEditPatient = {},
            onDeletePatient = {},
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
        onDeletePatient = {},
        onAddPatient = {_, _ -> },
        onNavigateBack = {}
    )
}

@Preview(showBackground = true, locale = "en")
@Composable
fun AddPatientFormPreview() {
    AddPatientForm(
        onSave = { _, _ -> }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, locale = "es")
@Composable
fun AddPatientFormDatePickerPreview() {
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
    DatePickerDialog(
        onDismissRequest = {},
        confirmButton = {
            TextButton(onClick = {}) { Text(stringResource(R.string.patients_save)) }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
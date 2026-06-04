package com.aentrena.escalasrhb.presentation


import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aentrena.escalasrhb.R
import com.aentrena.escalasrhb.presentation.theme.EscalasRhbTheme
import com.aentrena.escalasrhb.presentation.theme.P1L
import com.aentrena.escalasrhb.presentation.theme.TextPrim



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToPatients: () -> Unit = {},
    onNavigateToContact: () -> Unit = {},
    onNavigateToBerg: () -> Unit = {},
    onNavigateToMotricity: () -> Unit = {},
    onNavigateToTrunk: () -> Unit = {},
) {
    var menuExpanded by remember { mutableStateOf(false)}

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    // Context menu
                    Box {
                        IconButton(onClick = {menuExpanded = true}) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú",
                                tint = TextPrim
                            )
                        }
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.home_menu_patients)) },
                                leadingIcon = {
                                    Icon(Icons.Default.Person, contentDescription = null)
                                },
                                onClick = {
                                    menuExpanded = false
                                    onNavigateToPatients()
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.home_menu_contact)) },
                                leadingIcon = {
                                    Icon(Icons.Default.Email, contentDescription = null)
                                },
                                onClick = {
                                    menuExpanded = false
                                    onNavigateToContact()
                                }
                            )
                        }
                    }
                }
            )
        }
    ) {padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.Welcome),
                style = MaterialTheme.typography.headlineMedium
            )

            Image(
                painter = painterResource(id = R.mipmap.icono),
                contentDescription = "icono app"
            )

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = stringResource(R.string.GladToSeeYou),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = stringResource(R.string.Choose_a_scale),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Tres botones
            Button(
                onClick = onNavigateToBerg,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(stringResource(R.string.Berg))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onNavigateToMotricity,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(stringResource(R.string.Motricity))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onNavigateToTrunk,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(stringResource(R.string.Trunk))
            }

            Spacer(modifier = Modifier.height(12.dp))

             Button(
                onClick = { throw RuntimeException("Test Crash") },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Test Crash")
            }
        }
    }
}

@Preview(locale = "en")
@Composable
private fun HomeScreen_Preview() {
    EscalasRhbTheme {
        HomeScreen()
    }

}


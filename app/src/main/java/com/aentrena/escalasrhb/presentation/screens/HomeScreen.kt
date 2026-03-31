package com.aentrena.escalasrhb.presentation.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aentrena.escalasrhb.presentation.theme.P1L
import com.aentrena.escalasrhb.presentation.theme.TextPrim



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    // Lambdas que descoplan la navegación de la UI

    onNavigateToPatients: () -> Unit = {},
    onNavigateToContact: () -> Unit = {},
    onAction1: () -> Unit = {},
    onAction2: () -> Unit = {},
    onAction3: () -> Unit = {},
) {
    /*
    Estado local de la UI.
    remember guarda el estado durante las recomposiciones,

    mutableStateOf hace que Compose re-renderice cuando cambie el valor:
        En este caso es el menu desplegable.
     */

    var menuExpanded by remember { mutableStateOf(false)}

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {},
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
                        /*
                        Se muestra cuando menuExpanded = true
                         */
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Pacientes") },
                                leadingIcon = {
                                    Icon(Icons.Default.Person, contentDescription = null)
                                },
                                onClick = {
                                    menuExpanded = false
                                    // TODO onNavigateToPatients()
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Contacto") },
                                leadingIcon = {
                                    Icon(Icons.Default.Email, contentDescription = null)
                                },
                                onClick = {
                                    menuExpanded = false
                                    // TODO onNavigateToContact()
                                }
                            )
                        }
                    }
                }
            )
        }
    ) {padding ->
        // Aplicamos padding al contenido para dejar espacio para la TopBar
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Mensaje de bienvenida (2 líneas)
            Text(
                text = "Bienvenido",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Nos alegra verte de nuevo.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Elige una escala para comenzar.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Tres botones
            Button(
                onClick = onAction1,
                colors = ButtonDefaults.buttonColors(containerColor = P1L),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Berg Balance Scale")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onAction2,
                colors = ButtonDefaults.buttonColors(containerColor = P1L),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Índice motor")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onAction3,
                colors = ButtonDefaults.buttonColors(containerColor = P1L),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Test de control de tronco")
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreen_Preview() {
    HomeScreen()
}


package com.aentrena.escalasrhb.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.aentrena.escalasrhb.presentation.HomeScreen
import com.aentrena.escalasrhb.presentation.patients.PatientsScreen
import com.aentrena.escalasrhb.presentation.patients.PatientsViewModel

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToPatients = { navController.navigate(Routes.PATIENTS)},
                onNavigateToContact = { navController.navigate(Routes.CONTACT)}
            )
        }

        composable(Routes.PATIENTS) {
            val viewModel: PatientsViewModel = hiltViewModel()
            val patients by viewModel.patients.collectAsStateWithLifecycle()

            PatientsScreen(
                patients = patients,
                onLookDetail = { /*navController.navigate(Routes.PATIENT_DETAIL)*/},
                onEditPatient = { viewModel.onEditPatient(it)},
                onAddPatient = {name, birthdate -> viewModel.addPatient(name,birthdate)},
                onNavigateBack = { navController.popBackStack()}
            )
        }

        /*composable(Routes.CONTACT) {
            ContactScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        } */
    }
}
package com.aentrena.escalasrhb.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.model.TestType
import com.aentrena.escalasrhb.domain.model.patients.Patient
import com.aentrena.escalasrhb.presentation.HomeScreen
import com.aentrena.escalasrhb.presentation.patients.PatientsScreen
import com.aentrena.escalasrhb.presentation.patients.PatientsScreenMode
import com.aentrena.escalasrhb.presentation.patients.PatientsViewModel
import com.aentrena.escalasrhb.presentation.scalesMenu.ScalesMenuScreen
import com.aentrena.escalasrhb.presentation.scalesMenu.ScalesMenuViewModel

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
                onNavigateToContact = { navController.navigate(Routes.CONTACT)},
                onNavigateToBerg = { navController.navigate(Routes.scaleMenu(TestType.BERG))},
                onNavigateToMotricity = { navController.navigate(Routes.scaleMenu(TestType.MOTRICITY_INDEX))},
                onNavigateToTrunk = { navController.navigate(Routes.scaleMenu(TestType.TRUNK_CONTROL_TEST))},
            )
        }

        composable(Routes.PATIENTS) {
            val viewModel: PatientsViewModel = hiltViewModel()
            val patients by viewModel.patients.collectAsStateWithLifecycle()

            PatientsScreen(
                patients = patients,
                mode = PatientsScreenMode.Browse,
                onLookDetail = { /*navController.navigate(Routes.PATIENT_DETAIL)*/},
                onSelectPatient = { },
                onEditPatient = { viewModel.onEditPatient(it)},
                onAddPatient = {name, birthdate -> viewModel.addPatient(name,birthdate)},
                onNavigateBack = { navController.popBackStack() }
            )
        }

        /*composable(Routes.CONTACT) {
            ContactScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        } */

        composable(route = Routes.SCALE_MENU,
            arguments = listOf(navArgument("testType"){
                type = NavType.StringType
            })
        ) { backStackEntry ->

            val viewModel: ScalesMenuViewModel = hiltViewModel()

            val selectedPatient by viewModel.selectedPatient.collectAsStateWithLifecycle()
            val createdTest by viewModel._createdTest.collectAsStateWithLifecycle()
            val testType = viewModel.testType


            ScalesMenuScreen(
                selectedPatient = selectedPatient,
                createdTest = createdTest,
                testType = testType,
                onSelectPatient = {
                    navController.navigate(Routes.PATIENT_SELECT)
                },
                onNavigateToInfo = { /* TODO: handle info navigation */},
                onStartTest = { /* TODO: handle start test navigation */}
            )

        }

        composable(Routes.PATIENT_SELECT) {
           val patientsViewModel: PatientsViewModel = hiltViewModel()

            val patients by patientsViewModel.patients.collectAsStateWithLifecycle()

            val parentEntry = remember(navController.currentBackStackEntry) {
                navController.getBackStackEntry(Routes.SCALE_MENU)
            }
            val scalesViewModel: ScalesMenuViewModel = hiltViewModel(parentEntry)

            PatientsScreen(
                patients = patients,
                mode = PatientsScreenMode.Select,
                onLookDetail = {},
                onSelectPatient = { patient ->
                    scalesViewModel.setSelectedPatient(patient)
                    navController.popBackStack()
                },
                onEditPatient = {},
                onAddPatient = {_, _ -> },
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
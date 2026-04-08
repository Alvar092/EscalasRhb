package com.aentrena.escalasrhb.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aentrena.escalasrhb.domain.model.ClinicalTestInfo
import com.aentrena.escalasrhb.domain.model.TestType
import com.aentrena.escalasrhb.presentation.HomeScreen
import com.aentrena.escalasrhb.presentation.patients.PatientsScreen
import com.aentrena.escalasrhb.presentation.patients.PatientsScreenMode
import com.aentrena.escalasrhb.presentation.patients.PatientsViewModel
import com.aentrena.escalasrhb.presentation.scalesMenu.ScaleInfoScreen
import com.aentrena.escalasrhb.presentation.scalesMenu.ScaleMenuScreen
import com.aentrena.escalasrhb.presentation.scalesMenu.ScaleMenuViewModel

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

            val viewModel: ScaleMenuViewModel = hiltViewModel()
            val patients by viewModel.patients.collectAsStateWithLifecycle()
            val selectedPatient by viewModel.selectedPatient.collectAsStateWithLifecycle()
            val createdTest by viewModel._createdTest.collectAsStateWithLifecycle()
            val testType = viewModel.testType


            ScaleMenuScreen(
                patients = patients,
                selectedPatient = selectedPatient,
                createdTest = createdTest,
                testType = testType,
                onSelectPatient = {patient ->
                    viewModel.setSelectedPatient(patient)
                },
                onNavigateToInfo = {
                    val ruta = Routes.scaleInfo(TestType.BERG)
                    Log.d("NAV", "Navegando a: $ruta")
                    navController.navigate(Routes.scaleInfo(testType))
                                   },
                onStartTest = { /* TODO: handle start test navigation */ }
            )
        }

        composable(route = Routes.SCALE_INFO, arguments = listOf(navArgument("testType") { type =
            NavType.StringType})
        ) { backStackEntry ->

            val testTypeName = backStackEntry.arguments?.getString("testType")
            val testType = testTypeName?.let { TestType.valueOf(it) } ?: TestType.BERG
            ScaleInfoScreen(
               testType = testType,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
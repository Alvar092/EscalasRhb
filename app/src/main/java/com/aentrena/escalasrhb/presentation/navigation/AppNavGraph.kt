package com.aentrena.escalasrhb.presentation.navigation

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aentrena.escalasrhb.domain.model.TestType
import com.aentrena.escalasrhb.domain.model.scales.BergTest
import com.aentrena.escalasrhb.presentation.HomeScreen
import com.aentrena.escalasrhb.presentation.bergTest.BergTestScreen
import com.aentrena.escalasrhb.presentation.bergTest.BergTestUiState
import com.aentrena.escalasrhb.presentation.bergTest.BergTestViewModel
import com.aentrena.escalasrhb.presentation.patients.PatientsScreen
import com.aentrena.escalasrhb.presentation.patients.PatientsScreenMode
import com.aentrena.escalasrhb.presentation.patients.PatientsViewModel
import com.aentrena.escalasrhb.presentation.results.ResultsScreen
import com.aentrena.escalasrhb.presentation.results.ResultsViewModel
import com.aentrena.escalasrhb.presentation.scalesMenu.ScaleInfoScreen
import com.aentrena.escalasrhb.presentation.scalesMenu.ScaleMenuScreen
import com.aentrena.escalasrhb.presentation.scalesMenu.ScaleMenuViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

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
                    navController.navigate(Routes.scaleInfo(testType))
                                   },
                onStartTest = {
                    Log.d("NAV", "testType: $testType")
                    Log.d("NAV", "createdTest: $createdTest")
                    Log.d("NAV", "createdTest?.id: ${createdTest?.id}")
                    createdTest?.id?.let { testId ->
                        val ruta = Routes.test(testType, testId)
                        Log.d("NAV", "Navegando a: $ruta")
                        navController.navigate(Routes.test(testType, testId))
                    }
                }
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

        composable(route = Routes.TEST, arguments = listOf(
            navArgument("testId") { type = NavType.StringType},
            navArgument("testType") {type = NavType.StringType}
        )
        ) { backStackEntry ->
            val testType = backStackEntry.arguments?.getString("testType")
                ?.let { TestType.valueOf(it) } ?: TestType.BERG

            when(testType) {
                TestType.BERG -> {
                    val viewModel: BergTestViewModel = hiltViewModel()
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                    val currentItemIndex by viewModel.currentItemIndex.collectAsStateWithLifecycle()
                    val selectedScore by viewModel.selectedScoreItem.collectAsStateWithLifecycle()
                    val isTimerRunning by viewModel.isTimerRunning.collectAsStateWithLifecycle()
                    val formattedTime by viewModel.formattedTime.collectAsStateWithLifecycle()
                    val isLastItem by viewModel.isLastItem.collectAsStateWithLifecycle()

                    when (uiState) {
                        is BergTestUiState.Loading -> CircularProgressIndicator()
                        is BergTestUiState.Error -> Text("Error cargando el test")
                        is BergTestUiState.Ready -> {

                            val state = uiState as BergTestUiState.Ready

                            BergTestScreen(
                                currentItemIndex = currentItemIndex,
                                definition = viewModel.currentItemDefinition,
                                selectedScore = selectedScore,
                                totalScore = viewModel.totalScore,
                                isTimerRunning = isTimerRunning,
                                formattedTime = formattedTime,
                                itemCount = viewModel.items.size,
                                isLastItem = isLastItem,
                                onNextItem = { viewModel.nextItem() },
                                onBackItem = { viewModel.backItem() },
                                onSelectScore = { viewModel.selectScore(it) },
                                onStartTimer = { viewModel.startTimer() },
                                onStopTimer = { viewModel.saveAndStop() },
                                onResetTimer = { viewModel.resetTimer() },
                                onFinish = {
                                    viewModel.finishTest()
                                    navController.navigate(Routes.testResult(testId = viewModel.testId))

                                }
                            )
                        }
                    }
                }

                TestType.MOTRICITY_INDEX -> {

                }

                TestType.TRUNK_CONTROL_TEST -> {

                }
            }
        }

        composable(
            route = Routes.TEST_RESULT,
            arguments = listOf(navArgument("testId") { type = NavType.StringType}
            )
        ) { backStackEntry ->

            val viewModel: ResultsViewModel = hiltViewModel()
            val test by viewModel.test.collectAsStateWithLifecycle()
            val patient by viewModel.patient.collectAsStateWithLifecycle()
            val formattedDate by viewModel.formattedDate.collectAsState()

            ResultsScreen(
                test = test,
                patient = patient,
                formattedDate = formattedDate
            )

        }
    }
}
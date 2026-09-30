package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.screens.AchievementsScreen
import com.example.ui.screens.CaseSelectionScreen
import com.example.ui.screens.DictionaryScreen
import com.example.ui.screens.GroundingScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.InvestigationScreen
import com.example.ui.screens.LinearStorylineScreen
import com.example.ui.screens.MusicStudioScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.VeoVideoScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.WoodDark
import com.example.viewmodel.GameViewModel
import com.example.viewmodel.Screen

class MainActivity : ComponentActivity() {

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(uiState.toastMessage) {
                    uiState.toastMessage?.let { msg ->
                        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
                        viewModel.clearToast()
                    }
                }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(WoodDark),
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    containerColor = WoodDark
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (uiState.currentScreen) {
                            Screen.HOME -> HomeScreen(
                                uiState = uiState,
                                onNavigate = { viewModel.navigateTo(it) },
                                onStartCase = { caseId -> viewModel.startCase(caseId) },
                                onStartStoryline = { fromBeginning -> viewModel.startLinearStoryline(fromBeginning) }
                            )

                            Screen.CASE_SELECTION -> CaseSelectionScreen(
                                cases = uiState.cases,
                                userXp = uiState.userProgress.xp,
                                onBack = { viewModel.navigateTo(Screen.HOME) },
                                onSelectCase = { caseId -> viewModel.startCase(caseId) }
                            )

                            Screen.INVESTIGATION -> InvestigationScreen(
                                investigation = uiState.investigation,
                                viewModel = viewModel,
                                onBack = { viewModel.navigateTo(Screen.CASE_SELECTION) }
                            )

                            Screen.DICTIONARY -> DictionaryScreen(
                                entries = uiState.dictionaryEntries,
                                onBack = { viewModel.navigateTo(Screen.HOME) }
                            )

                            Screen.ACHIEVEMENTS -> AchievementsScreen(
                                uiState = uiState,
                                onBack = { viewModel.navigateTo(Screen.HOME) }
                            )

                            Screen.SETTINGS -> SettingsScreen(
                                onBack = { viewModel.navigateTo(Screen.HOME) },
                                onResetProgress = { viewModel.resetAllProgress() }
                            )

                            Screen.LINEAR_STORYLINE -> LinearStorylineScreen(
                                storyline = uiState.storyline,
                                viewModel = viewModel,
                                onBackToHome = { viewModel.navigateTo(Screen.HOME) }
                            )

                            Screen.GROUNDING_INTELLIGENCE -> GroundingScreen(
                                groundingState = uiState.grounding,
                                viewModel = viewModel,
                                onBack = { viewModel.navigateTo(Screen.HOME) }
                            )

                            Screen.MUSIC_STUDIO -> MusicStudioScreen(
                                musicState = uiState.music,
                                viewModel = viewModel,
                                onBack = { viewModel.navigateTo(Screen.HOME) }
                            )

                            Screen.VEO_RECONSTRUCTION -> VeoVideoScreen(
                                videoState = uiState.video,
                                viewModel = viewModel,
                                onBack = { viewModel.navigateTo(Screen.HOME) }
                            )

                            Screen.CLOUD_SYNC -> SettingsScreen(
                                onBack = { viewModel.navigateTo(Screen.HOME) },
                                onResetProgress = { viewModel.resetAllProgress() }
                            )
                        }
                    }
                }
            }
        }
    }
}

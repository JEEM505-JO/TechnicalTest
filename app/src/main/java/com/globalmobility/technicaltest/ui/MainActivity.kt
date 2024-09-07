package com.globalmobility.technicaltest.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.globalmobility.technicaltest.ui.home.HomeVM
import com.globalmobility.technicaltest.ui.home.ScreenHome
import com.globalmobility.technicaltest.ui.theme.TechnicalTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TechnicalTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: HomeVM = hiltViewModel()
                    val state by viewModel.state.collectAsState()
                    ScreenHome(state = state, paddingValues = innerPadding)
                }
            }
        }
    }
}



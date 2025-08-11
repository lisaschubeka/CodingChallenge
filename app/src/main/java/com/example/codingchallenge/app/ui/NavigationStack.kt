package com.example.codingchallenge.app.ui


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.codingchallenge.Screen
import com.example.codingchallenge.app.presentation.DetailViewModel
import com.example.codingchallenge.app.presentation.OverviewViewModel

@Composable
fun NavigationStack() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Overview) {
        composable<Screen.Overview> {
            val overviewViewModel: OverviewViewModel = hiltViewModel()
            HL7FileOverview(navController = navController, overviewViewModel)
        }


        composable<Screen.Detail> { backStackEntry ->
            val detailViewModel: DetailViewModel = hiltViewModel()

            val detailArgs = backStackEntry.toRoute<Screen.Detail>()
            val mshId = detailArgs.mshId
            HL7DetailScreen(
                navController = navController,
                viewModel = detailViewModel,
                mshId = mshId
            )
        }
    }
}
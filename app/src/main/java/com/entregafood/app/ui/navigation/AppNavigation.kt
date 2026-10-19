package com.entregafood.app.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.entregafood.app.data.MockRestaurants
import com.entregafood.app.ui.AuthViewModel
import com.entregafood.app.ui.SessionState
import com.entregafood.app.ui.home.HomeScreen
import com.entregafood.app.ui.login.LoginScreen
import com.entregafood.app.ui.restaurant.RestaurantScreen

object Routes {
    const val LOGIN = "login"
    const val HOME = "home"
    const val RESTAURANT = "restaurant/{id}"
    fun restaurant(id: String) = "restaurant/$id"
}

@Composable
fun AppNavigation(authViewModel: AuthViewModel) {
    val state by authViewModel.sessionState.collectAsState()
    val loginState by authViewModel.login.collectAsState()

    // Enquanto restaura a sessão, mostra um loading
    if (state is SessionState.Loading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        return
    }

    val navController = rememberNavController()
    val start = if (state is SessionState.LoggedIn) Routes.HOME else Routes.LOGIN

    // Reage ao login/logout: troca de fluxo e limpa a pilha de telas
    LaunchedEffect(state) {
        val route = navController.currentBackStackEntry?.destination?.route
        val target = when {
            state is SessionState.LoggedIn && route == Routes.LOGIN -> Routes.HOME
            state is SessionState.LoggedOut && route != null && route != Routes.LOGIN -> Routes.LOGIN
            else -> null
        }
        if (target != null) {
            navController.navigate(target) {
                popUpTo(navController.graph.id) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    NavHost(navController = navController, startDestination = start) {
        composable(Routes.LOGIN) {
            LoginScreen(
                state = loginState,
                onLogin = authViewModel::signIn,
                onDismissError = authViewModel::clearLoginError,
            )
        }
        composable(Routes.HOME) {
            val name = (state as? SessionState.LoggedIn)?.session?.user?.name ?: ""
            HomeScreen(
                userName = name,
                onRestaurantClick = { navController.navigate(Routes.restaurant(it.id)) },
                onLogout = authViewModel::signOut,
            )
        }
        composable(
            route = Routes.RESTAURANT,
            arguments = listOf(navArgument("id") { type = NavType.StringType }),
        ) { entry ->
            RestaurantScreen(
                restaurant = MockRestaurants.byId(entry.arguments?.getString("id")),
                onBack = { navController.popBackStack() },
            )
        }
    }
}

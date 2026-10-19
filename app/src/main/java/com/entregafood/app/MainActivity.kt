package com.entregafood.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.entregafood.app.ui.AuthViewModel
import com.entregafood.app.ui.navigation.AppNavigation
import com.entregafood.app.ui.theme.EntregaFoodTheme

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EntregaFoodTheme {
                AppNavigation(authViewModel)
            }
        }
    }
}

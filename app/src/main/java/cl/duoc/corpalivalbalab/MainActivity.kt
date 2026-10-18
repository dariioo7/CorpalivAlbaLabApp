package cl.duoc.corpalivalbalab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duoc.corpalivalbalab.ui.navigation.Screen
import cl.duoc.corpalivalbalab.ui.screens.CatalogoScreen
import cl.duoc.corpalivalbalab.ui.screens.ContactoScreen
import cl.duoc.corpalivalbalab.ui.screens.HomeScreen
import cl.duoc.corpalivalbalab.ui.theme.CorpalivAlbaLabAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CorpalivAlbaLabAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToCatalogo = { navController.navigate(Screen.Catalogo.route) },
                onNavigateToContacto = { navController.navigate(Screen.Contacto.route) }
            )
        }

        composable(Screen.Catalogo.route) {
            CatalogoScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Contacto.route) {
            ContactoScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
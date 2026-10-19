package cl.duoc.corpalivalbalab.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.duoc.corpalivalbalab.ui.screens.CatalogoScreen
import cl.duoc.corpalivalbalab.ui.screens.ContactoScreen
import cl.duoc.corpalivalbalab.ui.screens.CotizacionScreen
import cl.duoc.corpalivalbalab.ui.screens.HomeScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Catalogo : Screen("catalogo")
    object Contacto : Screen("contacto")
    object Cotizacion : Screen("cotizacion/{productoId}") {
        fun createRoute(productoId: Int) = "cotizacion/$productoId"
    }
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToCatalogo = { navController.navigate(Screen.Catalogo.route) },
                onNavigateToContacto = { navController.navigate(Screen.Contacto.route) },
                onVoiceCommand = { command -> processVoiceCommand(command, navController) }
            )
        }

        composable(Screen.Catalogo.route) {
            CatalogoScreen(
                onBack = { navController.popBackStack() },
                onProductoClick = { productoId ->
                    navController.navigate(Screen.Cotizacion.createRoute(productoId))
                }
            )
        }

        composable(Screen.Contacto.route) {
            ContactoScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Cotizacion.route,
            arguments = listOf(navArgument("productoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getInt("productoId") ?: 1
            CotizacionScreen(
                productoId = productoId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

// PUNTO 3 INTEGRADO: Función de interpretación de comandos de voz y enrutamiento
fun processVoiceCommand(command: String, navController: NavController) {
    when {
        command.contains("catálogo") || command.contains("productos") -> {
            navController.navigate(Screen.Catalogo.route)
        }
        command.contains("contacto") || command.contains("ayuda") -> {
            navController.navigate(Screen.Contacto.route)
        }
        command.contains("cotización") || command.contains("cotizar") -> {
            // Navega por defecto a la cotización con producto ID = 1
            navController.navigate(Screen.Cotizacion.createRoute(1))
        }
        command.contains("inicio") || command.contains("home") || command.contains("principal") -> {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Home.route) { inclusive = true }
            }
        }
    }
}
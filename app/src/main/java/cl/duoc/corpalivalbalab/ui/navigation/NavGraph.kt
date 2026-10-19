package cl.duoc.corpalivalbalab.ui.navigation

import androidx.compose.runtime.Composable
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
                onNavigateToContacto = { navController.navigate(Screen.Contacto.route) }
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
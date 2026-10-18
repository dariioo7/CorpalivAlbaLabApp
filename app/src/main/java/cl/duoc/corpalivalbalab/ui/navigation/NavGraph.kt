package cl.duoc.corpalivalbalab.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Catalogo : Screen("catalogo")
    object Contacto : Screen("contacto")
}
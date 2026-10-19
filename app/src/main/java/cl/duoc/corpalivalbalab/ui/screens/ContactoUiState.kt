package cl.duoc.corpalivalbalab.ui.screens

data class ContactoUiState(
    val nombre: String = "",
    val nombreError: String? = null,
    val correo: String = "",
    val correoError: String? = null,
    val telefono: String = "",
    val telefonoError: String? = null,
    val fechaContacto: String = "",
    val fechaError: String? = null,
    val requiereCertificado: Boolean = false,
    val suscribirBoletin: Boolean = true,
    val esFormularioValido: Boolean = false,
    val registroExitoso: Boolean = false
)
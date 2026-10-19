package cl.duoc.corpalivalbalab.ui.screens

data class CotizacionUiState(
    val nombreCliente: String = "",
    val correoCliente: String = "",
    val telefonoCliente: String = "",
    val cantidadText: String = "1",
    val errorNombre: String? = null,
    val errorCorreo: String? = null,
    val errorTelefono: String? = null,
    val errorCantidad: String? = null,
    val estaGuardado: Boolean = false,
    val mensajeExito: String? = null
)
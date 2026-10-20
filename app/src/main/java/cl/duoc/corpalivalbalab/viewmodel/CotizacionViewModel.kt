package cl.duoc.corpalivalbalab.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.corpalivalbalab.data.local.AppDatabase
import cl.duoc.corpalivalbalab.data.local.PedidoEntity
import cl.duoc.corpalivalbalab.data.repository.PedidoRepository
import cl.duoc.corpalivalbalab.ui.screens.CotizacionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CotizacionViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repository = PedidoRepository(db.pedidoDao())

    private val _uiState = MutableStateFlow(CotizacionUiState())
    val uiState: StateFlow<CotizacionUiState> = _uiState.asStateFlow()

    fun onNombreChanged(nombre: String) {
        _uiState.value = _uiState.value.copy(
            nombreCliente = nombre,
            errorNombre = if (nombre.isBlank()) "El nombre es obligatorio" else null
        )
    }

    fun onCorreoChanged(correo: String) {
        val esValido = android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()
        _uiState.value = _uiState.value.copy(
            correoCliente = correo,
            errorCorreo = if (!esValido) "Ingresa un correo válido" else null
        )
    }

    fun onTelefonoChanged(telefono: String) {
        _uiState.value = _uiState.value.copy(
            telefonoCliente = telefono,
            errorTelefono = if (telefono.length < 8) "Mínimo 8 dígitos" else null
        )
    }

    fun onCantidadChanged(cantidad: String) {
        val cant = cantidad.toIntOrNull()
        _uiState.value = _uiState.value.copy(
            cantidadText = cantidad,
            errorCantidad = if (cant == null || cant <= 0) "Cantidad debe ser mayor a 0" else null
        )
    }

    fun guardarPedido(productoNombre: String, productoPrecio: Int) {
        val state = _uiState.value
        val cant = state.cantidadText.toIntOrNull() ?: 0

        val errorNom = if (state.nombreCliente.isBlank()) "El nombre es obligatorio" else null
        val errorCor = if (!android.util.Patterns.EMAIL_ADDRESS.matcher(state.correoCliente).matches()) "Correo inválido" else null
        val errorTel = if (state.telefonoCliente.length < 8) "Teléfono inválido" else null
        val errorCant = if (cant <= 0) "Cantidad inválida" else null

        if (errorNom != null || errorCor != null || errorTel != null || errorCant != null) {
            _uiState.value = state.copy(
                errorNombre = errorNom,
                errorCorreo = errorCor,
                errorTelefono = errorTel,
                errorCantidad = errorCant
            )
            return
        }

        viewModelScope.launch {
            val fechaActual = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
            val total = productoPrecio * cant

            val nuevoPedido = PedidoEntity(
                productoNombre = productoNombre,
                productoPrecio = productoPrecio,
                cantidad = cant,
                total = total,
                nombreCliente = state.nombreCliente,
                correoCliente = state.correoCliente,
                telefonoCliente = state.telefonoCliente,
                fechaRegistro = fechaActual
            )

            repository.insertarPedido(nuevoPedido)

            _uiState.value = _uiState.value.copy(
                estaGuardado = true,
                mensajeExito = "¡Solicitud registrada con éxito para $productoNombre!"
            )
        }
    }

    fun resetEstado() {
        _uiState.value = CotizacionUiState()
    }
}
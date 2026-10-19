package cl.duoc.corpalivalbalab.viewmodel

import androidx.lifecycle.ViewModel
import cl.duoc.corpalivalbalab.ui.screens.ContactoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ContactoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ContactoUiState())
    val uiState: StateFlow<ContactoUiState> = _uiState.asStateFlow()

    fun onNombreChanged(nuevoNombre: String) {
        _uiState.update { it.copy(nombre = nuevoNombre, nombreError = null) }
        validarFormulario()
    }

    fun onCorreoChanged(nuevoCorreo: String) {
        _uiState.update { it.copy(correo = nuevoCorreo, correoError = null) }
        validarFormulario()
    }

    fun onTelefonoChanged(nuevoTelefono: String) {
        // Permitir solo dígitos
        if (nuevoTelefono.all { it.isDigit() }) {
            _uiState.update { it.copy(telefono = nuevoTelefono, telefonoError = null) }
            validarFormulario()
        }
    }

    fun onFechaChanged(nuevaFecha: String) {
        _uiState.update { it.copy(fechaContacto = nuevaFecha, fechaError = null) }
        validarFormulario()
    }

    fun onRequiereCertificadoChanged(valor: Boolean) {
        _uiState.update { it.copy(requiereCertificado = valor) }
    }

    fun onSuscribirBoletinChanged(valor: Boolean) {
        _uiState.update { it.copy(suscribirBoletin = valor) }
    }

    fun enviarFormulario() {
        val estadoActual = _uiState.value

        var hayError = false
        var errorNombre: String? = null
        var errorCorreo: String? = null
        var errorTelefono: String? = null
        var errorFecha: String? = null

        // validar nombre
        if (estadoActual.nombre.isBlank()) {
            errorNombre = "El nombre no puede estar vacío"
            hayError = true
        }

        // validar correo
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(estadoActual.correo).matches()) {
            errorCorreo = "Ingrese un correo electrónico válido"
            hayError = true
        }

        // validar telefono min 8 digitos
        if (estadoActual.telefono.length < 8) {
            errorTelefono = "El teléfono debe tener al menos 8 dígitos"
            hayError = true
        }

        // validar fecha
        if (estadoActual.fechaContacto.isBlank()) {
            errorFecha = "Debe seleccionar una fecha de preferencia"
            hayError = true
        }

        if (hayError) {
            _uiState.update {
                it.copy(
                    nombreError = errorNombre,
                    correoError = errorCorreo,
                    telefonoError = errorTelefono,
                    fechaError = errorFecha,
                    esFormularioValido = false
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    esFormularioValido = true,
                    registroExitoso = true
                )
            }
        }
    }

    private fun validarFormulario() {
        val estado = _uiState.value
        val esValido = estado.nombre.isNotBlank() &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(estado.correo).matches() &&
                estado.telefono.length >= 8 &&
                estado.fechaContacto.isNotBlank()

        _uiState.update { it.copy(esFormularioValido = esValido) }
    }

    fun resetearEstadoRegistro() {
        _uiState.update { it.copy(registroExitoso = false) }
    }
}
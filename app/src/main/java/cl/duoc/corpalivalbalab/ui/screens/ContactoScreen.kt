package cl.duoc.corpalivalbalab.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.corpalivalbalab.viewmodel.ContactoViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactoScreen(
    onBack: () -> Unit,
    viewModel: ContactoViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var mostrarDatePicker by remember { mutableStateOf(false) }

    // diálogo de confirmación
    if (uiState.registroExitoso) {
        AlertDialog(
            onDismissRequest = { viewModel.resetearEstadoRegistro() },
            title = { Text("¡Solicitud Enviada!") },
            text = { Text("Gracias por tu interés en apoyar a CORPALIV / Alba Lab. Nos pondremos en contacto contigo pronto.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.resetearEstadoRegistro()
                        onBack()
                    }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }

    // seleccionar fecha
    if (mostrarDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { mostrarDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            formatter.timeZone = TimeZone.getTimeZone("UTC")
                            val fechaFormateada = formatter.format(Date(millis))
                            viewModel.onFechaChanged(fechaFormateada)
                        }
                        mostrarDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Formulario de Voluntariado") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Ingrese sus datos",
                style = MaterialTheme.typography.titleLarge
            )

            // campo nombre
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { viewModel.onNombreChanged(it) },
                label = { Text("Nombre Completo") },
                isError = uiState.nombreError != null,
                supportingText = {
                    uiState.nombreError?.let { Text(it) }
                },
                trailingIcon = {
                    if (uiState.nombreError != null) {
                        Icon(Icons.Filled.Error, "Error", tint = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // campo correo
            OutlinedTextField(
                value = uiState.correo,
                onValueChange = { viewModel.onCorreoChanged(it) },
                label = { Text("Correo Electrónico") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = uiState.correoError != null,
                supportingText = {
                    uiState.correoError?.let { Text(it) }
                },
                trailingIcon = {
                    if (uiState.correoError != null) {
                        Icon(Icons.Filled.Error, "Error", tint = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // campo teléfono
            OutlinedTextField(
                value = uiState.telefono,
                onValueChange = { viewModel.onTelefonoChanged(it) },
                label = { Text("Teléfono de Contacto") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = uiState.telefonoError != null,
                supportingText = {
                    uiState.telefonoError?.let { Text(it) }
                },
                trailingIcon = {
                    if (uiState.telefonoError != null) {
                        Icon(Icons.Filled.Error, "Error", tint = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // campo fecha
            OutlinedTextField(
                value = uiState.fechaContacto,
                onValueChange = {},
                readOnly = true,
                label = { Text("Fecha de disponibilidad") },
                isError = uiState.fechaError != null,
                supportingText = {
                    uiState.fechaError?.let { Text(it) }
                },
                trailingIcon = {
                    IconButton(onClick = { mostrarDatePicker = true }) {
                        Icon(Icons.Filled.CalendarToday, contentDescription = "Seleccionar fecha")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // checkbox
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = uiState.requiereCertificado,
                    onCheckedChange = { viewModel.onRequiereCertificadoChanged(it) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Deseo recibir correos sobre noticias",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // switch
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Es primera vez siendo voluntario?",
                    style = MaterialTheme.typography.bodyMedium
                )
                Switch(
                    checked = uiState.suscribirBoletin,
                    onCheckedChange = { viewModel.onSuscribirBoletinChanged(it) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // botón enviar
            Button(
                onClick = { viewModel.enviarFormulario() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enviar Solicitud")
            }
        }
    }
}
package cl.duoc.corpalivalbalab.ui.screens

import android.content.Intent
import android.net.Uri
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cl.duoc.corpalivalbalab.R
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToCatalogo: () -> Unit,
    onNavigateToContacto: () -> Unit,
    onVoiceCommand: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val telefonoCorpaliv = "+56912345678"
    val direccionCorpaliv = "Lo Encalada 207, Ñuñoa, Santiago, Chile"

    // Launcher para capturar la respuesta del reconocedor de voz nativo
    val voiceLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val spokenText = result.data
            ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            ?.firstOrNull()

        spokenText?.lowercase()?.let { command ->
            onVoiceCommand(command)
            when {
                command.contains("catálogo") || command.contains("productos") -> onNavigateToCatalogo()
                command.contains("contacto") || command.contains("ayuda") -> onNavigateToContacto()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CORPALIV / Alba Lab", fontWeight = FontWeight.Bold) },
                actions = {
                    // PUNTO 2 INTEGRADO: Botón de micrófono en la TopAppBar
                    // Dentro de HomeScreen.kt, en el evento onClick del IconButton del micrófono:

                    IconButton(onClick = {
                        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                            putExtra(
                                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                            )
                            // Forzar el idioma explícitamente a español de Chile / General
                            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-CL")
                            putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "es-CL")
                            putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "es-CL")

                            putExtra(
                                RecognizerIntent.EXTRA_PROMPT,
                                "Di 'Ver catálogo' o 'Contacto'"
                            )
                        }
                        try {
                            voiceLauncher.launch(intent)
                        } catch (e: Exception) {
                            // En caso de que el dispositivo no tenga una app de reconocimiento de voz instalada
                            e.printStackTrace()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "Navegación por voz",
                            tint = MaterialTheme.colorScheme.primary
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. HEADER INSTITUCIONAL CON LOGO
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_corpaliv),
                        contentDescription = "Logo CORPALIV",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Corporación de Ayuda al Niño Limitado Visual y Multilisiado",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Impulsando la inclusión, autonomía y capacitación laboral mediante el proyecto Alba Lab.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // 2. NAVEGACIÓN RÁPIDA (CATÁLOGO Y CONTACTO)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onNavigateToCatalogo,
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.ShoppingBag, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ver Catálogo")
                }

                OutlinedButton(
                    onClick = onNavigateToContacto,
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Email, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Contacto")
                }
            }

            // 3. INDICADORES / KPIs DE IMPACTO SOCIAL
            Text(
                text = "Nuestro Impacto Social",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                KpiCard(
                    icono = Icons.Default.Groups,
                    cifra = "+150",
                    etiqueta = "Jóvenes y Familias Beneficiadas",
                    modifier = Modifier.weight(1f)
                )
                KpiCard(
                    icono = Icons.Default.Favorite,
                    cifra = "30+",
                    etiqueta = "Años de Trayectoria",
                    modifier = Modifier.weight(1f)
                )
                KpiCard(
                    icono = Icons.Default.Star,
                    cifra = "100%",
                    etiqueta = "Productos Inclusivos",
                    modifier = Modifier.weight(1f)
                )
            }

            // 4. INFORMACIÓN INSTITUCIONAL Y PROYECTO ALBA LAB
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Taller Ocupacional Alba Lab",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Alba Lab es la iniciativa productiva de CORPALIV diseñada para promover la autonomía e integración social de jóvenes con múltiple discapacidad mediante el desarrollo de productos artesanales sustentables de alta calidad.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            // 5. CASO DE ÉXITO / TESTIMONIO
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Casos de Éxito",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "«Gracias a los talleres de Alba Lab, los participantes adquieren herramientas laborales concretas y potencian su autoestima y sociabilización en un entorno adaptado y seguro.»",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // 6. RECURSOS NATIVOS: SECCIÓN DE ATENCIÓN DIRECTA Y UBICACIÓN
            Text(
                text = "Atención e Instalaciones",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary
            )

            // Tarjeta de Llamada Telefónica (Recurso Nativo 1)
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Atención Telefónica",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Text(
                        text = "Llama directamente a la institución mediante la aplicación nativa del dispositivo.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:$telefonoCorpaliv")
                            }
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Icon(Icons.Default.Call, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Llamar ($telefonoCorpaliv)")
                    }
                }
            }

            // Tarjeta de Ubicación / Google Maps (Recurso Nativo 2)
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Ubicación Sede CORPALIV",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "Lo Encalada 207, Ñuñoa, Santiago. Presiona para abrir las indicaciones en Google Maps.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedButton(
                        onClick = {
                            val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(direccionCorpaliv)}")
                            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                                setPackage("com.google.android.apps.maps")
                            }
                            try {
                                context.startActivity(mapIntent)
                            } catch (e: Exception) {
                                val genericMapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                context.startActivity(genericMapIntent)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Icon(Icons.Default.LocationOn, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Abrir en Google Maps")
                    }
                }
            }
        }
    }
}

// Componente auxiliar para los KPIs
@Composable
fun KpiCard(
    icono: ImageVector,
    cifra: String,
    etiqueta: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = cifra,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = etiqueta,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center
            )
        }
    }
}
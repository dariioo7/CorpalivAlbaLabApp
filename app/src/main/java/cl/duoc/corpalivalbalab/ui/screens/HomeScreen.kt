package cl.duoc.corpalivalbalab.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToCatalogo: () -> Unit,
    onNavigateToContacto: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CORPALIV - Alba Lab") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Propósito de Alba Lab",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "A través de la Escuela Jan Van Dijk y Alba Lab, impulsamos la autonomía y capacitación laboral.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onNavigateToCatalogo,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Catálogo")
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onNavigateToContacto,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Quiero Colaborar / Contacto")
            }
        }
    }
}
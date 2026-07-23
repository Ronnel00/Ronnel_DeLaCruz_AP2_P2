package edu.ucne.ronnel_delacruz_ap2_p2.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    id: Int,
    onBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.load(id)
    }

    LaunchedEffect(state.savedSuccessfully) {
        if (state.savedSuccessfully) onBack()
    }

    DetailBodyScreen(
        state = state,
        onBack = onBack,
        onSuplidorChange = viewModel::onSuplidorChange,
        onFechaChange = viewModel::onFechaChange,
        onNcfChange = viewModel::onNcfChange,
        onItbisChange = viewModel::onItbisChange,
        onMontoChange = viewModel::onMontoChange,
        onSave = viewModel::save
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBodyScreen(
    state: DetailUiState,
    onBack: () -> Unit,
    onSuplidorChange: (String) -> Unit,
    onFechaChange: (String) -> Unit,
    onNcfChange: (String) -> Unit,
    onItbisChange: (String) -> Unit,
    onMontoChange: (String) -> Unit,
    onSave: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.gastoId == 0) "Nuevo Gasto" else "Editar Gasto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = state.suplidor,
                        onValueChange = onSuplidorChange,
                        label = { Text("Suplidor") },
                        isError = state.suplidorError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.suplidorError?.let {
                        Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
                    }

                    OutlinedTextField(
                        value = state.fecha,
                        onValueChange = onFechaChange,
                        label = { Text("Fecha (2026-07-22T00:00:00)") },
                        isError = state.fechaError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.fechaError?.let {
                        Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
                    }

                    OutlinedTextField(
                        value = state.ncf,
                        onValueChange = onNcfChange,
                        label = { Text("NCF") },
                        isError = state.ncfError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.ncfError?.let {
                        Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
                    }

                    OutlinedTextField(
                        value = if (state.itbis == 0.0) "" else state.itbis.toString(),
                        onValueChange = onItbisChange,
                        label = { Text("ITBIS") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        isError = state.itbisError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.itbisError?.let {
                        Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
                    }

                    OutlinedTextField(
                        value = if (state.monto == 0.0) "" else state.monto.toString(),
                        onValueChange = onMontoChange,
                        label = { Text("Monto") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        isError = state.montoError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.montoError?.let {
                        Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
                    }

                    if (state.error != null) {
                        Text(
                            "Error: ${state.error}",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = onSave,
                        enabled = !state.isSaving,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (state.isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = Color.White
                            )
                        } else {
                            Text(if (state.gastoId == 0) "Guardar" else "Actualizar")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailBodyScreenPreview() {
    MaterialTheme {
        DetailBodyScreen(
            state = DetailUiState(),
            onBack = {},
            onSuplidorChange = {},
            onFechaChange = {},
            onNcfChange = {},
            onItbisChange = {},
            onMontoChange = {},
            onSave = {}
        )
    }
}
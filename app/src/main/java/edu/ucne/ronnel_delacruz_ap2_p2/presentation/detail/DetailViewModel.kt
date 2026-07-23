package edu.ucne.ronnel_delacruz_ap2_p2.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.ronnel_delacruz_ap2_p2.data.remote.Resource
import edu.ucne.ronnel_delacruz_ap2_p2.data.remote.dto.GastoRequest
import edu.ucne.ronnel_delacruz_ap2_p2.domain.usecase.GetGastoDetailUseCase
import edu.ucne.ronnel_delacruz_ap2_p2.domain.usecase.SaveGastoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getGastoDetailUseCase: GetGastoDetailUseCase,
    private val saveGastoUseCase: SaveGastoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state: StateFlow<DetailUiState> = _state.asStateFlow()

    fun load(id: Int) {
        if (id == 0) {
            _state.update { DetailUiState() }
            return
        }
        viewModelScope.launch {
            getGastoDetailUseCase(id).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update {
                        it.copy(isLoading = true)
                    }
                    is Resource.Success -> _state.update {
                        it.copy(
                            isLoading = false,
                            gastoId = result.data?.gastoId ?: 0,
                            fecha = result.data?.fecha ?: "",
                            suplidor = result.data?.suplidor ?: "",
                            ncf = result.data?.ncf ?: "",
                            itbis = result.data?.itbis ?: 0.0,
                            monto = result.data?.monto ?: 0.0
                        )
                    }
                    is Resource.Error -> _state.update {
                        it.copy(isLoading = false, error = result.message)
                    }
                }
            }
        }
    }

    fun onSuplidorChange(value: String) = _state.update {
        it.copy(
            suplidor = value,
            suplidorError = if (value.isBlank()) "Suplidor es requerido" else null
        )
    }

    fun onFechaChange(value: String) = _state.update {
        it.copy(
            fecha = value,
            fechaError = if (value.isBlank()) "Fecha es requerida" else null
        )
    }

    fun onNcfChange(value: String) = _state.update {
        it.copy(
            ncf = value,
            ncfError = if (value.isBlank()) "NCF es requerido" else null
        )
    }

    fun onItbisChange(value: String) {
        val d = value.toDoubleOrNull()
        _state.update {
            it.copy(
                itbis = d ?: 0.0,
                itbisError = when {
                    value.isBlank() -> "ITBIS es requerido"
                    d == null -> "Ingrese un número válido"
                    d <= 0 -> "ITBIS debe ser mayor a 0"
                    else -> null
                }
            )
        }
    }

    fun onMontoChange(value: String) {
        val d = value.toDoubleOrNull()
        _state.update {
            it.copy(
                monto = d ?: 0.0,
                montoError = when {
                    value.isBlank() -> "Monto es requerido"
                    d == null -> "Ingrese un número válido"
                    d <= 0 -> "El monto debe ser mayor a 0"
                    else -> null
                }
            )
        }
    }

    fun save() {
        val s = _state.value
        val suplidorError = if (s.suplidor.isBlank()) "Suplidor es requerido" else null
        val fechaError = if (s.fecha.isBlank()) "Fecha es requerida" else null
        val ncfError = if (s.ncf.isBlank()) "NCF es requerido" else null
        val itbisError = if (s.itbis <= 0) "ITBIS debe ser mayor a 0" else null
        val montoError = if (s.monto <= 0) "El monto debe ser mayor a 0" else null

        if (suplidorError != null || fechaError != null || ncfError != null ||
            itbisError != null || montoError != null
        ) {
            _state.update {
                it.copy(
                    suplidorError = suplidorError,
                    fechaError = fechaError,
                    ncfError = ncfError,
                    itbisError = itbisError,
                    montoError = montoError
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            val request = GastoRequest(
                fecha = s.fecha,
                suplidor = s.suplidor,
                ncf = s.ncf,
                itbis = s.itbis,
                monto = s.monto
            )
            saveGastoUseCase(
                id = if (s.gastoId == 0) null else s.gastoId,
                gasto = request
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isSaving = true) }
                    is Resource.Success -> _state.update {
                        it.copy(isSaving = false, savedSuccessfully = true)
                    }
                    is Resource.Error -> _state.update {
                        it.copy(isSaving = false, error = result.message)
                    }
                }
            }
        }
    }
}
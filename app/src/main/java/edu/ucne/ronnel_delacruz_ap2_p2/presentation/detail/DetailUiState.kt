package edu.ucne.ronnel_delacruz_ap2_p2.presentation.detail

data class DetailUiState(
    val gastoId: Int = 0,
    val fecha: String = "",
    val suplidor: String = "",
    val ncf: String = "",
    val itbis: Double = 0.0,
    val monto: Double = 0.0,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val error: String? = null,
    val savedSuccessfully: Boolean = false,
    val suplidorError: String? = null,
    val fechaError: String? = null,
    val ncfError: String? = null,
    val itbisError: String? = null,
    val montoError: String? = null
)
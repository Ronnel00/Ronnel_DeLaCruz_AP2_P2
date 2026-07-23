package edu.ucne.ronnel_delacruz_ap2_p2.presentation.list

import edu.ucne.ronnel_delacruz_ap2_p2.domain.model.Gasto

data class ListUiState(
    val isLoading: Boolean = false,
    val gastos: List<Gasto> = emptyList(),
    val error: String? = null
)
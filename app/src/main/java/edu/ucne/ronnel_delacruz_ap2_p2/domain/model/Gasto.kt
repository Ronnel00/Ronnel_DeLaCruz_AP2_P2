package edu.ucne.ronnel_delacruz_ap2_p2.domain.model

data class Gasto(
    val gastoId: Int = 0,
    val fecha: String = "",
    val suplidor: String = "",
    val ncf: String = "",
    val itbis: Double = 0.0,
    val monto: Double = 0.0
)
package edu.ucne.ronnel_delacruz_ap2_p2.data.remote.dto

import edu.ucne.ronnel_delacruz_ap2_p2.domain.model.Gasto

data class GastoRequest(
    val fecha: String,
    val suplidor: String,
    val ncf: String,
    val itbis: Double,
    val monto: Double
)

data class GastoResponse(
    val gastoId: Int,
    val fecha: String,
    val suplidor: String,
    val ncf: String,
    val itbis: Double,
    val monto: Double
) {
    fun toDomain() = Gasto(
        gastoId = gastoId,
        fecha = fecha,
        suplidor = suplidor,
        ncf = ncf,
        itbis = itbis,
        monto = monto
    )
}
package edu.ucne.ronnel_delacruz_ap2_p2.domain.repository

import edu.ucne.ronnel_delacruz_ap2_p2.data.remote.Resource
import edu.ucne.ronnel_delacruz_ap2_p2.data.remote.dto.GastoRequest
import edu.ucne.ronnel_delacruz_ap2_p2.domain.model.Gasto
import kotlinx.coroutines.flow.Flow

interface GastoRepository {
    fun getGastos(): Flow<Resource<List<Gasto>>>
    fun getGastoById(id: Int): Flow<Resource<Gasto>>
    fun createGasto(gasto: GastoRequest): Flow<Resource<Gasto>>
    fun updateGasto(id: Int, gasto: GastoRequest): Flow<Resource<Gasto>>
}
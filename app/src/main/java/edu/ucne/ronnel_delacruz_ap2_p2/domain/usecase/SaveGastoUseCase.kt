package edu.ucne.ronnel_delacruz_ap2_p2.domain.usecase

import edu.ucne.ronnel_delacruz_ap2_p2.data.remote.dto.GastoRequest
import edu.ucne.ronnel_delacruz_ap2_p2.domain.repository.GastoRepository
import javax.inject.Inject

class SaveGastoUseCase @Inject constructor(
    private val repository: GastoRepository
) {
    operator fun invoke(id: Int?, gasto: GastoRequest) =
        if (id == null || id == 0)
            repository.createGasto(gasto)
        else
            repository.updateGasto(id, gasto)
}
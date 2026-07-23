package edu.ucne.ronnel_delacruz_ap2_p2.domain.usecase

import edu.ucne.ronnel_delacruz_ap2_p2.domain.repository.GastoRepository
import javax.inject.Inject

class GetGastosUseCase @Inject constructor(
    private val repository: GastoRepository
) {
    operator fun invoke() = repository.getGastos()
}
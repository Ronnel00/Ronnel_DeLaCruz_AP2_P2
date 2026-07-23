package edu.ucne.ronnel_delacruz_ap2_p2.data.repository

import edu.ucne.ronnel_delacruz_ap2_p2.data.remote.GastoApi
import edu.ucne.ronnel_delacruz_ap2_p2.data.remote.Resource
import edu.ucne.ronnel_delacruz_ap2_p2.data.remote.dto.GastoRequest
import edu.ucne.ronnel_delacruz_ap2_p2.domain.model.Gasto
import edu.ucne.ronnel_delacruz_ap2_p2.domain.repository.GastoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GastoRepositoryImpl @Inject constructor(
    private val api: GastoApi
) : GastoRepository {

    override fun getGastos(): Flow<Resource<List<Gasto>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getGastos()
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()!!.map { it.toDomain() }))
            } else {
                emit(Resource.Error("Error ${response.code()}"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Error de servidor: ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error desconocido: ${e.message}"))
        }
    }

    override fun getGastoById(id: Int): Flow<Resource<Gasto>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getGastoById(id)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()!!.toDomain()))
            } else {
                emit(Resource.Error("Error ${response.code()}"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Error de servidor: ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error desconocido: ${e.message}"))
        }
    }

    override fun createGasto(gasto: GastoRequest): Flow<Resource<Gasto>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.createGasto(gasto)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()!!.toDomain()))
            } else {
                emit(Resource.Error("Error ${response.code()}"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Error de servidor: ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error desconocido: ${e.message}"))
        }
    }

    override fun updateGasto(id: Int, gasto: GastoRequest): Flow<Resource<Gasto>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.updateGasto(id, gasto)
            if (response.isSuccessful) {
                emit(Resource.Success(gasto.toGasto(id)))
            } else {
                emit(Resource.Error("Error ${response.code()}"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Error de servidor: ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error desconocido: ${e.message}"))
        }
    }

    private fun GastoRequest.toGasto(id: Int) = Gasto(
        gastoId = id,
        fecha = fecha,
        suplidor = suplidor,
        ncf = ncf,
        itbis = itbis,
        monto = monto
    )
}
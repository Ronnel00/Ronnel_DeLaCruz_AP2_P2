package edu.ucne.ronnel_delacruz_ap2_p2.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.ronnel_delacruz_ap2_p2.data.remote.GastoApi
import edu.ucne.ronnel_delacruz_ap2_p2.data.repository.GastoRepositoryImpl
import edu.ucne.ronnel_delacruz_ap2_p2.domain.repository.GastoRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideApi(moshi: Moshi): GastoApi =
        Retrofit.Builder()
            .baseUrl("https://api-2026-h7eddqgydxc0fmau.eastus2-01.azurewebsites.net/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GastoApi::class.java)

    @Provides
    @Singleton
    fun provideRepository(api: GastoApi): GastoRepositoryImpl =
        GastoRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideGastoRepository(impl: GastoRepositoryImpl): GastoRepository = impl
}
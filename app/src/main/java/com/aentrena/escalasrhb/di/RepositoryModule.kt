package com.aentrena.escalasrhb.di

import com.aentrena.escalasrhb.data.local.daos.BergTestDao
import com.aentrena.escalasrhb.data.local.daos.MotricityIndexDao
import com.aentrena.escalasrhb.data.local.daos.PatientDao
import com.aentrena.escalasrhb.data.local.daos.TrunkControlTestDao
import com.aentrena.escalasrhb.data.repositories.BergTestRepositoryImpl
import com.aentrena.escalasrhb.data.repositories.MotricityIndexRepositoryImpl
import com.aentrena.escalasrhb.data.repositories.PatientRepositoryImpl
import com.aentrena.escalasrhb.data.repositories.TrunkControlTestRepositoryImpl
import com.aentrena.escalasrhb.domain.interfaces.repositories.BergTestRepository
import com.aentrena.escalasrhb.domain.interfaces.repositories.MotricityIndexRepository
import com.aentrena.escalasrhb.domain.interfaces.repositories.TrunkControlRepository
import com.aentrena.escalasrhb.domain.interfaces.repositories.PatientRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providePatientRepository(dao: PatientDao): PatientRepository =
        PatientRepositoryImpl(dao)


    @Provides
    @Singleton
    fun provideBergTestRepository(dao: BergTestDao): BergTestRepository =
        BergTestRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideMotricityIndexRepository(dao: MotricityIndexDao): MotricityIndexRepository =
        MotricityIndexRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideTrunkControlTestRepository(dao: TrunkControlTestDao): TrunkControlRepository =
        TrunkControlTestRepositoryImpl(dao)

}
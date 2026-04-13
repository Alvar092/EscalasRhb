package com.aentrena.escalasrhb.di

import android.content.Context
import androidx.room.Room
import com.aentrena.escalasrhb.data.local.AppDatabase
import com.aentrena.escalasrhb.data.local.daos.BergTestDao
import com.aentrena.escalasrhb.data.local.daos.ClinicalHistoryDao
import com.aentrena.escalasrhb.data.local.daos.MotricityIndexDao
import com.aentrena.escalasrhb.data.local.daos.PatientDao
import com.aentrena.escalasrhb.data.local.daos.TrunkControlTestDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "escalas_db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providePatientDao(db: AppDatabase): PatientDao = db.patientDao()

    @Provides
    @Singleton
    fun provideClinicalHistoryDao(db: AppDatabase): ClinicalHistoryDao = db.clinicalHistoryDao()

    @Provides
    @Singleton
    fun provideBergTestDao(db: AppDatabase): BergTestDao = db.bergTestDao()

    @Provides
    @Singleton
    fun provideMotricityIndexDao(db: AppDatabase): MotricityIndexDao = db.motricityIndexDao()

    @Provides
    @Singleton
    fun provideTrunkControlTestDao(db: AppDatabase): TrunkControlTestDao = db.trunkControlTestDao()
}
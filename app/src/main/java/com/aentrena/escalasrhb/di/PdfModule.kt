package com.aentrena.escalasrhb.di

import android.content.Context
import com.aentrena.escalasrhb.data.services.pdf.PdfGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PdfModule {

    @Provides
    @Singleton
    fun providePdfGenerator(@ApplicationContext context: Context): PdfGenerator {
        return PdfGenerator(context)
    }
}
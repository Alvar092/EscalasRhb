package com.aentrena.escalasrhb.domain.interfaces.repositories

import com.aentrena.escalasrhb.domain.interfaces.ClinicalTest
import com.aentrena.escalasrhb.domain.model.patients.ClinicalHistory
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ClinicalHistoryRepository {
    fun getPatientHistory(patientId: UUID): Flow<List<ClinicalHistory>>
    suspend fun addEntry(test: ClinicalTest)

    fun getById(testId: UUID): Flow<ClinicalHistory?>
}
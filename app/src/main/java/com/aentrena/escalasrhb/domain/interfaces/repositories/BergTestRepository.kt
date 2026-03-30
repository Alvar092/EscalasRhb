package com.aentrena.escalasrhb.domain.interfaces.repositories


import com.aentrena.escalasrhb.domain.model.scales.BergTest
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface BergTestRepository {
    fun getAll(): Flow<List<BergTest>>
    fun getByPatient(patientId: UUID): Flow<List<BergTest>>
    fun getById(id: UUID): Flow<BergTest?>
    suspend fun save(test: BergTest)
    suspend fun update(test: BergTest)
}
package com.aentrena.escalasrhb.domain.interfaces.repositories


import com.aentrena.escalasrhb.domain.model.scales.MotricityIndexTest
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface MotricityIndexRepository {
    fun getAll(): Flow<List<MotricityIndexTest>>
    fun getByPatient(patientId: UUID): Flow<List<MotricityIndexTest>>
    fun getById(id: UUID): Flow<MotricityIndexTest?>
    suspend fun save(test: MotricityIndexTest)
    suspend fun update(test: MotricityIndexTest)
}